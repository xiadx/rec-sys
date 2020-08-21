package sample

import entity.{OnlineFeature, RawFeature, FeatureTypeEnum}
import frame.ConfManager

object OnlineFeatureFactory {

  def getOnlineFeature(c: String): OnlineFeature = {
    val af = ConfManager.advanceFeatureConf.fmap(c)
    if (af.ftyp.equals(FeatureTypeEnum.RAW.toString)) {
      val rf = new RawFeature
      rf.ftyp = af.ftyp
      rf.name = af.name
      rf.alias = af.alias
      rf.value = af.value
//      rf.exp = af.source + ":" + af.alias
      rf.exp = s"${af.source}:${af.alias}"
      rf
    } else {
      new RawFeature
    }
  }

}
