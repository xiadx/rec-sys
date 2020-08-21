package entity

object FeatureMark extends Enumeration {

  type FeatureMark = Value

  val USER: FeatureMark.Value = Value("u")
  val ITEM: FeatureMark.Value = Value("i")
  val CONTEXT: FeatureMark.Value = Value("c")

}
