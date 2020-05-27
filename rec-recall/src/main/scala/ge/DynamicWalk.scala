package ge

import data.{DataUtil, MddUtil, PoiUtil, UserCenterUtil}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, StringType, StructField, StructType}
import utils.{ConfigUtil, GLUtil, TimeUtil, MathUtil}

import scala.collection.mutable
import scala.util.Random

object DynamicWalk {

  val logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val interval: Int = ConfigUtil.dynamicwalkConf.getInt("interval")
  val minSearch: Int = ConfigUtil.dynamicwalkConf.getInt("min-search")
  val minCount: Int = ConfigUtil.dynamicwalkConf.getInt("min-count")
  val maxCount: Int = ConfigUtil.dynamicwalkConf.getInt("max-count")
  val pathCount: Int = ConfigUtil.dynamicwalkConf.getInt("path-count")
  val pathLength: Int = ConfigUtil.dynamicwalkConf.getInt("path-length")
  val transferProbA: Double = ConfigUtil.dynamicwalkConf.getDouble("transfer-prob.a")
  val transferProbB: Double = ConfigUtil.dynamicwalkConf.getDouble("transfer-prob.b")
  val transferProbC: Double = ConfigUtil.dynamicwalkConf.getDouble("transfer-prob.c")
  val timeSensitivity: Double = ConfigUtil.dynamicwalkConf.getDouble("time-sensitivity")
  val distanceSensitivity: Double = ConfigUtil.dynamicwalkConf.getDouble("distance-sensitivity")
  val typeBase: Double = ConfigUtil.dynamicwalkConf.getDouble("type-base")
  val walkPath: String = ConfigUtil.dynamicwalkConf.getString("walk-path")

  def main(args: Array[String]): Unit = {
    var dt = ""
    if (args.length == 0) {
      dt = TimeUtil.getYesterdayStr()
    } else if (args.length == 1) {
      dt = args(0)
    } else {
      logger.error("dt parameter error")
      sys.exit(1)
    }

    val spark = SparkSession.
      builder().
      appName("DynamicWalk").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    val endTime = dt
    val startTime = TimeUtil.getFewDaysAgo(dt, interval)

    val historySearch = getHistorySearch(spark)
    val userAction = getUserAction(startTime, endTime, spark)
    val poiLocation = getPoiLocation(spark)
    val mddLocation = getMddLocation(spark)
    val contentLocation = getContentLocation(endTime, spark)

    val nodeLocation = poiLocation.union(mddLocation).union(contentLocation).na.fill("0").distinct()
    val nodeAction = userAction.union(historySearch).na.fill("0").distinct()
    val nodeActionLocation = nodeAction.join(nodeLocation, Seq("item_type", "item_id"), "left").na.fill("0")

    val userBehaviors = getUserBehaviors(nodeActionLocation, spark)
    val walks = getDynamicWalk(userBehaviors)

    saveDynamicWalk(dt, walks)
  }

  def getHistorySearch(spark: SparkSession): DataFrame = {
    import spark.implicits._

    val historySearch = UserCenterUtil.getUserCenter(spark).
      select($"open_udid", $"action_search_historySearch".as("items")).
      filter(length($"items").>(0)).
      select($"open_udid",
        explode(from_json($"items",
          ArrayType(
            StructType(
              StructField("ct", StringType) ::
                StructField("inten", StringType) ::
                StructField("kw", StringType) ::
                StructField("mdd", StringType) :: Nil))))
          .as("items")).
      select($"open_udid",
        $"items.ct".as("ct"),
        $"items.inten".as("inten"),
        $"items.kw".as("kw"),
        $"items.mdd".as("mdd"))

    val queryFreq = historySearch.groupBy("kw").count().filter("count > %d".format(minSearch))

    val historySearchFilter = queryFreq.join(historySearch, Seq("kw"), "left").
      select($"open_udid", $"ct".as("ctime"), $"kw".as("item_id")).
      withColumn("item_type", lit("query"))

    historySearchFilter
  }

