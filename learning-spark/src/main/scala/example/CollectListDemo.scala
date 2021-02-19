package example

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.functions._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object CollectListDemo {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("CollectListDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("CollectListDemo")
      .getOrCreate()

    import spark.implicits._

    val schema = StructType(StructField("id", StringType, true) ::
      StructField("name", StringType, true) ::
      StructField("f1", StringType, true) ::
      StructField("f2", StringType, true):: Nil)

    val lr = new java.util.ArrayList[Row](java.util.Arrays.asList[Row](
      Row("i1", "a", "fav", "m,n"),
      Row("i1", "a", "vote", "m"),
      Row("i2", "b", "comment", "n"),
      Row("i2", "b", "fav", null)
    ))

    var df = spark.createDataFrame(lr, schema)

    val cols = Array("f1", "f2")

    df = df.rdd.map { r =>
      mapper.writeValueAsString(r.getValuesMap(cols))
    }.toDF("value")

    df.show(false)

    spark.stop()

//    var df = spark.createDataFrame(lr, schema)
//    df.show(false)
//
////    df = df.groupBy("id", "name").agg("f1" -> "collect_list")
//    df = df.groupBy("id", "name").agg(collect_list(col("f1")).as("fs"))
//    df = df.withColumn("f3", get_fs(col("fs")))
//
//    df.show(false)
  }

  val get_fs: UserDefinedFunction = udf((fs: Seq[String]) => {
    var flag = 0
    for (f <- fs) {
      if (f.contains("z")) flag = 1
    }
    flag
  })

}
