package feature.scala.process

import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import feature.scala.event.SimplifyBaseEvent
import feature.scala.entity.SURF

class SURFProcess(val slide: Long) extends ProcessWindowFunction[
  SimplifyBaseEvent,
  SURF,
  String,
  TimeWindow] {
  override def process(key: String,
                       ctx: Context,
                       vals: Iterable[SimplifyBaseEvent],
                       out: Collector[SURF]): Unit = {
    val openUdid: String = key

    var totalClickCount: Long = 0L
    var totalShowCount: Long = 0L
    var totalCtr: Double = 0.0d

    var indexClickCount: Long = 0L
    var indexShowCount: Long = 0L
    var indexCtr: Double = 0.0d

    var recClickCount: Long = 0L
    var recShowCount: Long = 0L
    var recCtr: Double = 0.0d

    var nearClickCount: Long = 0L
    var nearShowCount: Long = 0L
    var nearCtr: Double = 0.0d

    var wengDetailClickCount: Long = 0L
    var wengDetailShowCount: Long = 0L
    var wengDetailCtr: Double = 0.0d

    var videoDetailClickCount: Long = 0L
    var videoDetailShowCount: Long = 0L
    var videoDetailCtr: Double = 0.0d

    var mddClickCount: Long = 0L
    var mddShowCount: Long = 0L
    var mddCtr: Double = 0.0d

    var totalWengClickCount: Long = 0L
    var totalWengShowCount: Long = 0L
    var totalWengCtr: Double = 0.0d
    var totalTravelClickCount: Long = 0L
    var totalTravelShowCount: Long = 0L
    var totalTravelCtr: Double = 0.0d
    var totalVideoClickCount: Long = 0L
    var totalVideoShowCount: Long = 0L
    var totalVideoCtr: Double = 0.0d

    var indexWengClickCount: Long = 0L
    var indexWengShowCount: Long = 0L
    var indexWengCtr: Double = 0.0d
    var indexTravelClickCount: Long = 0L
    var indexTravelShowCount: Long = 0L
    var indexTravelCtr: Double = 0.0d
    var indexVideoClickCount: Long = 0L
    var indexVideoShowCount: Long = 0L
    var indexVideoCtr: Double = 0.0d

    var recWengClickCount: Long = 0L
    var recWengShowCount: Long = 0L
    var recWengCtr: Double = 0.0d
    var recTravelClickCount: Long = 0L
    var recTravelShowCount: Long = 0L
    var recTravelCtr: Double = 0.0d
    var recVideoClickCount: Long = 0L
    var recVideoShowCount: Long = 0L
    var recVideoCtr: Double = 0.0d

    var nearWengClickCount: Long = 0L
    var nearWengShowCount: Long = 0L
    var nearWengCtr: Double = 0.0d
    var nearTravelClickCount: Long = 0L
    var nearTravelShowCount: Long = 0L
    var nearTravelCtr: Double = 0.0d
    var nearVideoClickCount: Long = 0L
    var nearVideoShowCount: Long = 0L
    var nearVideoCtr: Double = 0.0d

    var wengDetailWengClickCount: Long = 0L
    var wengDetailWengShowCount: Long = 0L
    var wengDetailWengCtr: Double = 0.0d
    var wengDetailTravelClickCount: Long = 0L
    var wengDetailTravelShowCount: Long = 0L
    var wengDetailTravelCtr: Double = 0.0d
    var wengDetailVideoClickCount: Long = 0L
    var wengDetailVideoShowCount: Long = 0L
    var wengDetailVideoCtr: Double = 0.0d

    var videoDetailWengClickCount: Long = 0L
    var videoDetailWengShowCount: Long = 0L
    var videoDetailWengCtr: Double = 0.0d
    var videoDetailTravelClickCount: Long = 0L
    var videoDetailTravelShowCount: Long = 0L
    var videoDetailTravelCtr: Double = 0.0d
    var videoDetailVideoClickCount: Long = 0L
    var videoDetailVideoShowCount: Long = 0L
    var videoDetailVideoCtr: Double = 0.0d

    var mddWengClickCount: Long = 0L
    var mddWengShowCount: Long = 0L
    var mddWengCtr: Double = 0.0d
    var mddTravelClickCount: Long = 0L
    var mddTravelShowCount: Long = 0L
    var mddTravelCtr: Double = 0.0d
    var mddVideoClickCount: Long = 0L
    var mddVideoShowCount: Long = 0L
    var mddVideoCtr: Double = 0.0d

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
    var deadlineTime: Long = 0L

    for (v <- vals) {
      val eventCode = v.eventCode
      val eventType = v.eventType
      val channelId = v.channelId
      val eventTime = v.eventTime
      val itemType = v.itemType
      val windowStartTime = ctx.window.getStart
      val windowEndTime = ctx.window.getEnd

      if (eventTime > windowStartTime + slide && eventTime <= windowEndTime) {
        itemType match {

          case "0" =>
            eventType match {
              case "1" =>
                totalClickCount += 1
                totalWengClickCount += 1
              case "0" =>
                totalShowCount += 1
                totalWengShowCount += 1
              case _ =>
            }
            eventCode match {
              case "click_index" =>
                indexClickCount += 1
                indexWengClickCount += 1
                if ("55".equals(channelId)) {
                  recClickCount += 1
                  recWengClickCount += 1
                }
                if ("61".equals(channelId)) {
                  nearClickCount += 1
                  nearWengClickCount += 1
                }
              case "show_index" =>
                indexShowCount += 1
                indexWengShowCount += 1
                if ("55".equals(channelId)) {
                  recShowCount += 1
                  recWengShowCount += 1
                }
                if ("61".equals(channelId)) {
                  nearShowCount += 1
                  nearWengShowCount += 1
                }
              case "click_weng_detail" =>
                wengDetailClickCount += 1
                wengDetailWengClickCount += 1
              case "show_weng_detail" =>
                wengDetailShowCount += 1
                wengDetailVideoShowCount += 1
              case "click_video_detail" =>
                videoDetailClickCount += 1
                videoDetailWengClickCount += 1
              case "show_video_detail" =>
                videoDetailShowCount += 1
                videoDetailWengShowCount += 1
              case "click_mdd_index" =>
                mddClickCount += 1
                mddWengClickCount += 1
              case "show_mdd_index" =>
                mddShowCount += 1
                mddWengShowCount += 1
              case "page" =>
              case _ =>
            }

          case "1" =>
            eventType match {
              case "1" =>
                totalClickCount += 1
                totalTravelClickCount += 1
              case "0" =>
                totalShowCount += 1
                totalTravelShowCount += 1
              case _ =>
            }
            eventCode match {
              case "click_index" =>
                indexClickCount += 1
                indexTravelClickCount += 1
                if ("55".equals(channelId)) {
                  recClickCount += 1
                  recTravelClickCount += 1
                }
                if ("61".equals(channelId)) {
                  nearClickCount += 1
                  nearTravelClickCount += 1
                }
              case "show_index" =>
                indexShowCount += 1
                indexTravelShowCount += 1
                if ("55".equals(channelId)) {
                  recShowCount += 1
                  recTravelShowCount += 1
                }
                if ("61".equals(channelId)) {
                  nearShowCount += 1
                  nearTravelShowCount += 1
                }
              case "click_weng_detail" =>
                wengDetailClickCount += 1
                wengDetailTravelClickCount += 1
              case "show_weng_detail" =>
                wengDetailShowCount += 1
                wengDetailTravelShowCount += 1
              case "click_video_detail" =>
                videoDetailClickCount += 1
                videoDetailTravelClickCount += 1
              case "show_video_detail" =>
                videoDetailShowCount += 1
                videoDetailTravelShowCount += 1
              case "click_mdd_index" =>
                mddClickCount += 1
                mddTravelClickCount += 1
              case "show_mdd_index" =>
                mddShowCount += 1
                mddTravelShowCount += 1
              case "page" =>
              case _ =>
            }

          case "6" =>
            eventType match {
              case "1" =>
                totalClickCount += 1
                totalVideoClickCount += 1
              case "0" =>
                totalShowCount += 1
                totalVideoShowCount += 1
              case _ =>
            }
            eventCode match {
              case "click_index" =>
                indexClickCount += 1
                indexVideoClickCount += 1
                if ("55".equals(channelId)) {
                  recClickCount += 1
                  recVideoClickCount += 1
                }
                if ("61".equals(channelId)) {
                  nearClickCount += 1
                  nearVideoClickCount += 1
                }
              case "show_index" =>
                indexShowCount += 1
                indexVideoShowCount += 1
                if ("55".equals(channelId)) {
                  recShowCount += 1
                  recVideoShowCount += 1
                }
                if ("61".equals(channelId)) {
                  nearShowCount += 1
                  nearVideoClickCount += 1
                }
              case "click_weng_detail" =>
                wengDetailClickCount += 1
                wengDetailVideoClickCount += 1
              case "show_weng_detail" =>
                wengDetailClickCount += 1
                wengDetailVideoShowCount += 1
              case "click_video_detail" =>
                videoDetailClickCount += 1
                videoDetailVideoClickCount += 1
              case "show_video_detail" =>
                videoDetailShowCount += 1
                videoDetailVideoShowCount += 1
              case "click_mdd_index" =>
                mddClickCount += 1
                mddVideoClickCount += 1
              case "show_mdd_index" =>
                mddShowCount += 1
                mddVideoShowCount += 1
              case "page" =>
              case _ =>
            }

          case _ =>
        }
      }
      if (eventStartTime > eventTime) eventStartTime = eventTime
      if (eventEndTime < eventTime) eventEndTime = eventTime
    }

    windowStartTime = ctx.window.getStart
    windowEndTime = ctx.window.getEnd

    totalCtr = totalClickCount.toDouble / (totalShowCount + 1)
    indexCtr = indexClickCount.toDouble / (indexShowCount + 1)
    recCtr = recClickCount.toDouble / (recShowCount + 1)
    nearCtr = nearClickCount.toDouble / (nearShowCount + 1)
    wengDetailCtr = wengDetailClickCount.toDouble / (wengDetailShowCount + 1)
    videoDetailCtr = videoDetailClickCount.toDouble / (videoDetailShowCount + 1)
    mddCtr = mddClickCount.toDouble / (mddShowCount + 1)
    totalWengCtr = totalWengClickCount.toDouble / (totalWengShowCount + 1)
    totalTravelCtr = totalTravelClickCount.toDouble / (totalTravelShowCount + 1)
    totalVideoCtr = totalVideoClickCount.toDouble / (totalVideoShowCount + 1)
    indexWengCtr = indexWengClickCount.toDouble / (indexWengShowCount + 1)
    indexTravelCtr = indexTravelClickCount.toDouble / (indexTravelShowCount + 1)
    indexVideoCtr = indexVideoClickCount.toDouble / (indexVideoShowCount + 1)
    recWengCtr = recWengClickCount.toDouble / (recWengShowCount + 1)
    recTravelCtr = recTravelClickCount.toDouble / (recTravelShowCount + 1)
    recVideoCtr = recVideoClickCount.toDouble / (recVideoShowCount + 1)
    nearWengCtr = nearWengClickCount.toDouble / (nearWengShowCount + 1)
    nearTravelCtr = nearTravelClickCount.toDouble / (nearTravelShowCount + 1)
    nearVideoCtr = nearVideoClickCount.toDouble / (nearVideoShowCount + 1)
    wengDetailWengCtr = wengDetailWengClickCount.toDouble / (wengDetailWengShowCount + 1)
    wengDetailTravelCtr = wengDetailTravelClickCount.toDouble / (wengDetailTravelShowCount + 1)
    wengDetailVideoCtr = wengDetailVideoClickCount.toDouble / (wengDetailVideoShowCount + 1)
    videoDetailWengCtr = videoDetailWengClickCount.toDouble / (videoDetailWengShowCount + 1)
    videoDetailTravelCtr = videoDetailTravelClickCount.toDouble / (videoDetailTravelShowCount + 1)
    videoDetailVideoCtr = videoDetailVideoClickCount.toDouble / (videoDetailVideoShowCount + 1)
    mddWengCtr = mddWengClickCount.toDouble / (mddWengShowCount + 1)
    mddTravelCtr = mddTravelClickCount.toDouble / (mddTravelShowCount + 1)
    mddVideoCtr = mddVideoClickCount.toDouble / (mddVideoShowCount + 1)

    out.collect(
      SURF(openUdid,
        totalClickCount,
        totalShowCount,
        totalCtr,
        indexClickCount,
        indexShowCount,
        indexCtr,
        recClickCount,
        recShowCount,
        recCtr,
        nearClickCount,
        nearShowCount,
        nearCtr,
        wengDetailClickCount,
        wengDetailShowCount,
        wengDetailCtr,
        videoDetailClickCount,
        videoDetailShowCount,
        videoDetailCtr,
        mddClickCount,
        mddShowCount,
        mddCtr,
        totalWengClickCount,
        totalWengShowCount,
        totalWengCtr,
        totalTravelClickCount,
        totalTravelShowCount,
        totalTravelCtr,
        totalVideoClickCount,
        totalVideoShowCount,
        totalVideoCtr,
        indexWengClickCount,
        indexWengShowCount,
        indexWengCtr,
        indexTravelClickCount,
        indexTravelShowCount,
        indexTravelCtr,
        indexVideoClickCount,
        indexVideoShowCount,
        indexVideoCtr,
        recWengClickCount,
        recWengShowCount,
        recWengCtr,
        recTravelClickCount,
        recTravelShowCount,
        recTravelCtr,
        recVideoClickCount,
        recVideoShowCount,
        recVideoCtr,
        nearWengClickCount,
        nearWengShowCount,
        nearWengCtr,
        nearTravelClickCount,
        nearTravelShowCount,
        nearTravelCtr,
        nearVideoClickCount,
        nearVideoShowCount,
        nearVideoCtr,
        wengDetailWengClickCount,
        wengDetailWengShowCount,
        wengDetailWengCtr,
        wengDetailTravelClickCount,
        wengDetailTravelShowCount,
        wengDetailTravelCtr,
        wengDetailVideoClickCount,
        wengDetailVideoShowCount,
        wengDetailVideoCtr,
        videoDetailWengClickCount,
        videoDetailWengShowCount,
        videoDetailWengCtr,
        videoDetailTravelClickCount,
        videoDetailTravelShowCount,
        videoDetailTravelCtr,
        videoDetailVideoClickCount,
        videoDetailVideoShowCount,
        videoDetailVideoCtr,
        mddWengClickCount,
        mddWengShowCount,
        mddWengCtr,
        mddTravelClickCount,
        mddTravelShowCount,
        mddTravelCtr,
        mddVideoClickCount,
        mddVideoShowCount,
        mddVideoCtr,
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
