package data

import org.apache.spark.sql.{DataFrame, SparkSession}

object MddUtil {

  /**
   * root
   * |-- mddid: string (nullable = true)
   * |-- name: string (nullable = true)
   * |-- en_name: string (nullable = true)
   * |-- native_name: string (nullable = true)
   * |-- other_name: string (nullable = true)
   * |-- pinyin_full: string (nullable = true)
   * |-- pinyin_short: string (nullable = true)
   * |-- hot: string (nullable = true)
   * |-- lat: string (nullable = true)
   * |-- lng: string (nullable = true)
   * |-- type: string (nullable = true)
   * |-- status: string (nullable = true)
   * |-- ctime: string (nullable = true)
   * |-- mtime: string (nullable = true)
   * |-- img: string (nullable = true)
   * |-- name_md5: string (nullable = true)
   */
  def getMddBase(spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | mdd.mdd_base
      """.stripMargin

    spark.sql(sql)
  }

}
