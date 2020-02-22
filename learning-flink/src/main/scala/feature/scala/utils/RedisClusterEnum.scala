package feature.scala.utils

object RedisClusterEnum extends Enumeration {

  type RedisClusterEnum = Value

  val RECOMMEND_MASTER: RedisClusterEnum.Value = Value("192.168.7.163:7001")
  val RECOMMEND_BACKUP: RedisClusterEnum.Value = Value("192.168.2.233:7011")

}
