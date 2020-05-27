package utils

import org.apache.spark.sql.{DataFrame, SparkSession}

object HiveUtil {

  def saveDataFrameAsHiveTable(rawInput: DataFrame, dbName: String, tableName: String, dt: String, spark: SparkSession): Unit = {
    import org.apache.spark.sql.functions.lit
    if (spark.catalog.tableExists(dbName + "." + tableName)) {
      val dropPartitionSql =
        """
          |alter table %s.%s drop if exists partition (dt='%s')
        """.stripMargin.format(dbName, tableName, dt)
      spark.sql(dropPartitionSql)
    }
    val withDateDF = rawInput.withColumn("dt", lit(dt))
    withDateDF.write
      .partitionBy("dt")
      .format("hive")
      .mode("append")
      .saveAsTable(dbName + "." + tableName)
    successToHive(dbName, tableName, dt, spark)
  }

  def successToHive(dbName: String, tableName: String, dt: String, spark: SparkSession): Unit = {
    val dropPartitionSql =
      """
        |alter table %s.%s drop if exists partition (dt='%s.done')
      """.stripMargin.format(dbName, tableName, dt)
    val addPartitionSql =
      """
        |alter table %s.%s add partition (dt='%s.done')
      """.stripMargin.format(dbName, tableName, dt)
    spark.sql(dropPartitionSql)
    spark.sql(addPartitionSql)
  }

}
