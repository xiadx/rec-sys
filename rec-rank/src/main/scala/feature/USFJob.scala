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

object USFJob {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val usfConf: Config = ConfigUtil.appConf.getConfig("usf")

  val outputPath: String = usfConf.getString("output-path")
  val interval: Int = usfConf.getInt("interval")
  val npartitions: Int = usfConf.getInt("npartitions")
  val storageLevel: String = usfConf.getString("storage-level")
  val days: mutable.Seq[Integer] = usfConf.getIntList("days").asScala

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
      appName("USFJob").
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
    val click = log.filter("is_click_event=1").select("open_udid", "dt")
    val show = log.filter("is_click_event=0").select("open_udid", "dt")

    var usf = log.select("open_udid").rdd.map { r =>
      (r.getAs[String]("open_udid"), 1L)
    }.reduceByKey(_ + _).map(_._1).toDF("open_udid")

    for (d <- days) {
      val start = TimeUtil.getFewDaysAgo(dt, d)
      val end = dt
//      click.filter($"dt".>=(start).and($"dt".<=(end)))
      val cdf = click.filter(s"dt>=$start and dt<= $end").rdd.map { r =>
        (r.getAs[String]("open_udid"), 1L)
      }.reduceByKey(_ + _).toDF("open_udid", s"index_user_click_$d")
      val sdf = show.filter(s"dt>=$start and dt<= $end").rdd.map { r =>
        (r.getAs[String]("open_udid"), 1L)
      }.reduceByKey(_ + _).toDF("open_udid", s"index_user_show_$d")
      val df = sdf.join(cdf, Seq("open_udid"), "left").
        withColumn(s"index_user_ctr_$d", CommonUDF.calcCtr(col(s"index_user_click_$d"), col(s"index_user_show_$d")))
      usf = usf.join(df, Seq("open_udid"), "left")
    }

    val path = outputPath.replace("{dt}", dt)
    DFUtil.save(usf, path)

    spark.stop()
  }

}
