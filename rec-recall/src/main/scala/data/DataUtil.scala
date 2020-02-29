package data

import org.apache.spark.sql.{DataFrame, SparkSession}

object DataUtil {

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
      .filter("status = 1 and itemtype in ('0','1','6')")
      .selectExpr("itemid as item_id", "itemtype as item_type")
  }

  /**
   * root
   * |-- open_udid: string (nullable = true)
   * |-- ctime: string (nullable = true)
   * |-- item_type: string (nullable = true)
   * |-- item_id: string (nullable = true)
   * |-- dt: string (nullable = true)
   */
  def getUserAction(startTime: String, endTime: String, spark: SparkSession): DataFrame = {
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

}
