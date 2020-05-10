import scala.collection.mutable

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{concat_ws, explode, split}
import org.apache.log4j.{Level, Logger}

import data.DataUtil
import utils.TimeUtil
import udfs.CommonUDF

object BasicItemCFJob {

  val logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

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

    import spark.implicits._

    val endTime = dt
    val startTime = TimeUtil.getFewDaysAgo(dt, 30)

    val userAction = DataUtil.getUserAction(startTime, endTime, spark).
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
      filter("tokenNums < 100 and tokenNums > 5").na.drop()

    val itemCount = clickSession.select("tokens").
      withColumn("item_uuid", explode(split($"tokens", "\t"))).
      select("item_uuid").
      rdd.map(r => (r(0).toString, 1L)).
      reduceByKey(_ + _).
      collectAsMap()

    val broadcastItemCount = spark.sparkContext.broadcast(itemCount)

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

    val whiteSet = DataUtil.getWhiteList(endTime, spark).
      select(concat_ws("_", $"item_id", $"item_type").as("item_uuid")).rdd.map(r => r(0).toString).
      collect().toSet

    val broadcastWhiteSet = spark.sparkContext.broadcast(whiteSet)

    val whiteItemSimilarity = itemSimilarity.rdd.map { r =>
      val triggerId = r(0).toString
      val simId = r(1).toString
      val score = r(2).toString.toDouble
      val simItemId = simId.split("_")(0)
      val simItemType = simId.split("_")(1)
      val prefix = "{CF_BASICITEMCF_RECALLTYPE_V1}_"
      val key = prefix.replace("RECALLTYPE", simItemType) + triggerId
      val whiteSet = broadcastWhiteSet.value
      if (whiteSet.contains(simId)) (key, simItemId, simItemType, score) else null
    }.filter(t => t != null).toDF("key", "simItemId", "simItemType", "score")

    itemSimilarity.show

  }

  def baseContributeScore(n: Long): Double = {
    1.0d / Math.log(1 + n)
  }

}
