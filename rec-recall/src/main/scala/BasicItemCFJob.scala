import scala.collection.mutable
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{concat_ws, explode, split}
import org.apache.log4j.{Level, Logger}
import data.DataUtil
import org.apache.spark.broadcast.Broadcast
import utils.{TimeUtil, HiveUtil, ConfigUtil}
import udfs.CommonUDF

object BasicItemCFJob {

  val logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val interval: Int = ConfigUtil.basicitemcfConf.getInt("interval")
  val tokenMax: Int = ConfigUtil.basicitemcfConf.getInt("token-max")
  val tokenMin: Int = ConfigUtil.basicitemcfConf.getInt("token-min")
  val truncate: Int = ConfigUtil.basicitemcfConf.getInt("truncate")
  val decimal: String = ConfigUtil.basicitemcfConf.getString("decimal")
  val prefix: String = ConfigUtil.basicitemcfConf.getString("prefix")
  val dbName: String = ConfigUtil.basicitemcfConf.getString("dbname")
  val tableName: String = ConfigUtil.basicitemcfConf.getString("tablename")

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
      appName("BasicItemCFJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    val endTime = dt
    val startTime = TimeUtil.getFewDaysAgo(dt, interval)

    val clickSession = getClickSession(startTime, endTime, spark)

    val itemCount = getItemCount(clickSession, spark)
    val broadcastItemCount = spark.sparkContext.broadcast(itemCount)

    val commonAppear = getCommonAppear(clickSession, spark)
    val itemSimilarity = getItemSimilarity(commonAppear, broadcastItemCount, spark)

    val whiteList = getWhiteList(endTime, spark)
    val broadcastWhiteList = spark.sparkContext.broadcast(whiteList)
    val whiteListItemSimilarity = getWhiteListItemSimilarity(itemSimilarity, broadcastWhiteList, spark)

    val triggerSim = getTriggerSim(prefix, truncate, whiteListItemSimilarity, spark: SparkSession)

    HiveUtil.saveDataFrameAsHiveTable(triggerSim, dbName, tableName, endTime, spark)
  }

  def baseContributeScore(n: Long): Double = {
    1.0d / Math.log(1 + n)
  }

  def getClickSession(startTime: String, endTime: String, spark: SparkSession): DataFrame = {
    import spark.implicits._

    val userAction = DataUtil.getUserActionHour(startTime, endTime, spark).
      select("open_udid", "item_id", "item_type", "dt").
      na.drop().
      withColumn("item_type", CommonUDF.itemTypeTransform($"item_type")).
      filter("item_type != '-1'").
      select(concat_ws("_", $"open_udid", $"dt").as("user_uuid"),
        concat_ws("_", $"item_id", $"item_type").as("item_uuid"))

    val clickSession = userAction.rdd.map { r =>
      (r(0).toString, (r(1).toString, 1L))
    }.reduceByKey((x, y) => (x._1 + "\t" + y._1, x._2 + y._2)).map { r =>
      (r._1.toString, r._2._1, r._2._2)
    }.toDF("user_uuid", "tokens", "tokenNums").
      filter("tokenNums < %d and tokenNums > %d".format(tokenMax, tokenMin)).na.drop()

    clickSession
  }

  def getItemCount(clickSession: DataFrame, spark: SparkSession): scala.collection.Map[String, Long] = {
    import spark.implicits._

    val itemCount = clickSession.select("tokens").
      withColumn("item_uuid", explode(split($"tokens", "\t"))).
      select("item_uuid").
      rdd.map(r => (r(0).toString, 1L)).
      reduceByKey(_ + _).
      collectAsMap()

    itemCount
  }

