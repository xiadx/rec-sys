package feature.scala.utils

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{HostAndPort, JedisCluster}
import RedisClusterEnum.RedisClusterEnum

object JedisUtil {

  val MAX_TOTAL = 25
  val MAX_IDLE = 10
  val MAX_WAIT_MILLIS: Long = 1000 * 100
  val TEST_ON_BORROW = true

  def getJedisCluser(redisClusterEnum: RedisClusterEnum): JedisCluster = {
    val poolConfig = new GenericObjectPoolConfig
    poolConfig.setMaxTotal(MAX_TOTAL)
    poolConfig.setMaxIdle(MAX_IDLE)
    poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS)
    poolConfig.setTestOnBorrow(TEST_ON_BORROW)

    val redisClusterText = redisClusterEnum.toString
    val hostAndPort = redisClusterText.split(":")
    val node = new HostAndPort(hostAndPort(0), hostAndPort(1).toInt)

    new JedisCluster(node, poolConfig)
  }

  def getJedisCluster(redisClusterText: String): JedisCluster = {
    val poolConfig = new GenericObjectPoolConfig
    poolConfig.setMaxTotal(MAX_TOTAL)
    poolConfig.setMaxIdle(MAX_IDLE)
    poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS)
    poolConfig.setTestOnBorrow(TEST_ON_BORROW)

    val hostAndPort = redisClusterText.split(":")
    val node = new HostAndPort(hostAndPort(0), hostAndPort(1).toInt)

    new JedisCluster(node, poolConfig)
  }

}
