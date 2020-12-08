package utils.entity

@SerialVersionUID(1L)
object ScalerTypeEnum extends Enumeration with Serializable {

  type ScalerTypeEnum = Value

  val MINMAX: ScalerTypeEnum.Value = Value("minmax")
  val STANDARD: ScalerTypeEnum.Value = Value("standard")
  val MAXABS: ScalerTypeEnum.Value = Value("maxabs")

}
