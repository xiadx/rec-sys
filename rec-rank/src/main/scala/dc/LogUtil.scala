package dc

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions.{lit, col}
import udfs.CommonUDF

object LogUtil {

  /**
   * root
   * |-- uid: long (nullable = true)
   * |-- abtest: string (nullable = true)
   * |-- device_id: string (nullable = true)
   * |-- device_type: string (nullable = true)
   * |-- device_brand: string (nullable = true)
   * |-- device_model: string (nullable = true)
   * |-- device_token: string (nullable = true)
   * |-- app_ver: string (nullable = true)
   * |-- app_code: string (nullable = true)
   * |-- sys_ver: string (nullable = true)
   * |-- isp: string (nullable = true)
   * |-- net_type: string (nullable = true)
   * |-- lat: double (nullable = true)
   * |-- lng: double (nullable = true)
   * |-- u_loc_mdd_id: long (nullable = true)
   * |-- intent_mddid: string (nullable = true)
   * |-- channel: array (nullable = true)
   * |    |-- element: string (containsNull = true)
   * |-- channel_origin: string (nullable = true)
   * |-- channel_code: string (nullable = true)
   * |-- client_ip: string (nullable = true)
   * |-- service_ip: string (nullable = true)
   * |-- sdk_ver: string (nullable = true)
   * |-- mfw_env: string (nullable = true)
   * |-- service_time: timestamp (nullable = true)
   * |-- service_hour: string (nullable = true)
   * |-- launch_guid: string (nullable = true)
   * |-- day_first: long (nullable = true)
   * |-- day_before: long (nullable = true)
   * |-- city: string (nullable = true)
   * |-- province: string (nullable = true)
   * |-- event_time: timestamp (nullable = true)
   * |-- event_guid: string (nullable = true)
   * |-- event_code: string (nullable = true)
   * |-- pos_id: string (nullable = true)
   * |-- pos_page: string (nullable = true)
   * |-- pos_channel_id: string (nullable = true)
   * |-- pos_module: string (nullable = true)
   * |-- pos_index: string (nullable = true)
   * |-- module_name: string (nullable = true)
   * |-- page_guid: string (nullable = true)
   * |-- parent_page_guid: string (nullable = true)
   * |-- uri: string (nullable = true)
   * |-- page_name: string (nullable = true)
   * |-- parent_uri: string (nullable = true)
   * |-- parent_name: string (nullable = true)
   * |-- referer_uri: string (nullable = true)
   * |-- show_cycle_id: string (nullable = true)
   * |-- item_source: string (nullable = true)
   * |-- duration: double (nullable = true)
   * |-- item: map (nullable = true)
   * |    |-- key: string
   * |    |-- value: string (valueContainsNull = true)
   * |-- related_mddid: long (nullable = true)
   * |-- item_uri: string (nullable = true)
   * |-- prm_id: string (nullable = true)
   * |-- is_algo: long (nullable = true)
   * |-- algo_sessionid: string (nullable = true)
   * |-- algo_scenario: string (nullable = true)
   * |-- algo_strategy: string (nullable = true)
   * |-- algo_recall: string (nullable = true)
   * |-- algo_rank: string (nullable = true)
   * |-- algo_cid: string (nullable = true)
   * |-- algo_reserve: string (nullable = true)
   * |-- is_click_event: long (nullable = true)
   * |-- item_id: string (nullable = true)
   * |-- item_type: string (nullable = true)
   * |-- basic_abtest: map (nullable = true)
   * |    |-- key: string
   * |    |-- value: string (valueContainsNull = true)
   * |-- biz_line: string (nullable = true)
   * |-- page_lv1: string (nullable = true)
   * |-- page_lv2: string (nullable = true)
   * |-- page_lv3: string (nullable = true)
   * |-- page_type: string (nullable = true)
   * |-- parent_lv1: string (nullable = true)
   * |-- parent_lv2: string (nullable = true)
   * |-- parent_lv3: string (nullable = true)
   * |-- parent_type: string (nullable = true)
   * |-- referer_lv1: string (nullable = true)
   * |-- referer_lv2: string (nullable = true)
   * |-- referer_lv3: string (nullable = true)
   * |-- referer_type: string (nullable = true)
   * |-- dt: string (nullable = true)
   */
  def getRecommendLog(startTime: String, endTime: String, spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | mfw_dwd.fact_flow_mobile_recommend_event_increment
        |where
        | dt>='%s' and dt<='%s'
      """.stripMargin.format(startTime, endTime)

    spark.sql(sql)
  }

  def getIndexSceneClickShow(startTime: String, endTime: String, spark: SparkSession): DataFrame = {
    val log = getRecommendLog(startTime, endTime, spark)
    val clickShow = log.
      selectExpr(
        "device_id as open_udid",
        "item_id",
        "item_type",
        "pos_channel_id",
        "show_cycle_id",
        "event_code",
        "is_click_event",
        "biz_line",
        "app_code",
        "dt"
      ).filter("app_code in ('com.mfw.roadbook','cn.mafengwo.www') and biz_line='rec' and pos_channel_id='55' and event_code in ('click_index','show_index')").
      withColumn("scene", lit("index")).
      withColumn("item_type", CommonUDF.itemTypeTrans(col("item_type")))

    clickShow
  }

}
