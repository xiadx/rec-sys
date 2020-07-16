package example

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{col, udf}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.StandardScaler
import org.apache.spark.ml.feature.MinMaxScaler
import org.apache.spark.ml.feature.MaxAbsScaler
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.types.{StructType, StructField, StringType, DoubleType, IntegerType}

object FeatureScalerDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("FeatureScalerDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("FeatureScalerDemo")
      .getOrCreate()

    import spark.implicits._

    val schema = StructType(StructField("id", StringType, true) ::
        StructField("name", StringType, true) ::
        StructField("f1", DoubleType, true) ::
        StructField("f2", IntegerType, true):: Nil)

    var feature = Seq[(String, String, Integer, Int)](
      ("i1", "a", 1, 11),
      ("i2", "b", 2, 12),
      ("i3", null, null, 13),
      ("i4", "d", 4, 14)
    ).toDF("id", "name", "f1", "f2")

    feature = scaledFeatures(feature, Array("f1", "f2"), spark)

    feature.show(false)
  }

  def vectorToArray(vector: Vector): Array[Double] = {
    if (null != vector) {
      vector.toArray.map(x => x.formatted("%.6f").toDouble)
    } else {
      null
    }
  }

  def scaledFeatures(df: DataFrame, cols: Array[String], spark: SparkSession): DataFrame = {
    import spark.implicits._

    var rst = df.na.fill(0)

    val assembler = new VectorAssembler()
      .setInputCols(cols)
      .setOutputCol("features")
    rst = assembler.transform(rst)

    val standardScaler = new StandardScaler()
      .setInputCol("features")
      .setOutputCol("standardScaledFeatures")
      .setWithStd(true)
      .setWithMean(true)
    val standardScalerModel = standardScaler.fit(rst)
    rst = standardScalerModel.transform(rst)

    val vectorToArrayUDF = udf(vectorToArray _)

    rst = rst.withColumn("standardScaledFeaturesVec", vectorToArrayUDF($"standardScaledFeatures"))

    var cs = cols.map(c => c + "_ss").zipWithIndex.map { case (alias, idx) => col("standardScaledFeaturesVec").getItem(idx).as(alias).toString() }
    rst = rst.selectExpr(rst.columns ++ cs: _*)
    rst = rst.drop("standardScaledFeatures", "standardScaledFeaturesVec")

    val minMaxScaler = new MinMaxScaler()
      .setInputCol("features")
      .setOutputCol("minMaxScaledFeatures")
    val minMaxScalerModel = minMaxScaler.fit(rst)
    rst = minMaxScalerModel.transform(rst)

    rst = rst.withColumn("minMaxScaledFeaturesVec", vectorToArrayUDF($"minMaxScaledFeatures"))

    cs = cols.map(c => c + "_ms").zipWithIndex.map { case (alias, idx) => col("minMaxScaledFeaturesVec").getItem(idx).as(alias).toString() }
    rst = rst.selectExpr(rst.columns ++ cs: _*)
    rst = rst.drop("minMaxScaledFeatures", "minMaxScaledFeaturesVec")

    val maxAbsScaler = new MaxAbsScaler()
      .setInputCol("features")
      .setOutputCol("maxAbsScaledFeatures")
    val maxAbsScalerModel = maxAbsScaler.fit(rst)
    rst = maxAbsScalerModel.transform(rst)

    rst = rst.withColumn("maxAbsScaledFeaturesVec", vectorToArrayUDF($"maxAbsScaledFeatures"))

    cs = cols.map(c => c + "_as").zipWithIndex.map { case (alias, idx) => col("maxAbsScaledFeaturesVec").getItem(idx).as(alias).toString() }
    rst = rst.selectExpr(rst.columns ++ cs: _*)
    rst = rst.drop("maxAbsScaledFeatures", "maxAbsScaledFeaturesVec")

    rst = rst.drop("features")

    rst
  }

}
