package tfrecord

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SparkSession, SaveMode}
import org.apache.spark.sql.functions.col

object TFRecordPartition {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("TFRecordPartition")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("TFRecordPartition")
      .getOrCreate()

    import spark.implicits._

    val df = Seq((8, "bat"),(8, "abc"), (1, "xyz"), (2, "aaa")).toDF("number", "word")
    df.show

    val path = "data/learning-spark/tfrecord-partition/test-output.tfrecord"

    // dump the tfrecords to files.
    df.repartition(3, col("number"))
      .write.mode(SaveMode.Overwrite)
      .partitionBy("number")
      .format("tfrecord")
      .option("recordType", "Example")
      .save(path)

    val newDf = spark.read.format("tfrecord").option("recordType", "Example").load(path)
    newDf.show
  }

}
