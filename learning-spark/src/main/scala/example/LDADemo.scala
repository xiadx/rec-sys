package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object LDADemo {

  val logger: Logger = Logger.getLogger("org")
  logger.setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("LDADemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("LDADemo")
      .getOrCreate()

    val dataset = spark.read.format("libsvm")
      .load("data/mllib/sample_lda_libsvm_data.txt")


  }



}
