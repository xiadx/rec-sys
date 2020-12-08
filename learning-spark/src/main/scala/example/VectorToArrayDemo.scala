package example

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{col, struct, udf}
import org.apache.spark.sql.types.{StructType, StructField, StringType, DoubleType}

object VectorToArrayDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("VectorToArrayDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("VectorToArrayDemo")
      .getOrCreate()

//    import spark.implicits._
//
//    var df = Seq(
//      ("a", 0.1, 1, 10),
//      ("b", 0.2, 2, 20),
//      ("c", 0.3, 3, 30)
//    ).toDF("name", "f1", "f2", "f3")
//
//    df = vector(df, Array("f1", "f2", "f3"), "vector")
//    df = vec2arr(df, Array("vector"), Array("array"))
//    df = vec2arr(df, "vector", "arr")
//    df.show(false)

    val schema = StructType(StructField("id", StringType, true) ::
      StructField("name", StringType, true) ::
      StructField("f1", DoubleType, true) ::
      StructField("f2", DoubleType, true):: Nil)

    val lr = new java.util.ArrayList[Row](java.util.Arrays.asList[Row](
      Row("i1", "a", 0.1, 1.0),
      Row("i2", "b", 0.2, 2.0),
      Row("i3", null, 0.3, 3.0),
      Row("i4", "d", 0.4, 4.0)
    ))

    var df = spark.createDataFrame(lr, schema)
    df = vector(df, Array("f1", "f2"), "r1")
    df.show(false)
  }

  def vector(df: DataFrame, usingColumns: Array[String], outputColumns: String): DataFrame = {
    def vec: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(usingColumns.map { c =>
        r.get(r.fieldIndex(c)).toString.toDouble
      }).toSparse
    })
    df.withColumn(outputColumns, vec(struct(usingColumns.map(c => col(c)): _*)))
  }

  def vec2arr(df: DataFrame, usingColumns: Array[String], outputColumns: Array[String]): DataFrame = {
    def v2a: UserDefinedFunction = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      v.toArray
    })
    var rst = df
    for (i <- usingColumns.indices) {
      rst = rst.withColumn(outputColumns(i), v2a(col(usingColumns(i))))
    }
    rst
  }

  def vec2arr(df: DataFrame, usingColumns: String, outputColumns: String): DataFrame = {
    def v2a: UserDefinedFunction = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      v.toArray
    })
    df.withColumn(outputColumns, v2a(col(usingColumns)))
  }

}
