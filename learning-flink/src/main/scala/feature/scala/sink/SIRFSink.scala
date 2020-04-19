package feature.scala.sink

import java.util.Calendar

import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import feature.scala.entity.SIRF
import feature.scala.utils.{ConfigUtil, JedisUtil, MapperUtil, RedisClusterEnum}
import org.apache.flink.configuration.Configuration
import redis.clients.jedis.JedisCluster

class SIRFSink(val window: String) extends RichSinkFunction[SIRF] {

  var jedis_cluster: JedisCluster = _
  val redisClusterText: String = ConfigUtil.sirfConf.getString("redis.cluster")
  val slide: Long = ConfigUtil.sirfConf.getLong("window." + window + ".slide")
  val extraDeadline: Long = ConfigUtil.sirfConf.getLong("window." + window + ".extra-deadline")
  val keyPrefix: String = ConfigUtil.sirfConf.getString("redis.key-prefix")
  val expireTime: Int = ConfigUtil.sirfConf.getInt("redis.expire-time")

  override def open(parameters: Configuration): Unit = {
    super.open(parameters)
    jedis_cluster = JedisUtil.getJedisCluster(redisClusterText)
  }

  override def close(): Unit = {
    super.close()
    if (jedis_cluster != null) {
      jedis_cluster.close()
    }
  }

  override def invoke(in: SIRF): Unit = {
    val cal = Calendar.getInstance()
    val currentTime = System.currentTimeMillis()
    cal.setTimeInMillis(currentTime)

    in.toRedisTime = currentTime
    in.toRedisYear = cal.get(Calendar.YEAR)
    in.toRedisMonth = cal.get(Calendar.MONTH) + 1
    in.toRedisDay = cal.get(Calendar.DATE)
    in.toRedisWeek = cal.get(Calendar.DAY_OF_WEEK)
    in.toRedisHour = cal.get(Calendar.HOUR_OF_DAY)
    in.toRedisMinute = cal.get(Calendar.MINUTE) + 1
    in.toRedisSecond = cal.get(Calendar.SECOND)
    in.toRedisDelay = in.toRedisTime - in.windowEndTime
    in.deadlineTime = in.toRedisTime + (in.eventEndTime - in.windowStartTime - slide) + extraDeadline

    var action = ""
    if (in.eventEndTime > in.windowEndTime - slide) {
      action = "update"
    } else {
      if (in.eventStartTime > in.windowStartTime + slide) {
        action = "fix"
      } else {
        if (in.eventEndTime > in.windowStartTime + slide) {
          action = "update"
        } else {
          action = "fix"
        }
      }
    }

    if ("update".equals(action)) {
      val key = keyPrefix + in.itemUniqueId
      val value = MapperUtil.writeSIRFToString(window, in)
      jedis_cluster.hset(key, window, value)
      jedis_cluster.expire(key, expireTime)
    }
  }

}
