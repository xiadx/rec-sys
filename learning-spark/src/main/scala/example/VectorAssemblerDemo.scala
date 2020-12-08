package example

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{col, struct, udf}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.collection.mutable

object VectorAssemblerDemo {

  case class Feature(name: String, typ: String, value: String)

  def getFeatureMap(): mutable.HashMap[String, Feature] = {
    val fmap = new mutable.HashMap[String, Feature]()
    fmap.put("a", Feature("a", "float", "0.0"))
    fmap.put("b", Feature("b", "long", "0"))
    fmap.put("c", Feature("c", "long", "0"))
    fmap
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("VectorAssemblerDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("VectorAssemblerDemo")
      .getOrCreate()

    import spark.implicits._

    var df = Seq(
      ("a", 0.1, 1, 10),
      ("b", 0.2, 2, 20),
      ("c", 0.3, 3, 30)
    ).toDF("name", "f1", "f2", "f3")

    df = vector(df, Array("f1", "f2", "f3"), "array")
    df.show(false)
  }

  def vector(df: DataFrame, usingColumns: Array[String], outputColumns: String): DataFrame = {
    def vec: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(usingColumns.map { c =>
        r.get(r.fieldIndex(c)).toString.toDouble
      }).toArray
    })
    df.withColumn(outputColumns, vec(struct(usingColumns.map(c => col(c)): _*)))
  }

}
