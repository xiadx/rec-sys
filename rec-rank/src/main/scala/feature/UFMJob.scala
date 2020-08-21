package feature

import com.typesafe.config.Config
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import dc.UPUtil
import utils.{ConfigUtil, TimeUtil, DFUtil, CMUtil, MapperUtil}
import entity.{FeatureStatus, FeatureMark}
import frame.ConfManager
import scala.collection.JavaConverters._
import scala.collection.mutable

object UFMJob {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val ufmConf: Config = ConfigUtil.appConf.getConfig("ufm")

  val usingColumns: mutable.Seq[String] = ufmConf.getStringList("using-columns").asScala
  val inputPath: mutable.Seq[String] = ufmConf.getStringList("input-path").asScala
  val outputPath: String = ufmConf.getString("output-path")
  val prefix: String = ufmConf.getString("prefix")
  val joiner: String = ufmConf.getString("joiner")
  val outputHive: String = ufmConf.getString("output-hive")

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
      appName("UFMJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    import spark.implicits._

    var ufm = UPUtil.getUserProfile(dt, spark).selectExpr(usingColumns: _*)

    for (path <- inputPath) {
      val df = DFUtil.read(path.replace("{dt}", dt), spark)
      ufm = ufm.join(df, usingColumns, "left")
    }

    ufm = DFUtil.cast(ufm, ufm.columns, ConfManager.basicFeatureConf.tmap)
    ufm = DFUtil.fill(ufm, ufm.columns, ConfManager.basicFeatureConf.dmap)
    ufm = DFUtil.decimal(ufm, ufm.columns, ConfManager.basicFeatureConf.tmap)

    val path = outputPath.replace("{dt}", dt)
    DFUtil.save(ufm, path)

    val onlineCols = ConfManager.basicFeatureConf.fmap.
      filter(f => FeatureStatus.YES.equals(FeatureStatus.withName(f._2.status))).
      filter(f => FeatureMark.USER.equals(FeatureMark.withName(f._2.mark))).
      map(f => f._2.name).toSeq

    val onlineUFM = DFUtil.read(path, spark).rdd.map { r =>
      val key = prefix + usingColumns.map(c => r.getAs[Any](c).toString).mkString(joiner)
      val value = MapperUtil.mapper.writeValueAsString(r.getValuesMap(onlineCols).
        filter(a => ConfManager.basicFeatureConf.dmap(a._1) != a._2).
        map { a =>
          val f = ConfManager.basicFeatureConf.fmap(a._1)
          (f.alias, a._2)
        })
      (key, value)
    }.toDF("key", "value")

    DFUtil.saveDataFrameAsHiveTable(onlineUFM, outputHive, dt, spark)

    spark.stop()
  }

}
