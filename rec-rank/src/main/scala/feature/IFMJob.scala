package feature

import com.typesafe.config.Config
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import dc.IPUtil
import frame.ConfManager
import entity.{FeatureStatus, FeatureMark}
import utils.{ConfigUtil, TimeUtil, DFUtil, CMUtil, MapperUtil}
import scala.collection.JavaConverters._
import scala.collection.mutable

object IFMJob {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val ifmConf: Config = ConfigUtil.appConf.getConfig("ifm")

  val usingColumns: mutable.Seq[String] = ifmConf.getStringList("using-columns").asScala
  val inputPath: mutable.Seq[String] = ifmConf.getStringList("input-path").asScala
  val outputPath: String = ifmConf.getString("output-path")
  val prefix: String = ifmConf.getString("prefix")
  val joiner: String = ifmConf.getString("joiner")
  val outputHive: String = ifmConf.getString("output-hive")

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
      appName("IFMJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    import spark.implicits._

    var ifm = IPUtil.getItemProfile(dt, spark).selectExpr(usingColumns: _*)

    for (path <- inputPath) {
      val df = DFUtil.read(path.replace("{dt}", dt), spark)
      ifm = ifm.join(df, usingColumns, "left")
    }

    ifm = DFUtil.cast(ifm, ifm.columns, ConfManager.basicFeatureConf.tmap)
    ifm = DFUtil.fill(ifm, ifm.columns, ConfManager.basicFeatureConf.dmap)
    ifm = DFUtil.decimal(ifm, ifm.columns, ConfManager.basicFeatureConf.tmap)

    val path = outputPath.replace("{dt}", dt)
    DFUtil.save(ifm, path)

    val onlineCols = ConfManager.basicFeatureConf.fmap.
      filter(f => FeatureStatus.YES.equals(FeatureStatus.withName(f._2.status))).
      filter(f => FeatureMark.ITEM.equals(FeatureMark.withName(f._2.mark))).
      map(f => f._2.name).toSeq

    val onlineIFM = DFUtil.read(path, spark).rdd.map { r =>
      val key = prefix + usingColumns.map(c => r.getAs[Any](c).toString).mkString(joiner)
      val value = MapperUtil.mapper.writeValueAsString(r.getValuesMap(onlineCols).
        filter(a => ConfManager.basicFeatureConf.dmap(a._1) != a._2).
        map { a =>
          val f = ConfManager.basicFeatureConf.fmap(a._1)
          (f.alias, a._2)
        })
      (key, value)
    }.toDF("key", "value")

    DFUtil.saveDataFrameAsHiveTable(onlineIFM, outputHive, dt, spark)

    spark.stop()
  }

}
