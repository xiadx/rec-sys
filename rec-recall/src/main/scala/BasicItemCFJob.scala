import org.apache.spark.sql.SparkSession

import data.DataUtil

object BasicItemCFJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.
      builder().
      appName("BasicItemCFJob").
      config("spark.sql.warehouse.dir", "hdfs://mfwCluster/user/hive/warehouse").
      config("spark.kryoserializer.buffer.max", "1024").
      enableHiveSupport().
      getOrCreate()
    DataUtil.getRecPool("20200101", spark).printSchema()
  }

}