  def getPoiLocation(spark: SparkSession): DataFrame = {
    import spark.implicits._

    val poiLocation = PoiUtil.getPoiBase(spark).filter("flag = 0 and num_photo != 0").
      select($"poiid".as("item_id"), $"zh_name".as("name"), $"lat", $"lng").
      withColumn("item_type", lit("poi"))

    poiLocation
  }

  def getMddLocation(spark: SparkSession): DataFrame = {
    import spark.implicits._

    val mddLocation = MddUtil.getMddBase(spark).filter("status = '2'").
      select($"mddid".as("item_id"), $"name", $"lat", $"lng").
      withColumn("item_type", lit("mdd"))

    mddLocation
  }

  def getContentLocation(dt: String, spark: SparkSession): DataFrame = {
    val contentLocation = DataUtil.getUserActionExtraHour(dt, spark).
      select("item_id", "name", "lat", "lng", "item_type")

    contentLocation
  }

  def getUserAction(startTime: String, endTime: String, spark: SparkSession): DataFrame = {
    val userAction = DataUtil.getUserActionHour(startTime, endTime, spark)
      .select("open_udid", "ctime", "item_id", "item_type")

    userAction
  }

  def getUserBehaviors(nodeActionLocation: DataFrame, spark: SparkSession): DataFrame = {
    import spark.implicits._

    val userBehaviors = nodeActionLocation.
      select($"open_udid",
        concat(
        $"item_type", lit("_"),
          $"item_id", lit("_"),
          $"ctime", lit("_"),
          $"lat", lit("_"),
          $"lng").as("token")).
      groupBy($"open_udid").
      agg(concat_ws("\t", collect_list($"token")).alias("tokens")).
      filter($"tokens".contains("weng"))

    userBehaviors
  }

  def getDynamicWalk(behaviors: DataFrame): RDD[String] = {
    behaviors.rdd.flatMap { r =>
      val walks: mutable.ListBuffer[String] = mutable.ListBuffer.empty[String]
      val (openUdid: String, tokens: String) = (r(0), r(1))
      val itemUuid2Node = getItemUuid2Node(openUdid, tokens)

      if (itemUuid2Node.keySet.size > minCount && itemUuid2Node.keySet.size < maxCount) {
        val itemUuid2NodeSort: List[(String, EntityNode)] = itemUuid2Node.toSeq.sortWith {
          case ((u1, n1), (u2, n2)) =>
            val c1 = n1.getCtimeAverage
            val c2 = n2.getCtimeAverage
            c1 < c2
        }.toList

        val index2Node: mutable.HashMap[Int, EntityNode] = mutable.HashMap.empty[Int, EntityNode]
        for (index <- itemUuid2NodeSort.indices) {
          index2Node.put(index, itemUuid2NodeSort(index)._2)
        }

        val transferMatrix = initTransferMatrix(index2Node)

        val pc = Math.min(pathCount, index2Node.size)
        val pl = Math.min(pathLength, 2 * index2Node.size)

        val rand = new Random()

        for (_ <- 0 until pathCount) {
          var cur = 0
          val walk: mutable.ListBuffer[Integer] = mutable.ListBuffer.empty[Integer]
          while (walk.length < pathLength) {
            var next = 0
            val r = rand.nextDouble()
            var accumulate = .0
            while (next < transferMatrix.length && r >= accumulate) {
              accumulate += transferMatrix(cur)(next)
              next += 1
            }
            next -= 1
            if (next > 0) {
              walk.append(next)
            } else {
              val score = new Array[Double](transferMatrix.length)
              for (k <- transferMatrix.indices) {
                if (k != cur && k != next) {
                  val timeScore = 0.2 * getTimeScore(index2Node(cur).getCtimeAverage, index2Node(k).getCtimeAverage)
                  val distanceScore = 0.2 * getDistanceScore(index2Node(cur).getLat, index2Node(cur).getLng, index2Node(k).getLat, index2Node(k).getLng)
                  val frequencyScore = 0.2 * getFrequencyScore(index2Node(cur).getFreq)
                  val typeScore = 0.4 * getTypeScore(index2Node(cur).getItype, index2Node(k).getItype)
                  score(k) = Math.max(1.0 / (transferMatrix.length - 2), getScore(Array[Double](timeScore, distanceScore, frequencyScore, typeScore)))
                }
              }
              val softmaxScore = MathUtil.softmax(score)
              for (k <- transferMatrix.indices) {
                transferMatrix(next)(k) = softmaxScore(k)
              }
            }
            cur = next
          }
          val sentence = walk.map(x => index2Node(x).getItemUuid).mkString("\t")
          walks.append(sentence)
        }
      }
      walks.toList
    }
  }

