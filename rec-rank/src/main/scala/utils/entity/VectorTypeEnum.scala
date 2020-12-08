package utils.entity

@SerialVersionUID(1L)
object VectorTypeEnum extends Enumeration with Serializable {

  type VectorTypeEnum = Value

  val VECTOR: VectorTypeEnum.Value = Value("vector")
  val ARRAY: VectorTypeEnum.Value = Value("array")
  val SPARSE: VectorTypeEnum.Value = Value("sparse")
  val JSON: VectorTypeEnum.Value = Value("json")

}
