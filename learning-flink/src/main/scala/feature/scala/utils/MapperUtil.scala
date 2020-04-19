package feature.scala.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.collection.mutable
import feature.scala.entity._

object MapperUtil {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def getCCParams(cc: Product) = cc.getClass.getDeclaredFields.map(_.getName) // all field names
    .zip(cc.productIterator.to).toMap // zipped with all values

  def writeSURFToString(window: String, surf: SURF): String = {
    val fm = ConfigUtil.getSURFFeatureMap(window)
    val rm = mutable.HashMap[String, Any]()
    val fixedFields = ConfigUtil.surfConf.getStringList("feature.fixed")
    getCCParams(surf).foreach { a =>
      val f: Feature = if (fixedFields.contains(a._1)) {
        fm.getOrElse(a._1, null)
      } else {
        fm.getOrElse(a._1 + window.toUpperCase(), null)
      }
      if (f == null) {
        println(a._1 + window.toUpperCase() + " feature missing config")
        System.exit(1)
      } else {
        val id = f.id
        val mark = f.mark
        val name = f.name
        val status = f.status
        val defaultValue = f.defaultValue
        if (FeatureStatus.YES.toString.equals(status) && a._2 != null && !a._2.toString.equals(defaultValue)) {
          if (fixedFields.contains(name)) {
            rm.put(name, a._2)
          } else {
            rm.put(mark + id.toString, a._2)
          }
        }
      }
    }
    mapper.writeValueAsString(rm)
  }

  def writeSIRFToString(window: String, sirf: SIRF): String = {
    val fm = ConfigUtil.getSIRFFeatureMap(window)
    val rm = mutable.HashMap[String, Any]()
    val fixedFields = ConfigUtil.sirfConf.getStringList("feature.fixed")
    getCCParams(sirf).foreach { a =>
      val f: Feature = if (fixedFields.contains(a._1)) {
        fm.getOrElse(a._1, null)
      } else {
        fm.getOrElse(a._1 + window.toUpperCase(), null)
      }
      if (f == null) {
        println(a._1 + window.toUpperCase() + " feature missing config")
        System.exit(1)
      } else {
        val id = f.id
        val mark = f.mark
        val name = f.name
        val status = f.status
        val defaultValue = f.defaultValue
        if (FeatureStatus.YES.toString.equals(status) && a._2 != null && !a._2.toString.equals(defaultValue)) {
          if (fixedFields.contains(name)) {
            rm.put(name, a._2)
          } else {
            rm.put(mark + id.toString, a._2)
          }
        }
      }
    }
    mapper.writeValueAsString(rm)
  }

  def writeRFConfigToString(rfconf: RFConfig): String = {
    mapper.writeValueAsString(rfconf)
  }

}
