package merge

import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}
import utils.{ConfigUtil, DFUtil, TimeUtil}
import frame.ConfManager
import scala.collection.JavaConverters._
import scala.collection.mutable

object MergeJob {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val mergeConf: Config = ConfigUtil.appConf.getConfig("merge")

  val labelPath: String = mergeConf.getString("label.path")
  val ufmPath: String = mergeConf.getString("ufm.path")
  val ifmPath: String = mergeConf.getString("ifm.path")
  val ufmUsingColumns: mutable.Seq[String] = mergeConf.getStringList("ufm.using-columns").asScala
  val ifmUsingColumns: mutable.Seq[String] = mergeConf.getStringList("ifm.using-columns").asScala
  val outputPath: String = mergeConf.getString("output-path")

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
      appName("MergeJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    val label = DFUtil.read(labelPath.replace("{dt}", dt), spark)
    val ufm = DFUtil.read(ufmPath.replace("{dt}", TimeUtil.getFewDaysAgo(dt, 1)), spark)
    val ifm = DFUtil.read(ifmPath.replace("{dt}", TimeUtil.getFewDaysAgo(dt, 1)), spark)

    var merge = label.join(ufm, ufmUsingColumns, "left").join(ifm, ifmUsingColumns, "left")
    merge = DFUtil.fill(merge, merge.columns, ConfManager.basicFeatureConf.dmap)
    val path = outputPath.replace("{dt}", dt)
    DFUtil.save(merge, path)

    spark.stop()
  }

}
