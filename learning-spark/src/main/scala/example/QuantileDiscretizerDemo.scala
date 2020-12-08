package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.ml.feature.QuantileDiscretizer
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.functions.{col, struct, udf}
import org.apache.spark.sql.expressions.UserDefinedFunction

object QuantileDiscretizerDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("QuantileDiscretizerDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("QuantileDiscretizerDemo")
      .getOrCreate()

    val data = Array((0, 18.0), (1, 19.0), (2, 8.0), (3, 5.0), (4, 2.2), (5, 0.0), (6, 0.0), (7, 0.0))
    var df = spark.createDataFrame(data).toDF("id", "hour")

//    val discretizer = new QuantileDiscretizer()
//      .setInputCol("hour")
//      .setOutputCol("result")
//      .setNumBuckets(3)
//
//    val result = discretizer.fit(df).transform(df)
//    result.show(false)

    df = discrete(df, Array("id", "hour"), "f1")
    df.printSchema()
    df.show(false)
  }

//  def discrete(df: DataFrame, usingColumns: String, outputColumns: String, typ: String = "integral", numBuckets: Int = 10, relativeError: Double = 0.001, decl: String = "%.6f"): DataFrame = {
//    val probs = (0.0 to 1.0 by 1.0 / numBuckets).toArray
//    val splits = df.stat.approxQuantile(usingColumns, probs, relativeError).distinct.sorted
//    def search: UserDefinedFunction = udf(f = (v: Any) => {
//      val n = splits.length
//      var left = 0
//      var right = n - 1
//      while (left <= right) {
//        val mid = (left + right) / 2
//        if (v.toString.toDouble > splits(mid)) left = mid + 1 else right = mid - 1
//      }
//      typ match {
//        case "bucket" => left
//        case "normalize" => (left.toDouble / n).formatted(decl).toDouble
//        case "integral" => {
//          val index = if (left == 0) 0 else left - 1
//          ((index.toDouble / n) + ((v.toString.toDouble - splits(index)) / (splits(index + 1) - splits(index)) / n)).formatted(decl).toDouble
//        }
//        case _ => left
//      }
//    })
//    df.withColumn(outputColumns, search(col(usingColumns)))
//  }

//  def discrete(df: DataFrame, usingColumns: Array[String], outputColumns: Array[String], typ: String = "normalize", numBuckets: Int = 10, relativeError: Double = 0.001, decl: String = "%.6f"): DataFrame = {
//    val probs = (0.0 to 1.0 by 1.0 / numBuckets).toArray
//    var rst = df
//    for (i <- usingColumns.indices) {
//      val splits = df.stat.approxQuantile(usingColumns(i), probs, relativeError).distinct.sorted
//      def search: UserDefinedFunction = udf(f = (v: Any) => {
//        val n = splits.length
//        var left = 0
//        var right = n - 1
//        while (left <= right) {
//          val mid = (left + right) / 2
//          if (v.toString.toDouble > splits(mid)) left = mid + 1 else right = mid - 1
//        }
//        typ match {
//          case "bucket" => left
//          case "normalize" => (left.toDouble / n).formatted(decl).toDouble
//          case "integral" => {
//            val index = if (left == 0) 0 else left - 1
//            ((index.toDouble / n) + ((v.toString.toDouble - splits(index)) / (splits(index + 1) - splits(index)) / n)).formatted(decl).toDouble
//          }
//          case _ => left
//        }
//      })
//      rst = rst.withColumn(outputColumns(i), search(col(usingColumns(i))))
//    }
//    rst
//  }

  def discrete(df: DataFrame, usingColumns: Array[String], outputColumns: String, typ: String = "bucket", numBuckets: Int = 10, relativeError: Double = 0.001, decl: String = "%.6f"): DataFrame = {
    val probs = (0.0 to 1.0 by 1.0 / numBuckets).toArray
    val splits = df.stat.approxQuantile(usingColumns, probs, relativeError).map(_.distinct.sorted)
    def disc: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(usingColumns.indices.toArray.map { i =>
        val v = r.get(r.fieldIndex(usingColumns(i)))
        val n = splits(i).length
        var left = 0
        var right = n - 1
        while (left <= right) {
          val mid = (left + right) / 2
          if (v.toString.toDouble > splits(i)(mid)) left = mid + 1 else right = mid - 1
        }
        typ match {
          case "bucket" => left
          case "normalize" => (left.toDouble / n).formatted(decl).toDouble
          case "integral" => {
            val index = if (left == 0) 0 else left - 1
            ((index.toDouble / n) + ((v.toString.toDouble - splits(i)(index)) / (splits(i)(index + 1) - splits(i)(index)) / n)).formatted(decl).toDouble
          }
          case _ => left
        }
      })
    })
    df.withColumn(outputColumns, disc(struct(usingColumns.map(c => col(c)): _*)))
  }

}
