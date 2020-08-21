package example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{from_json, explode}
import org.apache.spark.sql.types.{ArrayType, StructType, StructField, IntegerType}

object FromJsonDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("HelloWorld")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("HelloWorld")
      .getOrCreate()

    import spark.implicits._

    val df = Seq(
      ("a", "[{\"size\":1,\"width\":2,\"height\":3},{\"size\":4,\"width\":5,\"height\":6}]")
    ).toDF("key", "value")

    val schema = ArrayType(
      new StructType()
      .add(StructField("size", IntegerType))
      .add(StructField("width", IntegerType))
      .add(StructField("height", IntegerType))
    )
    df.select($"key", from_json($"value", schema)).show(false)

    df.select($"key", explode(from_json($"value", schema))).show(false)

    df.select($"key", explode(from_json($"value", schema)).alias("value"))
      .select($"key",
        $"value.size".as("size"),
        $"value.width".as("width"),
        $"value.height".as("height"))
      .show(false)
  }

}
