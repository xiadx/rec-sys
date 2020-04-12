import org.junit.Test
import feature.scala.utils.{ConfigUtil, JedisUtil}
import redis.clients.jedis.JedisCluster

class TestConfigUtil {

  @Test
  def testGetFeatureMap(): Unit = {
    println(ConfigUtil.getFeatureMap("w1"))
  }

  @Test
  def testJedisCluster(): Unit = {
    val redisClusterText: String = ConfigUtil.surfConf.getString("redis.cluster")
    val jedis_cluster: JedisCluster = JedisUtil.getJedisCluster(redisClusterText)
    println(jedis_cluster)
  }

}
