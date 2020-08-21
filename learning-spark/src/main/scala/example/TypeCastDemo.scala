package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, DoubleType, StringType}

object TypeCastDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("TypeCastDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("TypeCastDemo")
      .getOrCreate()

    import spark.implicits._

    val df = Seq(
      ("a", "1", "2"),
      ("b", "3", "4")
    ).toDF("id", "c1", "c2")

    df.printSchema()

    df.selectExpr("id", "cast(c1 as long)", "cast(c2 as double)").printSchema()

    df.select($"id", $"c1".cast(LongType), $"c2".cast(DoubleType)).printSchema()

    var cols = new scala.collection.mutable.ArrayBuffer[String]
    cols += "id"
    cols += "cast(c1 as long)"
    cols += "cast(c2 as double)"
    df.selectExpr(cols: _*).printSchema()

    case class Feature(name: String,
                       dtype: String)

    val fm = new scala.collection.mutable.HashMap[String, Feature]
    fm.put("id", Feature("id", "string"))
    fm.put("c1", Feature("c1", "long"))
    fm.put("c2", Feature("c2", "double"))

    cols.clear()
    for ((n, f) <- fm) {
      cols += "cast(%s as %s)".format(f.name, f.dtype)
    }
    df.selectExpr(cols: _*).printSchema()

    val dtypeMap = Map("string" -> StringType, "long" -> LongType, "double" -> DoubleType)
    val cs = fm.keys.map { n =>
      org.apache.spark.sql.functions.col(fm(n).name).cast(dtypeMap(fm(n).dtype))
    }.toSeq
    df.select(cs: _*).printSchema()
  }

}
