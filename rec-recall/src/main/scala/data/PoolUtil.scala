package data

import org.apache.spark.sql.{DataFrame, SparkSession}

object PoolUtil {

  /**
   * root
   * |-- itemid: string (nullable = true)               内容itemid
   * |-- itemtype: string (nullable = true)             0-weng ; 1-note ; 2-qa ; 3-guide;6-video ，8-show
   * |-- flag: integer (nullable = true)                运营可发
   * |-- view: long (nullable = true)                   前天的曝光次数
   * |-- click: long (nullable = true)                  前天的点击次数
   * |-- oldview: long (nullable = true)                历史30天的曝光次数
   * |-- oldclick: long (nullable = true)               历史30天的曝光次数
   * |-- pool_status: integer (nullable = true)         进入推荐池后-池状态（是否推荐)
   * |-- flagupdatetime: string (nullable = true)       标签更新时间
   * |-- psupdatetime: string (nullable = true)         标识新增的时间
   * |-- statusupdatetime: string (nullable = true)     今日可露出状态更新时间
   * |-- ctr: double (nullable = true)                  前天点击率
   * |-- amdd: string (nullable = true)                 主要目的地
   * |-- ctime: string (nullable = true)                发布时间
   * |-- poiid: string (nullable = true)                用户关联poiid(一般1个)
   * |-- tags: string (nullable = true)                 机器标签 Tags 类型说明
   * |-- ar_tags: string (nullable = true)              人工标注的标签 Tags 类型说明
   * |-- searchcount: long (nullable = true)            目的地搜索次数
   * |-- search_mdd_name: string (nullable = true)      目的地名
   * |-- mdd_type: string (nullable = true)             目的地类型
   * |-- state_mddid: string (nullable = true)          国家目的地 id
   * |-- province_mddid: string (nullable = true)       省目的地id
   * |-- city_mddid: string (nullable = true)           城市目的地id
   * |-- totalsearch: long (nullable = true)            所有目的地累计搜索次数
   * |-- url: string (nullable = true)                  内容链接
   * |-- status: integer (nullable = true)              推荐池内容可推标示（1-可推，0-不可推）
   * |-- poiids_str: string (nullable = true)           目前召回在用的poiid列表（运营，用户）
   * |-- op_flag: long (nullable = true)                op_flag = 1表示运营引导标记
   * |-- op_topic_id: string (nullable = true)          运营：主题id
   * |-- op_tag_id: string (nullable = true)            运营：标签id
   * |-- active_months: string (nullable = true)        内容可用月份、目前均为空
   * |-- op_tag_mdds: string (nullable = true)          运营：标签目的地
   * |-- favo: string (nullable = true)                 收藏数
   * |-- vote: string (nullable = true)                 点赞数
   * |-- reply: string (nullable = true)                回复数
   * |-- cont: string (nullable = true)                 正文内容
   * |-- score: string (nullable = true)                内容基础质量分
   * |-- algo_status: integer (nullable = true)         判断内容优先级/目前仅根据时间
   * |-- otime: string (nullable = true)                运营pick时间变化
   * |-- contentlenth: integer (nullable = true)        文本字数
   * |-- ssr: string (nullable = true)                  是否精选内容
   * |-- g_treasure: string (nullable = true)           针对笔记：3(优质) ，4（精华）2（普通）
   * |-- pctr: string (nullable = true)                 目前未用
   * |-- item_version: string (nullable = true)         区分优质精华和扩量内容
   * |-- default_bucket: string (nullable = true)      （在做AB实验时）基准桶可推状态值
   * |-- test_bucket: string (nullable = true)          实验桶可推状态值
   * |-- basic_tags: string (nullable = true)           基础标签（中台打的标签）
   * |-- statusb: integer (nullable = true)             扩量状态
   * |-- test_bucket2: string (nullable = true)         扩量2.0
   * |-- ar_poi: string (nullable = true)               运营关联poi
   * |-- taglevel1: string (nullable = true)            标签树一级标签
   * |-- taglevel2: string (nullable = true)            标签树二级标签
   * |-- taglevel3: string (nullable = true)            标签树三级标签
   * |-- taglevel4: string (nullable = true)            标签树四级标签
   * |-- title: string (nullable = true)                标题
   * |-- titlelength: integer (nullable = true)         标题字数
   * |-- gaids: string (nullable = true)                有值代表金牌问答
   * |-- month: string (nullable = true)                内容月份
   * |-- diffmonth: integer (nullable = true)
   * |-- mdd_str: string (nullable = true)
   * |-- timeflag: string (nullable = true)             时间标签状态值（1-应季，0-当前时间段不可推，null-没有打时间标签）
   * |-- dt: string (nullable = true)                   日期分区
   */
  def getRecommendPool(dt: String, spark: SparkSession): DataFrame = {
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

  /**
   * root
   * |-- itemid: long (nullable = true)
   * |-- itemtype: integer (nullable = true)
   * |-- uid: string (nullable = true)
   * |-- url: string (nullable = true)
   * |-- title: string (nullable = true)
   * |-- content: string (nullable = true)
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
   * |-- isgood: string (nullable = true)
   * |-- level: integer (nullable = true)
   * |-- del: integer (nullable = true)
   * |-- photonum: integer (nullable = true)
   * |-- videonum: integer (nullable = true)
   * |-- titlelen: integer (nullable = true)
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
   * |-- mmdd: string (nullable = true)
   * |-- amdd: string (nullable = true)
   * |-- contenttype: integer (nullable = true)
   * |-- score: double (nullable = true)
   * |-- month: string (nullable = true)
   * |-- titlelength: integer (nullable = true)
   * |-- contlength: integer (nullable = true)
   * |-- cont: string (nullable = true)
   * |-- is_good: string (nullable = true)
   * |-- pnum: integer (nullable = true)
   * |-- vnum: integer (nullable = true)
   * |-- titlelength1: integer (nullable = true)
   * |-- contlength1: integer (nullable = true)
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
   * |-- oldview: long (nullable = true)
   * |-- oldclick: long (nullable = true)
   * |-- ctr: double (nullable = true)
   * |-- oldctr: double (nullable = true)
   * |-- default_bucket: string (nullable = true)
   * |-- test_bucket: string (nullable = true)
   * |-- pool_status: integer (nullable = true)
   * |-- statusb: integer (nullable = true)
   * |-- diffmonth: integer (nullable = true)
   * |-- sflag: integer (nullable = true)
   * |-- cflag: integer (nullable = true)
   * |-- write_flag: integer (nullable = true)
   * |-- flagtime: integer (nullable = true)
   * |-- timeflag: integer (nullable = true)
   * |-- status: string (nullable = true)
   * |-- highest_city_mdd_id: string (nullable = true)
   * |-- dt: string (nullable = true)
   */
  def getRecPool(dt: String, spark: SparkSession): DataFrame = {
    val sql =
      """
        |select
        | *
        |from
        | recommend.rec_pool
        |where
        | dt='%s'
      """.stripMargin.format(dt)
    spark.sql(sql)
  }

}
