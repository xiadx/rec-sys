package example

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{col, max, min, udf}

object MinMaxScalerDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("MinMaxScalerDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("MinMaxScalerDemo")
      .getOrCreate()

    import spark.implicits._

    var df = Seq(
      ("a", 0.1, 1, 10),
      ("b", 0.2, 2, 20),
      ("c", 0.3, 3, 30)
    ).toDF("name", "f1", "f2", "f3")

    df = minmax(df, Array("f1", "f2", "f3"), Array("f1_norm", "f2_norm", "f3_norm"))

    df.show(false)
  }

  def minmax(df: DataFrame, inputColumns: Array[String], outputColumns: Array[String]): DataFrame = {
    var rst = df
    for (i <- inputColumns.indices) {
      val icol = inputColumns(i)
      val ocol = outputColumns(i)
      val minv = df.select(icol).agg(min(col(icol))).collect()(0).get(0).toString.toDouble
      val maxv = df.select(icol).agg(max(col(icol))).collect()(0).get(0).toString.toDouble
      rst = rst.withColumn(ocol, minmax_scaler(minv, maxv)(col(icol)))
    }
    rst
  }

  def minmax_scaler(min_value: Double, max_value: Double): UserDefinedFunction = {
    udf((cur_value: Double) => {
      if (Math.abs(max_value - min_value) <= 0.000001) 0 else (cur_value - min_value) / (max_value - min_value)
    })
  }

}
