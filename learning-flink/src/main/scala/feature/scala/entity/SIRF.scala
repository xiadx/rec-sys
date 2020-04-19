package feature.scala.entity

/**
 * statistics item realtime feature
 *
 * @param itemId
 * @param itemType
 * @param itemUniqueId
 * @param totalClickCount
 * @param totalShowCount
 * @param totalCtr1
 * @param totalCtr2
 * @param indexClickCount
 * @param indexShowCount
 * @param indexCtrV1
 * @param indexCtrV2
 * @param recClickCount
 * @param recShowCount
 * @param recCtrV1
 * @param recCtrV2
 * @param nearClickCount
 * @param nearShowCount
 * @param nearCtrV1
 * @param wengDetailClickCount
 * @param wengDetailShowCount
 * @param wengDetailCtrV1
 * @param videoDetailClickCount
 * @param videoDetailShowCount
 * @param videoDetailCtrV1
 * @param mddClickCount
 * @param mddShowCount
 * @param mddCtrV1
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
 */
case class SIRF(itemId: String,
                itemType: String,
                itemUniqueId: String,
                totalClickCount: Long,
                totalShowCount: Long,
                totalCtr1: Double,
                totalCtr2: Double,
                indexClickCount: Long,
                indexShowCount: Long,
                indexCtrV1: Double,
                indexCtrV2: Double,
                recClickCount: Long,
                recShowCount: Long,
                recCtrV1: Double,
                recCtrV2: Double,
                nearClickCount: Long,
                nearShowCount: Long,
                nearCtrV1: Double,
                wengDetailClickCount: Long,
                wengDetailShowCount: Long,
                wengDetailCtrV1: Double,
                videoDetailClickCount: Long,
                videoDetailShowCount: Long,
                videoDetailCtrV1: Double,
                mddClickCount: Long,
                mddShowCount: Long,
                mddCtrV1: Double,
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
