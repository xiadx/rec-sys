package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.conf.Configuration

object VectorSplitDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("VectorSplitDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("VectorSplitDemo")
      .getOrCreate()

    import spark.implicits._

    val df = Seq[(String, String, Double, Double, Double)](
      ("i1", "a", 1, 11, 1),
      ("i2", "b", 2, 12, 0),
      ("i3", "c", 3, 13, 1),
      ("i4", "d", 4, 14, 0)
    ).toDF("id", "name", "f1", "f2", "label")

    val featureColumns = Array("f1", "f2")
    val labelColumn = "label"
    val path = "data/learning-spark/vector-split"

    saveAsLibSVMFile(df, featureColumns, labelColumn, path)
  }

  def saveAsLibSVMFile(df: DataFrame, featureColumns: Array[String], labelColumns: String, path: String): Unit = {
    val rdd = df.rdd.map { r =>
      val label = r.getAs[Double](labelColumns)
      val features = Vectors.dense(featureColumns.map { c =>
        r.getAs[Double](c)
      }).toSparse
      LabeledPoint(label, features)
    }
    val fs = FileSystem.get(new Configuration())
    val output = new Path(path)
    if (fs.exists(output)) {
      fs.delete(output, true)
    }
    MLUtils.saveAsLibSVMFile(rdd, path)
  }

}
