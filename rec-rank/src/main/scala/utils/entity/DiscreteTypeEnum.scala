package utils.entity

@SerialVersionUID(1L)
object DiscreteTypeEnum extends Enumeration with Serializable {

  type DiscreteTypeEnum = Value

  val BUCKET: DiscreteTypeEnum.Value = Value("bucket")
  val NORMALIZE: DiscreteTypeEnum.Value = Value("normalize")
  val INTEGRAL: DiscreteTypeEnum.Value = Value("integral")

}
