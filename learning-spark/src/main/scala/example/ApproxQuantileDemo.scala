package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object ApproxQuantileDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ApproxQuantileDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("ApproxQuantileDemo")
      .getOrCreate()
  }

}
