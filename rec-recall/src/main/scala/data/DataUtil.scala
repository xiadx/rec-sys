package data

import org.apache.spark.sql.{DataFrame, SparkSession}

object DataUtil {

  def getWhiteList(dt: String, spark: SparkSession): DataFrame = {
    val whiteList = PoolUtil.getRecPool(dt, spark).
      filter("status = 1 and itemtype in ('0','1','8')").
      selectExpr("itemid as item_id", "itemtype as item_type")

    whiteList
  }

  /**
   * root
   * |-- open_udid: string (nullable = true)
   * |-- ctime: string (nullable = true)
   * |-- item_type: string (nullable = true)
   * |-- item_id: string (nullable = true)
   * |-- dt: string (nullable = true)
   */
  def getUserActionHour(startTime: String, endTime: String, spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | recommend.app_behavior_collection_hour
        |where
        | dt between '%s' and '%s'
      """.stripMargin.format(startTime, endTime)

    spark.sql(sql)
  }

  /**
   * root
   * |-- item_id: string (nullable = true)
   * |-- item_type: string (nullable = true)
   * |-- name: string (nullable = true)
   * |-- lat: double (nullable = true)
   * |-- lng: double (nullable = true)
   * |-- dt: string (nullable = true)
   */
  def getUserActionExtraHour(dt: String, spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | recommend.app_behavior_collection_extra_hour
        |where
        | dt='%s'
      """.stripMargin.format(dt)

    spark.sql(sql)
  }

}
