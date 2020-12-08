package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.functions.

object NaFillDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("NaFillDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("NaFillDemo")
      .getOrCreate()

    val schema = StructType(StructField("id", StringType, true) ::
      StructField("name", StringType, true) ::
      StructField("f1", StringType, true) ::
      StructField("f2", StringType, true):: Nil)

    val lr = new java.util.ArrayList[Row](java.util.Arrays.asList[Row](
      Row("i1", "a", "x#y#z", "m,n"),
      Row("i2", "b", "x", "m"),
      Row("i3", null, "y#z", "n"),
      Row("i4", "d", "z", null)
    ))

    var df = spark.createDataFrame(lr, schema)

    df.show(false)

    val m = Map("name" -> "-1", "f2" -> ",")
    df = df.na.fill(m)

    df.show(false)
  }

}
