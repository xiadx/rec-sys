package data

import org.apache.spark.sql.{DataFrame, SparkSession}

object PoiUtil {

  /**
   * root
   * |-- id: integer (nullable = true)
   * |-- poiid: long (nullable = true)
   * |-- zh_name: string (nullable = true)
   * |-- foreign_name: string (nullable = true)
   * |-- local_name: string (nullable = true)
   * |-- type_id: integer (nullable = true)
   * |-- type_id_sub: integer (nullable = true)
   * |-- img_link: string (nullable = true)
   * |-- lat: double (nullable = true)
   * |-- lng: double (nullable = true)
   * |-- parent: integer (nullable = true)
   * |-- mergeid: integer (nullable = true)
   * |-- mddid: integer (nullable = true)
   * |-- area_id: integer (nullable = true)
   * |-- rank: integer (nullable = true)
   * |-- admin_rank: integer (nullable = true)
   * |-- total_rank: integer (nullable = true)
   * |-- num_comment: integer (nullable = true)
   * |-- num_real_comment: integer (nullable = true)
   * |-- num_photo: integer (nullable = true)
   * |-- num_ginfo: integer (nullable = true)
   * |-- isrec: integer (nullable = true)
   * |-- flag: integer (nullable = true)
   * |-- create_from: integer (nullable = true)
   * |-- ctime: string (nullable = true)
   * |-- mtime: string (nullable = true)
   * |-- online_mddid: integer (nullable = true)
   * |-- otime: timestamp (nullable = true)
   */
  def getPoiBase(spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | mdd.poi_base
      """.stripMargin

    spark.sql(sql)
  }

}
