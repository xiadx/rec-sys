package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{udf, col}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object OneHotDemo {

  case class OneHotFeature(name: String, value: Array[String], sep: String)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("OneHotDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("OneHotDemo")
      .getOrCreate()

    import spark.implicits._

    val schema = StructType(StructField("id", StringType, true) ::
      StructField("name", StringType, true) ::
      StructField("f1", StringType, true) ::
      StructField("f2", StringType, true):: Nil)

//    val lr = new java.util.ArrayList[Row]() {
//      {
//        add(Row("i1", "a", "x#y#z", "m,n"))
//        add(Row("i2", "b", "x", "m"))
//        add(Row("i3", null, "y#z", "n"))
//        add(Row("i4", "d", "z", null))
//      }
//    }

    val lr = new java.util.ArrayList[Row](java.util.Arrays.asList[Row](
      Row("i1", "a", "x#y#z", "m,n"),
      Row("i2", "b", "x", "m"),
      Row("i3", null, "y#z", "n"),
      Row("i4", "d", "z", null)
    ))

    val oneHotConf = Array(
      OneHotFeature("f1", Array("x", "y", "z"), "#"),
      OneHotFeature("f2", Array("m", "n"), ",")
    )

    var df = spark.createDataFrame(lr, schema)
    df = oneHot(df, oneHotConf)

    df.show()
  }

  def oneHot(df: DataFrame, oneHotConf: Array[OneHotFeature]): DataFrame = {
    var rst = df
    oneHotConf.foreach { f =>
      rst = oneHotColumn(rst, f.name, f.value, f.sep)
    }
    rst
  }

  def oneHotColumn(df: DataFrame, name: String, value: Array[String], sep: String, sym: String = "#"): DataFrame = {
    var rst = df
    value.foreach { v =>
      val containValue = udf { s: String =>
        if (null == s) 0 else if (s.split(sep).contains(v)) 1 else 0
      }
      val c = name + sym + v
      rst = rst.withColumn(c, containValue(col(name)))
    }
    rst
  }

}
