package dc

import org.apache.spark.sql.{SparkSession, DataFrame}

object IPUtil {

  /**
   * root
   * |-- itemid: long (nullable = true)
   * |-- itemtype: integer (nullable = true)
   * |-- uid: string (nullable = true)
   * |-- url: string (nullable = true)
   * |-- title: string (nullable = true)
   * |-- viewuv_30: long (nullable = true)
   * |-- ctime: string (nullable = true)
   * |-- ptime: string (nullable = true)
   * |-- mtime: string (nullable = true)
   * |-- rvtime: string (nullable = true)
   * |-- mainmdd: string (nullable = true)
   * |-- poiid: string (nullable = true)
   * |-- music: string (nullable = true)
   * |-- musictitle: string (nullable = true)
   * |-- feature_list: string (nullable = true)
   * |-- tags: string (nullable = true)
   * |-- template_id: string (nullable = true)
   * |-- sdate: string (nullable = true)
   * |-- edate: string (nullable = true)
   * |-- baid: string (nullable = true)
   * |-- gaids: string (nullable = true)
   * |-- b_score: string (nullable = true)
   * |-- note_focus_time: string (nullable = true)
   * |-- s_title: string (nullable = true)
   * |-- s_content: string (nullable = true)
   * |-- keywords: string (nullable = true)
   * |-- readable: string (nullable = true)
   * |-- simhash: string (nullable = true)
   * |-- md5code: string (nullable = true)
   * |-- use_template: string (nullable = true)
   * |-- timeline: array (nullable = true)
   * |    |-- element: array (containsNull = true)
   * |    |    |-- element: string (containsNull = true)
   * |-- level: integer (nullable = true)
   * |-- del: integer (nullable = true)
   * |-- photonum: integer (nullable = true)
   * |-- videonum: integer (nullable = true)
   * |-- ctruv_7: double (nullable = true)
   * |-- contentlenth: integer (nullable = true)
   * |-- favorite: integer (nullable = true)
   * |-- vote: integer (nullable = true)
   * |-- reply: integer (nullable = true)
   * |-- pv: integer (nullable = true)
   * |-- source: integer (nullable = true)
   * |-- play_times: integer (nullable = true)
   * |-- poll_num: integer (nullable = true)
   * |-- follow_num: integer (nullable = true)
   * |-- g_treasure: integer (nullable = true)
   * |-- lat: double (nullable = true)
   * |-- lng: double (nullable = true)
   * |-- al_static_score: double (nullable = true)
   * |-- duration: double (nullable = true)
   * |-- recommend_tags: array (nullable = true)
   * |    |-- element: string (containsNull = true)
   * |-- taglevel1: string (nullable = true)
   * |-- taglevel2: string (nullable = true)
   * |-- taglevel3: string (nullable = true)
   * |-- taglevel4: string (nullable = true)
   * |-- get_month: string (nullable = true)
   * |-- mdd_str: string (nullable = true)
   * |-- ar_poi: string (nullable = true)
   * |-- ctruv_30: double (nullable = true)
   * |-- amdd: string (nullable = true)
   * |-- contenttype: integer (nullable = true)
   * |-- score: double (nullable = true)
   * |-- month: string (nullable = true)
   * |-- titlelength: integer (nullable = true)
   * |-- contlength: integer (nullable = true)
   * |-- cont: string (nullable = true)
   * |-- is_good: string (nullable = true)
   * |-- viewuv_7: long (nullable = true)
   * |-- clickuv_7: long (nullable = true)
   * |-- viewuv: long (nullable = true)
   * |-- clickuv: long (nullable = true)
   * |-- cmonth: integer (nullable = true)
   * |-- pmonth: integer (nullable = true)
   * |-- fmdd: string (nullable = true)
   * |-- poiids_str: string (nullable = true)
   * |-- item_index: string (nullable = true)
   * |-- mdd_index: string (nullable = true)
   * |-- poi_index: string (nullable = true)
   * |-- tag1_index: string (nullable = true)
   * |-- tag2_index: string (nullable = true)
   * |-- tag3_index: string (nullable = true)
   * |-- tag4_index: string (nullable = true)
   * |-- mdd_id: long (nullable = true)
   * |-- mdd_name: string (nullable = true)
   * |-- mdd_type: long (nullable = true)
   * |-- province_country_mdd_id: string (nullable = true)
   * |-- is_foreign: long (nullable = true)
   * |-- state_mddid: long (nullable = true)
   * |-- province_mddid: long (nullable = true)
   * |-- city_mddid: long (nullable = true)
   * |-- item_version: string (nullable = true)
   * |-- flag: integer (nullable = true)
   * |-- view: long (nullable = true)
   * |-- click: long (nullable = true)
   * |-- view_30: long (nullable = true)
   * |-- click_30: long (nullable = true)
   * |-- ctr: double (nullable = true)
   * |-- ctr_30: double (nullable = true)
   * |-- default_bucket: string (nullable = true)
   * |-- test_bucket: string (nullable = true)
   * |-- pool_status: integer (nullable = true)
   * |-- statusb: integer (nullable = true)
   * |-- diffmonth: integer (nullable = true)
   * |-- sflag: integer (nullable = true)
   * |-- cflag: integer (nullable = true)
   * |-- ctruv: double (nullable = true)
   * |-- clickuv_30: long (nullable = true)
   * |-- timeflag: integer (nullable = true)
   * |-- status: string (nullable = true)
   * |-- highest_city_mdd_id: string (nullable = true)
   * |-- ismulticategory: integer (nullable = true)
   * |-- value_ugc_generallevel: string (nullable = true)
   * |-- value_ugc_generalscore: string (nullable = true)
   * |-- value_ugc_role: string (nullable = true)
   * |-- travelnote_fengshou_cnt: long (nullable = true)
   * |-- guide_cnt: long (nullable = true)
   * |-- guide_essence_cnt: long (nullable = true)
   * |-- weng_cnt: long (nullable = true)
   * |-- weng_quality_cnt: long (nullable = true)
   * |-- weng_selection_cnt: long (nullable = true)
   * |-- video_cnt: long (nullable = true)
   * |-- video_selection_cnt: long (nullable = true)
   * |-- video_quality_cnt: long (nullable = true)
   * |-- re_comment_guide_cnt: long (nullable = true)
   * |-- re_comment_travelnote_cnt: long (nullable = true)
   * |-- re_comment_weng_cnt: long (nullable = true)
   * |-- re_comment_video_cnt: long (nullable = true)
   * |-- re_vote_guide_cnt: long (nullable = true)
   * |-- re_vote_travelnote_cnt: long (nullable = true)
   * |-- re_vote_weng_cnt: long (nullable = true)
   * |-- re_vote_video_cnt: long (nullable = true)
   * |-- re_favorite_guide_cnt: long (nullable = true)
   * |-- re_favorite_travelnote_cnt: long (nullable = true)
   * |-- re_favorite_weng_cnt: long (nullable = true)
   * |-- re_favorite_video_cnt: long (nullable = true)
   * |-- ctr_7: double (nullable = true)
   * |-- view_7: long (nullable = true)
   * |-- click_7: long (nullable = true)
   * |-- 30_avg_play_duration: double (nullable = true)
   * |-- 30_avg_play_rate: double (nullable = true)
   * |-- 7_avg_play_duration: double (nullable = true)
   * |-- 7_avg_play_rate: double (nullable = true)
   * |-- 3_avg_play_duration: double (nullable = true)
   * |-- 3_avg_play_rate: double (nullable = true)
   * |-- 1_avg_play_duration: double (nullable = true)
   * |-- 1_avg_play_rate: double (nullable = true)
   * |-- isnewguidetag: integer (nullable = true)
   * |-- video_basicinfo: string (nullable = true)
   * |-- isblurvideo: integer (nullable = true)
   * |-- cover: string (nullable = true)
   * |-- image_feature_list: string (nullable = true)
   * |-- flow_layer_pre: string (nullable = true)
   * |-- flow_layer_now: string (nullable = true)
   * |-- cheatstatus: integer (nullable = true)
   * |-- dt: string (nullable = true)
   */
  def getItemProfile(dt: String, spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | recommend.rec_pool
        |where
        | dt='%s'
      """.stripMargin.format(dt)

    spark.sql(sql).
      withColumnRenamed("itemid", "item_id").
      withColumnRenamed("itemtype", "item_type")
  }

}
