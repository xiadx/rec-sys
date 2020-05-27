package utils

import com.typesafe.config._

object ConfigUtil {

  val basicitemcfConf = ConfigFactory.parseResources("basic-itemcf-job.conf")
  val dynamicwalkConf = ConfigFactory.parseResources("dynamic-walk.conf")
  val word2vecConf = ConfigFactory.parseResources("word2vec.conf")

}
