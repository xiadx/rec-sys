package feature.scala.entity

class RFConfig {
  var user_realtime_prefix = ""
  var item_realtime_prefix = ""
  var featureVersion = ""
  var featureStructure = ""
  var template_prefix = ""
  var itemprofile_prefix = ""
  var needCache = "true"
  var threshold = ""
  var rankValue = ""
  var item_type_ctr = ""
  var needMddVec = ""
  var modelUrl = ""
  var needFeatureLog = ""
  var missingValue = ""
  var needLogFeatureNum = ""
  var featureSampleNum = ""
  var needSampleUser = ""
  var features: List[OnlineFeature] = List.empty[OnlineFeature]
}
