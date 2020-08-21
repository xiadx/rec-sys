package sample

import com.typesafe.config.Config
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.log4j.{Level, Logger}
import org.apache.commons.lang3.StringUtils
import utils.{ConfigUtil, DFUtil, TimeUtil, MapperUtil}
import entity.{OnlineFeature, RawFeature}
import frame.ConfManager
import scala.collection.JavaConverters._
import scala.collection.mutable

object SampleJob {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val sampleConf: Config = ConfigUtil.appConf.getConfig("sample")

  val scene: String = sampleConf.getString("scene")
  val model: String = sampleConf.getString("model")
  val version: String = sampleConf.getString("version")
  val inputPath: String = sampleConf.getString("input-path")
  val samplePath: String = sampleConf.getString("sample-path")
  val outputPath: String = sampleConf.getString("output-path")
  val npartitions: Int = sampleConf.getInt("npartitions")
  val dtPart: Boolean = sampleConf.getBoolean("dt-part")
  val daysListConf: Config = sampleConf.getConfig("days-list")
  val daysListTrain: Array[String] = getDaysList(daysListConf, "train")
  val daysListTest: Array[String] = getDaysList(daysListConf, "test")
  val labelColumns: String = sampleConf.getString("label")
  val sparseColumns: String = sampleConf.getString("sparse")
  val rawColumns: mutable.Seq[String] = sampleConf.getStringList("raw").asScala
  val ignoreColumns: mutable.Seq[String] = sampleConf.getStringList("ignore").asScala
  val whereCondition: String = sampleConf.getString("where")

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
      appName("SampleJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    val trainSample = getSample(daysListTrain, spark)
    val trainPath = replacePath(samplePath, "train")
    saveSample(trainSample, trainPath)

    val testSample = getSample(daysListTest, spark)
    val testPath = replacePath(samplePath, "test")
    saveSample(testSample, testPath)

    val trainOutputPath = replacePath(outputPath, "train")
    val testOutputPath = replacePath(outputPath, "test")

    if (dtPart) {
      daysListTrain.foreach { day =>
        DFUtil.saveAsLibSVMFile(trainSample.where(s"dt='$day'"), labelColumns, sparseColumns, npartitions, s"$trainOutputPath/$day")
      }
      daysListTest.foreach { day =>
        DFUtil.saveAsLibSVMFile(testSample.where(s"dt='$day'"), labelColumns, sparseColumns, npartitions, s"$testOutputPath/$day")
      }
    } else {
      DFUtil.saveAsLibSVMFile(trainSample, labelColumns, sparseColumns, npartitions, trainOutputPath)
      DFUtil.saveAsLibSVMFile(testSample, labelColumns, sparseColumns, npartitions, testOutputPath)
    }

    spark.stop()
  }

  def replacePath(path: String, typ: String): String = {
    path.
      replace("{model}", model).
      replace("{version}", version).
      replace("[train|test]", typ)
  }

  def getDaysList(daysListConf: Config, typ: String): Array[String] = {
    val conf = daysListConf.getConfig(typ)
    val days: mutable.Seq[String] = conf.getStringList("days").asScala
    if (days.nonEmpty) {
      days.toArray
    } else {
      val start = conf.getString("start")
      val end = conf.getString("end")
      TimeUtil.getDaysList(start, end)
    }
  }

  def getSample(daysList: Array[String], spark: SparkSession): DataFrame = {
    val paths = daysList.map { day =>
      inputPath.replace("{dt}", day)
    }
    val merge = DFUtil.read(paths, spark)
    val whe = if (StringUtils.isBlank(whereCondition)) merge else merge.where(whereCondition)
    val sample = whe.selectExpr(ignoreColumns ++ rawColumns: _*)
    val featureColumns = sample.columns.diff(ignoreColumns)
    DFUtil.sparse(sample, featureColumns, sparseColumns)
  }

  def getConfig(): String = {
    val of = mutable.ArrayBuffer[OnlineFeature]()
    rawColumns.foreach { c =>
      of.append(OnlineFeatureFactory.getOnlineFeature(c))
    }
    MapperUtil.mapper.writeValueAsString(of)
  }

  def saveSample(sample: DataFrame, path: String): Unit = {
    DFUtil.save(sample, path)
  }

}