  def saveDynamicWalk(dt: String, walks: RDD[String]): Unit = {
    walks.saveAsTextFile(walkPath.replace("${dt}", dt))
  }

  def getItemUuid2Node(openUdid: String, tokens: String): mutable.HashMap[String, EntityNode] = {
    val rawInput = Array(openUdid) ++ tokens.split("\t")
    val itemUuid2Node = new mutable.HashMap[String, EntityNode]

    import scala.util.control.Breaks._

    var NF = 0
    rawInput.foreach { token =>
      breakable {
        try {
          val tokenSplit: List[String] = token.toString.split("_").toList
          if (tokenSplit.length != 5 && NF > 0) {
            break()
          } else {
            val itemType = if (NF == 0) "user" else tokenSplit(0)
            val itemId = if (NF == 0) rawInput(0) else tokenSplit(1)
            val eventTime = if (NF == 0) (-1).toString else tokenSplit(2)
            val lat = if (NF == 0) 0.toString else tokenSplit(3)
            val lng = if (NF == 0) 0.toString else tokenSplit(4)
            val itemUuid = itemType + "-" + itemId
            if (itemUuid2Node.contains(itemUuid)) {
              itemUuid2Node(itemUuid).add(eventTime.toLong)
            } else {
              val en = new EntityNode(itemType, itemId, eventTime.toLong)
              en.setLat(lat.toDouble)
              en.setLng(lng.toDouble)
              itemUuid2Node.put(itemUuid, en)
            }
          }
        } catch {
          case e: Exception => {
            e.printStackTrace()
            break()
          }
        }
        NF += 1
      }
    }

    itemUuid2Node
  }

  def initTransferMatrix(index2Node: mutable.HashMap[Int, EntityNode]): Array[Array[Double]] = {
    val capacity = index2Node.size
    val matrix = new Array[Array[Double]](capacity)
    for (i <- 0 until capacity) {
      matrix(i) = new Array[Double](capacity)
      for (j <- 0 until capacity) {
        if ((i == 0 && j == 0) || i == j) matrix(i)(j) = 0
        else if (i == capacity - 1 && j == 0) matrix(i)(j) = 1.0
        else if (i == 0) matrix(i)(j) = 1.0 / (capacity - 1)
        else if (j == 0) matrix(i)(j) = transferProb(index2Node(i).getFreq, -1)
        else if (i != j - 1) matrix(i)(j) = 0
        else matrix(i)(j) = transferProb(index2Node(i).getFreq, 1)
      }
    }
    matrix
  }

  def transferProb(freq: Long, direction: Int): Double = {
    direction * Math.pow(freq, -transferProbB) / transferProbA + transferProbC
  }

  def getTimeScore(pre: Long, next: Long): Double = {
    val deltaMinute = Math.abs(pre - next) / 60.0
    val sensitivity = timeSensitivity
    -1.0 * Math.log(0.1 + sensitivity * deltaMinute)
  }

  def getDistanceScore(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double = {
    if ((lat1 == 0 && lng1 == 0) || (lat2 == 0 && lng2 == 0)) return 0
    val distance = GLUtil.getDistance(lat1, lng1, lat2, lng2)
    val sensitivity = distanceSensitivity
    -1.0 * Math.log(0.1 + sensitivity * distance)
  }

  def getFrequencyScore(freq: Long): Double = {
    2 * Math.log(1 + freq) - 1.4
  }

  def getTypeScore(t1: String, t2: String): Double = {
    val base = typeBase
    if (t1 == t2) Math.pow(-base, 3)
    else Math.pow(base, 3)
  }

  def getScore(score: Array[Double]): Double = {
    var t = score.sum
    if (t < -2) t = -2
    if (t > 2) t = 2
    MathUtil.sigmoid(t)
  }

}
