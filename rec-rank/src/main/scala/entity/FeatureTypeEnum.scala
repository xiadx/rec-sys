package entity

object FeatureTypeEnum extends Enumeration {

  type FeatureTypeEnum = Value

  val RAW: FeatureTypeEnum.Value = Value("raw")
  val COS: FeatureTypeEnum.Value = Value("cos")
  val EDU: FeatureTypeEnum.Value = Value("edu")
  val MAT: FeatureTypeEnum.Value = Value("mat")

}
