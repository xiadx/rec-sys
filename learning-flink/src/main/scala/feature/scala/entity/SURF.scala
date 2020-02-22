package feature.scala.entity

/**
 * statistics user realtime feature
 *
 * @param openUdid
 * @param totalClickCount
 * @param totalShowCount
 * @param totalCtr
 * @param indexClickCount
 * @param indexShowCount
 * @param indexCtr
 * @param recClickCount
 * @param recShowCount
 * @param recCtr
 * @param nearClickCount
 * @param nearShowCount
 * @param nearCtr
 * @param wengDetailClickCount
 * @param wengDetailShowCount
 * @param wengDetailCtr
 * @param videoDetailClickCount
 * @param videoDetailShowCount
 * @param videoDetailCtr
 * @param mddClickCount
 * @param mddShowCount
 * @param mddCtr
 * @param totalWengClickCount
 * @param totalWengShowCount
 * @param totalWengCtr
 * @param totalTravelClickCount
 * @param totalTravelShowCount
 * @param totalTravelCtr
 * @param totalVideoClickCount
 * @param totalVideoShowCount
 * @param totalVideoCtr
 * @param indexWengClickCount
 * @param indexWengShowCount
 * @param indexWengCtr
 * @param indexTravelClickCount
 * @param indexTravelShowCount
 * @param indexTravelCtr
 * @param indexVideoClickCount
 * @param indexVideoShowCount
 * @param indexVideoCtr
 * @param recWengClickCount
 * @param recWengShowCount
 * @param recWengCtr
 * @param recTravelClickCount
 * @param recTravelShowCount
 * @param recTravelCtr
 * @param recVideoClickCount
 * @param recVideoShowCount
 * @param recVideoCtr
 * @param nearWengClickCount
 * @param nearWengShowCount
 * @param nearWengCtr
 * @param nearTravelClickCount
 * @param nearTravelShowCount
 * @param nearTravelCtr
 * @param nearVideoClickCount
 * @param nearVideoShowCount
 * @param nearVideoCtr
 * @param wengDetailWengClickCount
 * @param wengDetailWengShowCount
 * @param wengDetailWengCtr
 * @param wengDetailTravelClickCount
 * @param wengDetailTravelShowCount
 * @param wengDetailTravelCtr
 * @param wengDetailVideoClickCount
 * @param wengDetailVideoShowCount
 * @param wengDetailVideoCtr
 * @param videoDetailWengClickCount
 * @param videoDetailWengShowCount
 * @param videoDetailWengCtr
 * @param videoDetailTravelClickCount
 * @param videoDetailTravelShowCount
 * @param videoDetailTravelCtr
 * @param videoDetailVideoClickCount
 * @param videoDetailVideoShowCount
 * @param videoDetailVideoCtr
 * @param mddWengClickCount
 * @param mddWengShowCount
 * @param mddWengCtr
 * @param mddTravelClickCount
 * @param mddTravelShowCount
 * @param mddTravelCtr
 * @param mddVideoClickCount
 * @param mddVideoShowCount
 * @param mddVideoCtr
 * @param windowStartTime
 * @param windowEndTime
 * @param eventStartTime
 * @param eventEndTime
 * @param toRedisTime
 * @param toRedisYear
 * @param toRedisMonth
 * @param toRedisDay
 * @param toRedisWeek
 * @param toRedisHour
 * @param toRedisMinute
 * @param toRedisSecond
 * @param toRedisDelay
 * @param deadlineTime
 */
case class SURF(openUdid: String,
                totalClickCount: Long,
                totalShowCount: Long,
                totalCtr: Double,
                indexClickCount: Long,
                indexShowCount: Long,
                indexCtr: Double,
                recClickCount: Long,
                recShowCount: Long,
                recCtr: Double,
                nearClickCount: Long,
                nearShowCount: Long,
                nearCtr: Double,
                wengDetailClickCount: Long,
                wengDetailShowCount: Long,
                wengDetailCtr: Double,
                videoDetailClickCount: Long,
                videoDetailShowCount: Long,
                videoDetailCtr: Double,
                mddClickCount: Long,
                mddShowCount: Long,
                mddCtr: Double,
                totalWengClickCount: Long,
                totalWengShowCount: Long,
                totalWengCtr: Double,
                totalTravelClickCount: Long,
                totalTravelShowCount: Long,
                totalTravelCtr: Double,
                totalVideoClickCount: Long,
                totalVideoShowCount: Long,
                totalVideoCtr: Double,
                indexWengClickCount: Long,
                indexWengShowCount: Long,
                indexWengCtr: Double,
                indexTravelClickCount: Long,
                indexTravelShowCount: Long,
                indexTravelCtr: Double,
                indexVideoClickCount: Long,
                indexVideoShowCount: Long,
                indexVideoCtr: Double,
                recWengClickCount: Long,
                recWengShowCount: Long,
                recWengCtr: Double,
                recTravelClickCount: Long,
                recTravelShowCount: Long,
                recTravelCtr: Double,
                recVideoClickCount: Long,
                recVideoShowCount: Long,
                recVideoCtr: Double,
                nearWengClickCount: Long,
                nearWengShowCount: Long,
                nearWengCtr: Double,
                nearTravelClickCount: Long,
                nearTravelShowCount: Long,
                nearTravelCtr: Double,
                nearVideoClickCount: Long,
                nearVideoShowCount: Long,
                nearVideoCtr: Double,
                wengDetailWengClickCount: Long,
                wengDetailWengShowCount: Long,
                wengDetailWengCtr: Double,
                wengDetailTravelClickCount: Long,
                wengDetailTravelShowCount: Long,
                wengDetailTravelCtr: Double,
                wengDetailVideoClickCount: Long,
                wengDetailVideoShowCount: Long,
                wengDetailVideoCtr: Double,
                videoDetailWengClickCount: Long,
                videoDetailWengShowCount: Long,
                videoDetailWengCtr: Double,
                videoDetailTravelClickCount: Long,
                videoDetailTravelShowCount: Long,
                videoDetailTravelCtr: Double,
                videoDetailVideoClickCount: Long,
                videoDetailVideoShowCount: Long,
                videoDetailVideoCtr: Double,
                mddWengClickCount: Long,
                mddWengShowCount: Long,
                mddWengCtr: Double,
                mddTravelClickCount: Long,
                mddTravelShowCount: Long,
                mddTravelCtr: Double,
                mddVideoClickCount: Long,
                mddVideoShowCount: Long,
                mddVideoCtr: Double,
                windowStartTime: Long,
                windowEndTime: Long,
                eventStartTime: Long,
                eventEndTime: Long,
                var toRedisTime: Long,
                var toRedisYear: Int,
                var toRedisMonth: Int,
                var toRedisDay: Int,
                var toRedisWeek: Int,
                var toRedisHour: Int,
                var toRedisMinute: Int,
                var toRedisSecond: Int,
                var toRedisDelay: Long,
                var deadlineTime: Long)
