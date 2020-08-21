package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.{col, struct, udf}
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.apache.spark.mllib.linalg.VectorUDT
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.regression.LabeledPoint

object LibSVMDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("LibSVMDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("LibSVMDemo")
      .getOrCreate()

    val schema = StructType(StructField("id", StringType, true) ::
      StructField("label", DoubleType, true) ::
      StructField("f1", DoubleType, true) ::
      StructField("f2", DoubleType, true):: Nil)

    val lr = new java.util.ArrayList[Row](java.util.Arrays.asList[Row](
      Row("i1", 1.0, 0.1, 0.3),
      Row("i2", 0.0, null, 0.0),
      Row("i3", 1.0, 0.3, 0.5),
      Row("i4", 0.0, 0.2, null)
    ))

    var df = spark.createDataFrame(lr, schema)
    df = df.na.fill(0.0)
    df.show(false)

    val labelColumns = "label"
    val featureColumns = Array("f1", "f2")
    val sparseColumns = "features"
    df = sparse(df, featureColumns, sparseColumns)
    df.show(false)

    println(saveAsLibSVMFile(df, labelColumns, sparseColumns).take(1))
  }

//  def sparse(df: DataFrame, labelColumns: String, featureColumns: Array[String], sparseColumns: String, spark: SparkSession): DataFrame = {
//    val li = df.columns.indexOf(labelColumns)
//    val fi = featureColumns.map(df.columns.indexOf(_))
//    val schema = df.schema
//    val rdd = df.rdd.map { r =>
//      Row.merge(r, Row(Vectors.dense(fi.map { i =>
//        r.get(i).toString.toDouble
//      }).toSparse))
//    }
//    spark.createDataFrame(rdd, schema.add(StructField(sparseColumns, new VectorUDT, true)))
//  }

  def sparse(df: DataFrame, featureColumns: Array[String], sparseColumns: String): DataFrame = {
    def spar: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(featureColumns.map { c =>
        r.get(r.fieldIndex(c)).toString.toDouble
      }).toSparse
    })
    df.withColumn(sparseColumns, spar(struct(featureColumns.map(c => col(c)): _*)))
  }

  def saveAsLibSVMFile(df: DataFrame, labelColumns: String, sparseColumns: String): RDD[LabeledPoint] = {
    val rdd = df.rdd.map { r =>
      LabeledPoint(
        r.get(r.fieldIndex(labelColumns)).toString.toDouble,
        r.getAs[Vector](sparseColumns)
      )
    }
    rdd
  }

}
