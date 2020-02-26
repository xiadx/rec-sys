package data

import org.apache.spark.sql.{DataFrame, SparkSession}

object DataUtil {

  def getRecPool(dt: String, spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | recommend.recommend_pool
        |where
        | dt='%s'
      """.stripMargin.format(dt)
    spark.sql(sql)
  }

  def getWhiteList(dt: String, spark: SparkSession): DataFrame = {
    getRecPool(dt, spark)
      .select("status = 1")
  }

}
