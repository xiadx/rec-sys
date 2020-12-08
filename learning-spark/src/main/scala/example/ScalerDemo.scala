package example

import org.apache.spark.ml.feature.{MaxAbsScaler, MinMaxScaler, Normalizer, StandardScaler}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{col, struct, udf}
import org.apache.spark.sql.types.{LongType, IntegerType, FloatType, DoubleType, StringType, ArrayType}

object ScalerDemo {

  val typesMap = Map(
    "long" -> LongType,
    "integer" -> IntegerType,
    "double" -> DoubleType,
    "float" -> FloatType,
    "string" -> StringType,
    "array" -> ArrayType
  )

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ScalerDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("ScalerDemo")
      .getOrCreate()

    import spark.implicits._

    var df = Seq(
      ("a", 0.1, 1, 10),
      ("b", 0.2, 2, 20),
      ("c", 0.3, 3, 30)
    ).toDF("name", "f1", "f2", "f3")

    df.printSchema()
    df = vector(df, Array("f1", "f2", "f3"), "vector")
    df = scaler(df, "vector", "stvector")
    df = decimal(df, "stvector", "stvector")
    df = decimal(df, "f1", "f1")
    df.printSchema()
    df.show(false)
  }

  def vector(df: DataFrame, usingColumns: Array[String], outputColumns: String): DataFrame = {
    def vec: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(usingColumns.map { c =>
        r.get(r.fieldIndex(c)).toString.toDouble
      })
    })
    df.withColumn(outputColumns, vec(struct(usingColumns.map(c => col(c)): _*)))
  }

  def scaler(df: DataFrame, usingColumns: String, outputColumns: String, typ: String = "standard"): DataFrame = {
    var rst = df
    rst = convertVectorColumnsToML(rst, usingColumns)
    typ match {
      case "minmax" => {
        val slr = new MinMaxScaler().setInputCol(usingColumns).setOutputCol(outputColumns)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
      case "standard" => {
        val slr = new StandardScaler().setInputCol(usingColumns).setOutputCol(outputColumns).setWithStd(true).setWithMean(true)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
      case "maxabs" => {
        val slr = new MaxAbsScaler().setInputCol(usingColumns).setOutputCol(outputColumns)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
      case _ => {
        val slr = new MinMaxScaler().setInputCol(usingColumns).setOutputCol(outputColumns)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
    }
    convertVectorColumnsFromML(rst, outputColumns)
  }

  def decimal(df: DataFrame, inputColumns: String, outputColumns: String, decl: String = "%.6f"): DataFrame = {
    val declFloat = udf((v: Float) => {
      v.formatted(decl).toFloat
    })
    val declDouble = udf((v: Double) => {
      v.formatted(decl).toDouble
    })
    val declVector = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      Vectors.dense(v.toArray.map(x => x.formatted(decl).toDouble))
    })
    val schema = df.schema
    var rst = df
    if (schema(inputColumns).dataType.getClass == typesMap("float").getClass) {
      rst = rst.withColumn(outputColumns, declFloat(col(inputColumns)))
    }
    if (schema(inputColumns).dataType.getClass == typesMap("double").getClass) {
      rst = rst.withColumn(outputColumns, declDouble(col(inputColumns)))
    }
    if (schema(inputColumns).dataType.getClass == classOf[org.apache.spark.mllib.linalg.VectorUDT]) {
      rst = rst.withColumn(outputColumns, declVector(col(inputColumns)))
    }
    rst
  }

  def convertVectorColumnsToML(df: DataFrame, cols: Array[String]): DataFrame = {
    MLUtils.convertVectorColumnsToML(df, cols: _*)
  }

  def convertVectorColumnsToML(df: DataFrame, cols: String): DataFrame = {
    MLUtils.convertVectorColumnsToML(df, cols)
  }

  def convertVectorColumnsFromML(df: DataFrame, cols: Array[String]): DataFrame = {
    MLUtils.convertVectorColumnsFromML(df, cols: _*)
  }

  def convertVectorColumnsFromML(df: DataFrame, cols: String): DataFrame = {
    MLUtils.convertVectorColumnsFromML(df, cols)
  }

}
