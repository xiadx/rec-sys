package feature

import com.typesafe.config.Config
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import dc.LogUtil
import utils.{CMUtil, ConfigUtil, TimeUtil, DFUtil}
import udfs.CommonUDF
import scala.collection.JavaConverters._
import scala.collection.mutable

object ISFJob {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val isfConf: Config = ConfigUtil.appConf.getConfig("isf")

  val outputPath: String = isfConf.getString("output-path")
  val interval: Int = isfConf.getInt("interval")
  val npartitions: Int = isfConf.getInt("npartitions")
  val storageLevel: String = isfConf.getString("storage-level")
  val days: mutable.Seq[Integer] = isfConf.getIntList("days").asScala

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
      appName("ISFJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    import spark.implicits._

    val startTime = TimeUtil.getFewDaysAgo(dt, interval)
    val endTime = dt

    val log = LogUtil.getIndexSceneClickShow(startTime, endTime, spark).
      repartition(npartitions).
      persist(CMUtil.storageLevel(storageLevel))
    val click = log.filter("is_click_event=1").select("item_id", "item_type", "dt")
    val show = log.filter("is_click_event=0").select("item_id", "item_type", "dt")

    var usf = log.select("item_id", "item_type").rdd.map { r =>
      (r.getAs[String]("item_id"), r.getAs[String]("item_type")) -> 1L
    }.reduceByKey(_ + _).map(_._1).toDF("item_id", "item_type")

    for (d <- days) {
      val start = TimeUtil.getFewDaysAgo(dt, d)
      val end = dt
      //      click.filter($"dt".>=(start).and($"dt".<=(end)))
      val cdf = click.filter(s"dt>=$start and dt<= $end").rdd.map { r =>
        (r.getAs[String]("item_id"), r.getAs[String]("item_type")) -> 1L
      }.reduceByKey(_ + _).
        map(a => (a._1._1, a._1._2, a._2)).
        toDF("item_id", "item_type", s"index_item_click_$d")
      val sdf = show.filter(s"dt>=$start and dt<= $end").rdd.map { r =>
        (r.getAs[String]("item_id"), r.getAs[String]("item_type")) -> 1L
      }.reduceByKey(_ + _).
        map(a => (a._1._1, a._1._2, a._2)).
        toDF("item_id", "item_type", s"index_item_show_$d")
      val df = sdf.join(cdf, Seq("item_id", "item_type"), "left").
        withColumn(s"index_item_ctr_$d", CommonUDF.calcCtr(col(s"index_item_click_$d"), col(s"index_item_show_$d")))
      usf = usf.join(df, Seq("item_id", "item_type"), "left")
    }

    val path = outputPath.replace("{dt}", dt)
    DFUtil.save(usf, path)

    spark.stop()
  }

}
