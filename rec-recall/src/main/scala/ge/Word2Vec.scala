package ge

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import utils.{ConfigUtil, TimeUtil}

object Word2Vec {

  val logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  val walkPath: String = ConfigUtil.word2vecConf.getString("walk-path")
  val modelPath: String = ConfigUtil.word2vecConf.getString("model-path")
  val vectorPath: String = ConfigUtil.word2vecConf.getString("vector-path")
  val readRepartition: Int = ConfigUtil.word2vecConf.getInt("read-repartition")
  val vectorRepartition: Int = ConfigUtil.word2vecConf.getInt("vector-repartition")
  val inputCol: String = ConfigUtil.word2vecConf.getString("input-col")
  val outputCol: String = ConfigUtil.word2vecConf.getString("output-col")
  val vectorSize: Int = ConfigUtil.word2vecConf.getInt("vector-size")
  val minCount: Int = ConfigUtil.word2vecConf.getInt("min-count")
  val windowSize: Int = ConfigUtil.word2vecConf.getInt("window-size")
  val numPartitions: Int = ConfigUtil.word2vecConf.getInt("num-partitions")

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
      appName("Word2Vec").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()

    val document = read(walkPath.replace("${dt}", dt), spark)
    val word2vec = build()
    val model = fit(document, word2vec)
    save(modelPath.replace("${dt}", dt), model)
    vector(vectorPath.replace("${dt}", dt), model)
  }

  def read(path: String, spark: SparkSession): DataFrame = {
    import spark.implicits._

    spark.sparkContext.textFile(path).repartition(readRepartition).map(_.split("\\s").toSeq).toDF(inputCol)
  }

  def build(): org.apache.spark.ml.feature.Word2Vec = {
    new org.apache.spark.ml.feature.Word2Vec().
      setInputCol(inputCol).
      setOutputCol(outputCol).
      setVectorSize(vectorSize).
      setMinCount(minCount).
      setWindowSize(windowSize).
      setNumPartitions(numPartitions)
  }

  def fit(input: DataFrame, model: org.apache.spark.ml.feature.Word2Vec): org.apache.spark.ml.feature.Word2VecModel = {
    model.fit(input)
  }

  def save(path: String, model: org.apache.spark.ml.feature.Word2VecModel): Unit = {
    model.save(path)
  }

  def vector(path: String, model: org.apache.spark.ml.feature.Word2VecModel): Unit = {
    model.getVectors.repartition(vectorRepartition).rdd.saveAsTextFile(path)
  }

}
