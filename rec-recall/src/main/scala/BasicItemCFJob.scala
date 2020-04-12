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

    val whiteList = DataUtil.getWhiteList(endTime, spark)

    val userAction = DataUtil.getUserAction(startTime, endTime, spark).
      select("open_udid", "item_id", "item_type", "dt").
      na.drop().
      withColumn("item_type", CommonUDF.itemTypeTransform($"item_type")).
      filter("item_type != '-1'").
      select(concat_ws("_", $"open_udid", $"dt").as("open_udid"),
        concat_ws("_", $"item_id", $"item_type").as("item_uuid"))

    val clickSession = userAction.rdd.map { r =>
      (r(0).toString, (r(1).toString, 1))
    }.reduceByKey((x, y) => (x._1 + "\t" + y._1, x._2 + y._2)).map { r =>
      (r._1.toString, r._2._1, r._2._2)
    }.toDF("open_udid", "tokens", "tokenNums").
      filter("tokenNums < 100 and tokenNums > 5").na.drop()

    val itemCount = clickSession.select("tokens").
      withColumn("item_uuid", explode(split($"tokens", "\t"))).
      select("item_uuid").
      rdd.map(r => (r(0).toString, 1)).
      reduceByKey(_ + _).
      collectAsMap()

    val broadcastItemCount = spark.sparkContext.broadcast(itemCount)

    val commonAppear = clickSession.rdd.map { r =>
      val (openUdid: String, tokens: String, tokenNums: Long) = (r(0), r(1), r(2))
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
      ((openUdid,contributeScore), itemPair.mkString(";"))
    }.filter(t => t._2 != null).
      flatMapValues(_.split(";")).
      map { r =>
        val openUdid = r._1._1
        val contributeScore = r._1._2
        val Array(triggerId, simId) = r._2.split("#")
        (triggerId, simId, contributeScore)
      }.toDF("triggerId", "simId", "contributeScore")

  }

  def baseContributeScore(n: Long): Double = {
    1.0d / Math.log(1 + n)
  }

}
