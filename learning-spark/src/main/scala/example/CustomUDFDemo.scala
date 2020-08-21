package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions.{udf, col}
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

object CustomUDFDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("CustomUDFDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("CustomUDFDemo")
      .getOrCreate()

    val schema = StructType(StructField("id", StringType, true) ::
      StructField("name", StringType, true) ::
      StructField("f1", DoubleType, true) ::
      StructField("f2", DoubleType, true):: Nil)

    val lr = new java.util.ArrayList[Row](java.util.Arrays.asList[Row](
      Row("i1", "a", 0.1, 0.3),
      Row("i2", "b", null, 0.4),
      Row("i3", null, 0.3, 0.5),
      Row("i4", "d", 0.2, null)
    ))

    var df = spark.createDataFrame(lr, schema)

    def multiply(factor: Int = 2): UserDefinedFunction = {
      val multi = udf((v: Double) => {
        factor * v
      })
      multi
    }

    df.withColumn("f1", multiply()(col("f1"))).show(false)
  }

}
