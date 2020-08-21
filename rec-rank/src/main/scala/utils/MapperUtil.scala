package utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object MapperUtil {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def getCCParams(cc: Product) = cc.getClass.getDeclaredFields.map(_.getName) // all field names
    .zip(cc.productIterator.to).toMap // zipped with all values

}
