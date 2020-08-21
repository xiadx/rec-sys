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

    import spark.implicits._

    val df = Seq(
      ("a", 1, 2, 3),
      ("b", 4, 5, 6),
      ("c", 7, 8, 9)
    ).toDF("id", "c1", "c2", "c3")

    val probs = (0 until 2).map(_.toDouble / 2)
    val quantile = df.stat.approxQuantile(Array("c1", "c2", "c3"), probs.toArray, 0.0005)
    for (q <- quantile) {
      println(q.mkString(","))
    }
  }

}
