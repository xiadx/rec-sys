package label

import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}
import dc.LogUtil
import utils.{ConfigUtil, DFUtil, TimeUtil}

object LabelJob {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val labelConf: Config = ConfigUtil.appConf.getConfig("label")

  val outputPath: String = labelConf.getString("output-path")

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
      appName("LabelJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    val clickShow = LogUtil.getIndexSceneClickShow(dt, dt, spark)
    val schema = clickShow.schema

    val rdd = clickShow.rdd.map { r =>
      (r.getAs[String]("open_udid"),
        r.getAs[String]("item_id"),
        r.getAs[String]("item_type"),
        r.getAs[String]("scene"),
        r.getAs[String]("show_cycle_id")) -> r
    }.reduceByKey { (a, b) =>
      if (a.getAs[Long]("is_click_event") == 1L) a else b
    }.map(_._2)

    val label = spark.createDataFrame(rdd, schema).
      withColumnRenamed("is_click_event", "label")
    val path = outputPath.replace("{dt}", dt)
    DFUtil.save(label, path)

    spark.stop()
  }

}
