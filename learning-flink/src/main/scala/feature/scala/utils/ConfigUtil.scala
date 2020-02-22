package feature.scala.utils

import com.typesafe.config._
import scala.collection.mutable
import feature.scala.entity.Feature

object ConfigUtil {

  val surfConf = ConfigFactory.parseResources("surf.conf")

  def getFeatureMap(window: String): mutable.HashMap[String, Feature] = {
    val cl: java.util.List[_ <: Config] = surfConf.getConfig("feature").getConfigList(window)
    val m = new mutable.HashMap[String, Feature]()
    for (i <- 0 until cl.size()) {
      val id = cl.get(i).getInt("id")
      val mark = cl.get(i).getString("mark")
      val name = cl.get(i).getString("name")
      val detail = cl.get(i).getString("detail")
      val category = cl.get(i).getString("category")
      val expression = cl.get(i).getString("expression")
      val defaultValue = cl.get(i).getString("default")
      val defaultType = cl.get(i).getString("type")
      val status = cl.get(i).getString("status")
      val feature = Feature(
        id,
        mark,
        name,
        category,
        detail,
        expression,
        defaultValue,
        defaultType,
        status
      )
      m.put(name, feature)
    }
    m
  }

}