  def getCommonAppear(clickSession: DataFrame, spark: SparkSession): DataFrame = {
    import spark.implicits._

    val commonAppear = clickSession.rdd.map { r =>
      val (userUuid: String, tokens: String, tokenNums: Long) = (r(0), r(1), r(2))
      val contributeScore = baseContributeScore(tokenNums)
      val itemPair: mutable.ListBuffer[String] = new mutable.ListBuffer[String]
      val itemArray = tokens.split("\t")
      for (i <- itemArray.indices) {
        for (j <- itemArray.indices) {
          if (!itemArray(i).equals(itemArray(j))) {
            itemPair.append(itemArray(i) + "#" + itemArray(j))
          }
        }
      }
      if (itemPair.nonEmpty) ((userUuid, contributeScore), itemPair.toSet.mkString(";")) else null
    }.filter(t => t != null).
      flatMapValues(_.split(";")).
      map { r =>
        val contributeScore = r._1._2
        val Array(triggerId, simId) = r._2.split("#")
        (triggerId, simId, contributeScore)
      }.toDF("triggerId", "simId", "contributeScore")

    commonAppear
  }

  def getItemSimilarity(commonAppear: DataFrame, broadcastItemCount: Broadcast[scala.collection.Map[String, Long]], spark: SparkSession): DataFrame = {
    import spark.implicits._

    val itemSimilarity = commonAppear.rdd.map { r =>
      ((r(0).toString, r(1).toString), (r(2).toString.toDouble, 1L))
    }.reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)).map { r =>
      val triggerId = r._1._1.toString
      val simId = r._1._2.toString
      val totalContributeScore = r._2._1.toString.toDouble
      val itemCount = broadcastItemCount.value
      val c1 = itemCount.getOrElse(triggerId, 0L)
      val c2 = itemCount.getOrElse(simId, 0L)
      val similarity = totalContributeScore / Math.sqrt(c1 * c2)
      (triggerId, simId, similarity)
    }.toDF("triggerId", "simId", "similarity")

    itemSimilarity
  }

  def getWhiteList(dt: String, spark: SparkSession): scala.collection.immutable.Set[String] = {
    import spark.implicits._

    val whiteList = DataUtil.getWhiteList(dt, spark).
      select(concat_ws("_", $"item_id", $"item_type").as("item_uuid")).rdd.map(r => r(0).toString).
      collect().toSet

    whiteList
  }

  def getWhiteListItemSimilarity(itemSimilarity: DataFrame, broadcastWhiteList: Broadcast[scala.collection.immutable.Set[String]], spark: SparkSession): DataFrame = {
    import spark.implicits._

    val whiteListItemSimilarity = itemSimilarity.rdd.map { r =>
      val triggerId = r(0).toString
      val simId = r(1).toString
      val similarity = r(2).toString.toDouble
      val whiteList = broadcastWhiteList.value
      if (whiteList.contains(simId)) (triggerId, simId, similarity) else null
    }.filter(t => t != null).toDF("triggerId", "simId", "similarity")

    whiteListItemSimilarity
  }

  def getTriggerSim(prefix: String, truncate: Int, whiteListItemSimilarity: DataFrame, spark: SparkSession): DataFrame = {
    import spark.implicits._

    val triggerSim = whiteListItemSimilarity.rdd.map { r =>
      val triggerId = r(0).toString
      val simId = r(1).toString
      val score = r(2).toString.toDouble
      val simItemId = simId.split("_")(0)
      val simItemType = simId.split("_")(1)
      val key = prefix.format(simItemType) + triggerId
      (key, simItemId, simItemType, score)
    }.toDF("key", "simItemId", "simItemType", "score").rdd.groupBy(r => r(0)).map {
      case (triggerId, simItemIdList) =>
        val simArray = simItemIdList.toArray.sortBy(r => r(3).toString.toDouble).reverse.map { r =>
          r(1).toString + "_" + r(2).toString + "_" + r(3).toString.toDouble.formatted(decimal)
        }
        if (truncate == -1 || simArray.length <= truncate) (triggerId.toString, simArray.mkString(";"))
        else (triggerId.toString, simArray.slice(0, truncate).mkString(";"))
    }.toDF("trigger", "sim")

    triggerSim
  }

}
