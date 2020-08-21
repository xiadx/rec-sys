package example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf

object ObjectMapperDemo {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ObjectMapperDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("ObjectMapperDemo")
      .getOrCreate()

    import spark.implicits._

    var df = Seq(
      ("a", "{\"size\":1,\"width\":2,\"height\":3}")
    ).toDF("key", "value")

    val getSize = udf((value: String) => {
      val m = mapper.readValue(value, classOf[Map[String, Int]])
      m.getOrElse("size", -1)
    })
    val getHeight = udf((value: String) => {
      val m = mapper.readValue(value, classOf[Map[String, Int]])
      m.getOrElse("height", -1)
    })
    val getWidth = udf((value: String) => {
      val m = mapper.readValue(value, classOf[Map[String, Int]])
      m.getOrElse("width", -1)
    })

    df = df.withColumn("size", getSize($"value"))
      .withColumn("width", getWidth($"value"))
      .withColumn("height", getHeight($"value"))

    df.show(false)
  }

}
