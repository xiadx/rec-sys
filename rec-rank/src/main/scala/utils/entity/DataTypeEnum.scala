package utils.entity

object DataTypeEnum extends Enumeration {

  type DataTypeEnum = Value

  val LONG: DataTypeEnum.Value = Value("long")
  val INTEGER: DataTypeEnum.Value = Value("integer")
  val DOUBLE: DataTypeEnum.Value = Value("double")
  val FLOAT: DataTypeEnum.Value = Value("float")
  val STRING: DataTypeEnum.Value = Value("string")

}
