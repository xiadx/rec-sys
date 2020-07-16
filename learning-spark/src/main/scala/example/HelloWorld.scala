package example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object HelloWorld {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("HelloWorld")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("HelloWorld")
      .getOrCreate()

    import spark.implicits._

//    var df = Seq(
//      ("a", "20200701", "[{\"n\":1,\"c\":2},{\"n\":3,\"c\":4}]")
//    ).toDF("open_udid", "dt", "profile")
//
//    df.printSchema()
//
//    df = df.select("open_udid", "dt", "profile").rdd.flatMap { r =>
//      val ml = mapper.readValue(r.getAs[String]("profile"), classOf[Array[Map[String, Any]]])
//      ml.map { m =>
//        (r.getAs[String]("open_udid"), r.getAs[String]("dt"), m.getOrElse("n", -1).toString.toInt, m.getOrElse("c", -1).toString.toInt)
//      }
//    }.toDF("open_udid", "dt", "n", "c")
//
//    df.show(false)

    val df = Seq(
      ("a", "b"),
      ("c", "d"),
      ("e", "f")
    ).toDF("key", "value")

    val rdd = df.repartition(2).rdd.mapPartitions { rows =>
      rows.map { row =>
        val key = row.getAs[String]("key")
        val value = row.getAs[String]("value")
        (key, value)
      }
    }

    println(rdd.take(1)(0)._1)
    println(rdd.take(1)(0)._2)
  }

}
