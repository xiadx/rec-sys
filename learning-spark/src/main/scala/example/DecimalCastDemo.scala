package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession, DataFrame}
import org.apache.spark.sql.functions.{col, udf}
import org.apache.spark.sql.types.{StringType, DoubleType, StructField, StructType}
import scala.collection.mutable

object DecimalCastDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("DecimalCastDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("DecimalCastDemo")
      .getOrCreate()

    val schema = StructType(StructField("id", StringType, true) ::
      StructField("name", StringType, true) ::
      StructField("f1", DoubleType, true) ::
      StructField("f2", DoubleType, true):: Nil)

    val lr = new java.util.ArrayList[Row](java.util.Arrays.asList[Row](
      Row("i1", "a", 3.1234, 0.112),
      Row("i2", "b", 2.5678, 0.115),
      Row("i3", null, 1.9012, 0.116),
      Row("i4", "d", 0.8765, 0.117)
    ))

    var df = spark.createDataFrame(lr, schema)
    df.show(false)

    val tmap = mutable.HashMap[String, String]("id" -> "string", "name" -> "string", "f1" -> "double", "f2" -> "double")
    val cols = Array[String]("id", "name", "f1", "f2")
    df = decimal(df, cols, tmap)
    df.show(false)
  }

  def decimal(df: DataFrame, cols: Array[String], tmap: mutable.HashMap[String, String], decl: String = "%.1f"): DataFrame = {
    val declFloat = udf((v: Float) => {
      v.formatted(decl).toFloat
    })
    val declDouble = udf((v: Double) => {
      v.formatted(decl).toDouble
    })
    var rst = df
    cols.filter(c => tmap(c).equals("float")).foreach { c =>
      rst = rst.withColumn(c, declFloat(col(c)))
    }
    cols.filter(c => tmap(c).equals("double")).foreach { c =>
      rst = rst.withColumn(c, declDouble(col(c)))
    }
    rst
  }

}
