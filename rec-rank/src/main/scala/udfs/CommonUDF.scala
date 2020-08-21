package udfs

import entity.ItemTypeEnum
import org.apache.spark.sql.functions.udf
import org.apache.commons.lang3.StringUtils
import operator.CommonOperator

object CommonUDF {

  val calcCtr = udf(CommonOperator.calculateCtr _)

  val itemTypeTrans = udf(itemTypeTransform _)

  def itemTypeTransform(str: String): String = {
    if (StringUtils.isEmpty(str)) {
      ItemTypeEnum.OTHER.toString
    } else if (str.contains("weng")) {
      ItemTypeEnum.WENG.toString
    } else if (str.contains("note")) {
      ItemTypeEnum.NOTE.toString
    } else if (str.contains("travel")) {
      ItemTypeEnum.TRAVEL.toString
    } else if (str.contains("qa")) {
      ItemTypeEnum.QA.toString
    } else if (str.contains("wenda")) {
      ItemTypeEnum.WENDA.toString
    } else if (str.contains("guide")) {
      ItemTypeEnum.GUIDE.toString
    } else if (str.contains("gonglve")) {
      ItemTypeEnum.GONGLVE.toString
    } else if (str.contains("video")) {
      ItemTypeEnum.VIDEO.toString
    } else if (str.contains("show")) {
      ItemTypeEnum.SHOW.toString
    } else if (str.contains("sales")) {
      ItemTypeEnum.SALES.toString
    } else if (str.contains("poi")) {
      ItemTypeEnum.POI.toString
    } else if (str.contains("user")) {
      ItemTypeEnum.USER.toString
    } else if (str.contains("query")) {
      ItemTypeEnum.QUERY.toString
    } else {
      ItemTypeEnum.OTHER.toString
    }
  }

}
