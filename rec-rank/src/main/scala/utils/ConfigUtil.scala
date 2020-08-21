package utils

import com.typesafe.config._

object ConfigUtil {

  val appConf = ConfigFactory.parseResources("app.conf")

}
