package train

import java.util

import com.typesafe.config.{Config, ConfigValue}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import ml.dmlc.xgboost4j.scala.spark.{XGBoostClassificationModel, XGBoostClassifier}
import utils.{ConfigUtil, DFUtil, TimeUtil}
import scala.collection.JavaConverters._
import scala.collection.mutable

object SparkXGBTrainer {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val trainConf: Config = ConfigUtil.appConf.getConfig("train")

  val scene: String = trainConf.getString("scene")
  val model: String = trainConf.getString("model")
  val version: String = trainConf.getString("version")
  val inputPath: String = trainConf.getString("input-path")
  val modelPath: String = trainConf.getString("model-path")
  val nativeBoosterPath: String = trainConf.getString("native-booster")
  val labelColumns: String = trainConf.getString("label")
  val sparseColumns: String = trainConf.getString("sparse")
  val predictionColumns: String = trainConf.getString("prediction")
  val metricName: String = trainConf.getString("metric")
  val ignoreColumns: mutable.Seq[String] = trainConf.getStringList("ignore").asScala
  val param: Map[String, Any] = getParam(trainConf.getConfig("param"))

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
      appName("SparkXGBTrainer").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    val trainPath = replacePath(inputPath, "train")
    val testPath = replacePath(inputPath, "test")
    val trainSample = getSample(trainPath, spark)
    val testSample = getSample(testPath, spark)

    val model = fit(trainSample)
    save(model, modelPath, nativeBoosterPath)
    val metric = evaluate(model, testSample)

    println(s"$metricName:$metric")

//    val model = XGBoostClassificationModel.load(modelPath)
//    val metric = evaluate(model, testSample)
//    println(s"$metricName:$metric")
  }

  def fit(trainSample: DataFrame): XGBoostClassificationModel = {
    val classifier = new XGBoostClassifier(param).
      setFeaturesCol(sparseColumns).
      setLabelCol(labelColumns)
    val model = classifier.fit(trainSample)
    model
  }

  def save(model: XGBoostClassificationModel, modelPath: String, nativeBoosterPath: String): Unit = {
    DFUtil.delete(modelPath)
    model.save(modelPath)
    model.nativeBooster.saveModel(nativeBoosterPath)
  }

  def load(modelPath: String): XGBoostClassificationModel = {
    XGBoostClassificationModel.load(modelPath)
  }

  def evaluate(model: XGBoostClassificationModel, testSample: DataFrame): Double = {
    val evaluator = new BinaryClassificationEvaluator().
      setLabelCol(labelColumns).
      setRawPredictionCol(predictionColumns).
      setMetricName(metricName)
    val metric = evaluator.evaluate(model.transform(testSample))
    metric
  }

  def replacePath(path: String, typ: String): String = {
    path.
      replace("{model}", model).
      replace("{version}", version).
      replace("[train|test]", typ)
  }

  def getSample(path: String, spark: SparkSession): DataFrame = {
    val sample = DFUtil.read(path, spark).
      selectExpr(ignoreColumns ++ Array(labelColumns) ++ Array(sparseColumns): _*)
    DFUtil.convertVectorColumnsToML(sample, Array(sparseColumns))
  }

//  def getParam(conf: Config): Map[String, Any] = {
//    val param = new mutable.HashMap[String, Any]()
//    val se: util.Set[util.Map.Entry[String, ConfigValue]] = conf.entrySet
//    val iter: util.Iterator[util.Map.Entry[String, ConfigValue]] = se.iterator
//    while (iter.hasNext) {
//      val t: util.Map.Entry[String, ConfigValue] = iter.next
//      param.put(t.getKey, t.getValue.unwrapped())
//    }
//    param.toMap
//  }

  def getParam(conf: Config): Map[String, Any] = {
    conf.root().unwrapped().asScala.toMap
  }

}
