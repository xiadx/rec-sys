package entity

object ItemTypeEnum extends Enumeration {

  type ItemTypeEnum = Value

  val WENG: ItemTypeEnum.Value = Value("0")
  val NOTE: ItemTypeEnum.Value = Value("1")
  val TRAVEL: ItemTypeEnum.Value = Value("1")
  val QA: ItemTypeEnum.Value = Value("2")
  val WENDA: ItemTypeEnum.Value = Value("2")
  val GUIDE: ItemTypeEnum.Value = Value("3")
  val GONGLVE: ItemTypeEnum.Value = Value("3")
  val VIDEO: ItemTypeEnum.Value = Value("6")
  val SHOW: ItemTypeEnum.Value = Value("8")
  val SALES: ItemTypeEnum.Value = Value("5")
  val POI: ItemTypeEnum.Value = Value("102")
  val USER: ItemTypeEnum.Value = Value("103")
  val QUERY: ItemTypeEnum.Value = Value("104")
  val OTHER: ItemTypeEnum.Value = Value("-1")

}
