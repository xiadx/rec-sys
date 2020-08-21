package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf

object ClickSequenceTruncationDemo {

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

    val log = Seq(
      ("u1", "i1", 1),
      ("u1", "i2", 2),
      ("u1", "i3", 3),
      ("u2", "i1", 2),
      ("u2", "i2", 3),
      ("u2", "i3", 4)
    ).toDF("uid", "iid", "time")

    val action = log.rdd.groupBy(r => r(0)).map {
      case (uid, actions) =>
        val history = actions.toArray.sortBy(r => r(2).toString.toDouble).reverse.map { r=>
          r(1).toString + "=" + r(2).toString
        }.mkString(";")
        (uid.toString, history)
    }.toDF("uid", "history")

    log.show(false)
    action.show(false)

    val truncation = udf((history: String, time: Int) => {
      history.split(";")
        .map { x => (x.split("=")(0), x.split("=")(1).toInt) }
        .filter(x => x._2 < time)
        .sortBy(x => x._2)
        .reverse.map { x=>
//        x._1.toString + "=" + x._2.toString
        "%s=%s".format(x._1.toString, x._2.toString)
      }.mkString(";")
    })

    val truncat = log.join(action, Seq("uid"), "left")
      .withColumn("history", truncation($"history", $"time"))

    truncat.show(false)
  }

}
