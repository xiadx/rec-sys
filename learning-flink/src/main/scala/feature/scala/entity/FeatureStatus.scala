package feature.scala.entity

object FeatureStatus extends Enumeration {

  type FeatureStatus = Value

  val YES: FeatureStatus.Value = Value("1")
  val NO: FeatureStatus.Value = Value("0")

}
