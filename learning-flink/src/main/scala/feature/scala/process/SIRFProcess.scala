package feature.scala.process

import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import feature.scala.event.SimplifyBaseEvent
import feature.scala.entity.SIRF
import feature.scala.utils.ConfigUtil

class SIRFProcess(val slide: Long) extends ProcessWindowFunction[
  SimplifyBaseEvent,
  SIRF,
  String,
  TimeWindow] {
  override def process(key: String,
                       ctx: Context,
                       vals: Iterable[SimplifyBaseEvent],
                       out: Collector[SIRF]): Unit = {
    val itemId: String = key.split("_")(0)
    val itemType: String = key.split("_")(1)
    val itemUniqueId: String = key

    var totalClickCount: Long = 0L
    var totalShowCount: Long = 0L
    var totalCtr1: Double = 0.0d
    var totalCtr2: Double = 0.0d

    var indexClickCount: Long = 0L
    var indexShowCount: Long = 0L
    var indexCtrV1: Double = 0.0d
    var indexCtrV2: Double = 0.0d

    var recClickCount: Long = 0L
    var recShowCount: Long = 0L
    var recCtrV1: Double = 0.0d
    var recCtrV2: Double = 0.0d

    var nearClickCount: Long = 0L
    var nearShowCount: Long = 0L
    var nearCtrV1: Double = 0.0d

    var wengDetailClickCount: Long = 0L
    var wengDetailShowCount: Long = 0L
    var wengDetailCtrV1: Double = 0.0d

    var videoDetailClickCount: Long = 0L
    var videoDetailShowCount: Long = 0L
    var videoDetailCtrV1: Double = 0.0d

    var mddClickCount: Long = 0L
    var mddShowCount: Long = 0L
    var mddCtrV1: Double = 0.0d

    var windowStartTime: Long = 0L
    var windowEndTime: Long = 0L
    var eventStartTime: Long = Long.MaxValue
    var eventEndTime: Long = Long.MinValue
    var toRedisTime: Long = 0L
    var toRedisYear: Int = 0
    var toRedisMonth: Int = 0
    var toRedisDay: Int = 0
    var toRedisWeek: Int = 0
    var toRedisHour: Int = 0
    var toRedisMinute: Int = 0
    var toRedisSecond: Int = 0
    var toRedisDelay: Long = 0L
    var deadlineTime: Long = 1000L

    for (v <- vals) {
      val eventCode = v.eventCode
      val eventType = v.eventType
      val channelId = v.channelId
      val eventTime = v.eventTime
      val windowStartTime = ctx.window.getStart
      val windowEndTime = ctx.window.getEnd

      if (eventTime > windowStartTime + slide && eventTime <= windowEndTime) {
        eventType match {
          case "1" => totalClickCount += 1
          case "0" => totalShowCount += 1
          case _ =>
        }

        eventCode match {
          case "click_index" =>
            indexClickCount += 1
            if ("55".equals(channelId)) {
              recClickCount += 1
            }
            if ("61".equals(channelId)) {
              nearClickCount += 1
            }
          case "show_index" =>
            indexShowCount += 1
            if ("55".equals(channelId)) {
              recShowCount += 1
            }
            if ("61".equals(channelId)) {
              nearShowCount += 1
            }
          case "click_weng_detail" => wengDetailClickCount += 1
          case "show_weng_detail" => wengDetailShowCount += 1
          case "click_video_detail" => videoDetailClickCount += 1
          case "show_video_detail" => videoDetailShowCount += 1
          case "click_mdd_index" => mddClickCount += 1
          case "show_mdd_index" => mddShowCount += 1
          case "page" =>
          case _ =>
        }
      }
      if (eventStartTime > eventTime) eventStartTime = eventTime
      if (eventEndTime < eventTime) eventEndTime = eventTime
    }

    val (recAlphaV1, recBetaV1) = ConfigUtil.getBayesianSmoothAlphaBeta("v1", itemType, "doubleFlow")
    val (recAlphaV2, recBetaV2) = ConfigUtil.getBayesianSmoothAlphaBeta("v2", itemType, "doubleFlow")
    val (nearAlphaV1, nearBetaV1) = ConfigUtil.getBayesianSmoothAlphaBeta("v1", itemType, "near")
    val (wdAlphaV1, wdBetaV1) = ConfigUtil.getBayesianSmoothAlphaBeta("v1", itemType, "wengDetail")
    val (vdAlphaV1, vdBetaV1) = ConfigUtil.getBayesianSmoothAlphaBeta("v1", itemType, "videoDetail")
    val (mddAlphaV1, mddBetaV1) = ConfigUtil.getBayesianSmoothAlphaBeta("v1", itemType, "mddFlow")

    totalCtr1 = (totalClickCount.toDouble + recAlphaV1) / (totalShowCount.toDouble + recAlphaV1 + recBetaV1)
    totalCtr2 = (totalClickCount.toDouble + recAlphaV2) / (totalShowCount.toDouble + recAlphaV2 + recBetaV2)
    indexCtrV1 = (indexClickCount.toDouble + recAlphaV1) / (indexShowCount.toDouble + recAlphaV1 + recBetaV1)
    indexCtrV2 = (indexClickCount.toDouble + recAlphaV2) / (indexShowCount.toDouble + recAlphaV2 + recBetaV2)
    recCtrV1 = (recClickCount.toDouble + recAlphaV1) / (recShowCount.toDouble + recAlphaV1 + recBetaV1)
    recCtrV2 = (recClickCount.toDouble + recAlphaV2) / (recShowCount.toDouble + recAlphaV2 + recBetaV2)
    nearCtrV1 = (nearClickCount.toDouble + nearAlphaV1) / (nearShowCount.toDouble + nearAlphaV1 + nearBetaV1)
    wengDetailCtrV1 = (wengDetailClickCount.toDouble + wdAlphaV1) / (wengDetailShowCount.toDouble + wdAlphaV1 + wdBetaV1)
    videoDetailCtrV1 = (videoDetailClickCount.toDouble + vdAlphaV1) / (videoDetailClickCount.toDouble + vdAlphaV1 + vdBetaV1)
    mddCtrV1 = (mddClickCount.toDouble + mddAlphaV1) / (mddShowCount.toDouble + mddAlphaV1 + mddBetaV1)

    out.collect(
      SIRF(itemId,
        itemType,
        itemUniqueId,
        totalClickCount,
        totalShowCount,
        totalCtr1,
        totalCtr2,
        indexClickCount,
        indexShowCount,
        indexCtrV1,
        indexCtrV2,
        recClickCount,
        recShowCount,
        recCtrV1,
        recCtrV2,
        nearClickCount,
        nearShowCount,
        nearCtrV1,
        wengDetailClickCount,
        wengDetailShowCount,
        wengDetailCtrV1,
        videoDetailClickCount,
        videoDetailShowCount,
        videoDetailCtrV1,
        mddClickCount,
        mddShowCount,
        mddCtrV1,
        windowStartTime,
        windowEndTime,
        eventStartTime,
        eventEndTime,
        toRedisTime,
        toRedisYear,
        toRedisMonth,
        toRedisDay,
        toRedisWeek,
        toRedisHour,
        toRedisMinute,
        toRedisSecond,
        toRedisDelay,
        deadlineTime)
    )
  }
}
