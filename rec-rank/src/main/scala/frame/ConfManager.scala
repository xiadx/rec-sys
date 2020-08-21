package frame

import com.typesafe.config.Config
import utils.ConfigUtil
import entity.Feature
import entity.AdvanceFeature
import scala.collection.mutable

object ConfManager {

  val appConf = ConfigUtil.appConf
  val basicFeatureConf = loadBasicFeatureConf()
  val advanceFeatureConf = loadAdvanceFeatureConf()

  def loadBasicFeatureConf(): BasicFeatureConf = {
    val bfConf = appConf.getConfig("bf")
    val cl: java.util.List[_ <: Config] = bfConf.getConfigList("fconf")
    val fconf = mutable.ArrayBuffer.empty[Feature]
    for (i <- 0 until cl.size()) {
      val id = cl.get(i).getInt("id")
      val mark = cl.get(i).getString("mark")
      val name = cl.get(i).getString("name")
      val alias = cl.get(i).getString("alias")
      val exp = cl.get(i).getString("exp")
      val dtl = cl.get(i).getString("dtl")
      val typ = cl.get(i).getString("typ")
      val value = cl.get(i).getString("value")
      val status = cl.get(i).getString("status")
      val f = Feature(id, mark, name, alias, exp, dtl, typ, value, status)
      fconf.append(f)
    }
    val basicFeatureConf = new BasicFeatureConf(fconf.toList)
    basicFeatureConf
  }

  def loadAdvanceFeatureConf(): AdvanceFeatureConf = {
    val afConf = appConf.getConfig("af")
    val cl: java.util.List[_ <: Config] = afConf.getConfigList("fconf")
    val fconf = mutable.ArrayBuffer.empty[AdvanceFeature]
    for (i <- 0 until cl.size()) {
      val id = cl.get(i).getInt("id")
      val mark = cl.get(i).getString("mark")
      val name = cl.get(i).getString("name")
      val alias = cl.get(i).getString("alias")
      val exp = cl.get(i).getString("exp")
      val dtl = cl.get(i).getString("dtl")
      val vtyp = cl.get(i).getString("vtyp")
      val ftyp = cl.get(i).getString("ftyp")
      val value = cl.get(i).getString("value")
      val status = cl.get(i).getString("status")
      val source = cl.get(i).getString("source")
      val f = AdvanceFeature(id, mark, name, alias, exp, dtl, vtyp, ftyp, value, status, source)
      fconf.append(f)
    }
    val advanceFeatureConf = new AdvanceFeatureConf(fconf.toList)
    advanceFeatureConf
  }

  def main(args: Array[String]): Unit = {

//    println(basicFeatureConf.dmap)
    println(5.023 != 5.023)
  }

}
