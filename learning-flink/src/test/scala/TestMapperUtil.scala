import org.junit.Test

import feature.scala.entity.SURF
import feature.scala.utils.MapperUtil

class TestMapperUtil {

  @Test
  def testWriteSURFToString(): Unit = {
    val openUdid: String = "abc"
    var totalClickCount: Long = 1L
    var totalShowCount: Long = 0L
    var totalCtr: Double = 0.0d
    var indexClickCount: Long = 0L
    var indexShowCount: Long = 2L
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

    val surf = SURF(openUdid,
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

    println(MapperUtil.writeSURFToString( "w1", surf))
  }

}
