package frame

import entity.Feature
import entity.DataTypeEnum
import scala.collection.mutable

class BasicFeatureConf(var fconf: List[Feature]) {

  val fmap = getFeatureMap()
  val dmap = getDefaultValueMap()
  val tmap = getTypeMap()

  def getFeatureMap(): mutable.HashMap[String, Feature] = {
    val fmap = new mutable.HashMap[String, Feature]()
    for (f <- fconf) {
      val name = f.name
      fmap.put(name, f)
    }
    fmap
  }

  def getDefaultValueMap(): mutable.HashMap[String, Any] = {
    val dmap = new mutable.HashMap[String, Any]()
    for (f <- fconf) {
      val name = f.name
      val typ = f.typ
      val value = f.value
      if (DataTypeEnum.LONG.toString.equals(typ)) {
        dmap.put(name, value.toLong)
      }
      if (DataTypeEnum.INTEGER.toString.equals(typ)) {
        dmap.put(name, value.toInt)
      }
      if (DataTypeEnum.DOUBLE.toString.equals(typ)) {
        dmap.put(name, value.toDouble)
      }
      if (DataTypeEnum.FLOAT.toString.equals(typ)) {
        dmap.put(name, value.toFloat)
      }
      if (DataTypeEnum.STRING.toString.equals(typ)) {
        dmap.put(name, value.toString)
      }
    }
    dmap
  }

  def getTypeMap(): mutable.HashMap[String, String] = {
    val tmap = new mutable.HashMap[String, String]()
    for (f <- fconf) {
      val name = f.name
      val typ = f.typ
      tmap.put(name, typ)
    }
    tmap
  }

}
