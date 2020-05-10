package feature.scala.utils

import java.util

import com.typesafe.config._
import feature.scala.entity.{BayesianSmoothing, Feature, OnlineFeature, RFConfig}

import scala.collection.mutable
import scala.collection.JavaConverters._

object ConfigUtil {

  val surfConf = ConfigFactory.parseResources("surf.conf")
  val sirfConf = ConfigFactory.parseResources("sirf.conf")
  val rfprintConf = ConfigFactory.parseResources("rfprint.conf")
  val sirfBayesianSmoothing = getSIRFBayesianSmoothing()

  def getFeatureMap(window: String, config: Config): mutable.HashMap[String, Feature] = {
    val cl: java.util.List[_ <: Config] = config.getConfig("feature").getConfigList(window)
    val m = new mutable.HashMap[String, Feature]()
    for (i <- 0 until cl.size()) {
      val id = cl.get(i).getInt("id")
      val mark = cl.get(i).getString("mark")
      val name = cl.get(i).getString("name")
      val detail = cl.get(i).getString("detail")
      val category = cl.get(i).getString("category")
      val expression = cl.get(i).getString("expression")
      val defaultValue = cl.get(i).getString("default")
      val defaultType = cl.get(i).getString("type")
      val status = cl.get(i).getString("status")
      val feature = Feature(
        id,
        mark,
        name,
        category,
        detail,
        expression,
        defaultValue,
        defaultType,
        status
      )
      m.put(name, feature)
    }
    m
  }

  def getSURFFeatureMap(window: String): mutable.HashMap[String, Feature] = {
    getFeatureMap(window, surfConf)
  }

  def getSIRFFeatureMap(window: String): mutable.HashMap[String, Feature] = {
    getFeatureMap(window, sirfConf)
  }

  def getSIRFBayesianSmoothing(): List[BayesianSmoothing] = {
    val l = new mutable.ArrayBuffer[BayesianSmoothing]()
    val cl: java.util.List[_ <: Config] = sirfConf.getConfigList("bayesian-smoothing")
    for (i <- 0 until cl.size()) {
      val version = cl.get(i).getString("version")
      val itemType = cl.get(i).getString("itemtype")
      val pageScene = cl.get(i).getString("pagescene")
      val alpha = cl.get(i).getDouble("alpha")
      val beta = cl.get(i).getDouble("beta")
      val bs = BayesianSmoothing(
        version,
        itemType,
        pageScene,
        alpha,
        beta
      )
      l.append(bs)
    }
    l.toList
  }

  def getBayesianSmoothAlphaBeta(version: String, itemType: String, pageScene: String): (Double, Double) = {
    var alpha = 0.0d
    var beta = 0.0d
    for (bs <- sirfBayesianSmoothing) {
      val v = bs.version
      val t = bs.itemType
      val s = bs.pageScene
      if (version.equals(v) && itemType.equals(t) && pageScene.equals(s)) {
        alpha = bs.alpha
        beta = bs.beta
      }
    }
    (alpha, beta)
  }

  def getRFConfig(): RFConfig = {
    val rfconf = new RFConfig
    rfconf.user_realtime_prefix = rfprintConf.getString("user-realtime-prefix")
    rfconf.item_realtime_prefix = rfprintConf.getString("item-realtime-prefix")
    rfconf.featureVersion = rfprintConf.getString("feature-version")
    rfconf.featureStructure = rfprintConf.getString("feature-structure")
    rfconf.template_prefix = rfprintConf.getString("template-prefix")
    rfconf.itemprofile_prefix = rfprintConf.getString("itemprofile-prefix")
    rfconf.needCache = rfprintConf.getString("need-cache")
    rfconf.threshold = rfprintConf.getString("threshold")
    rfconf.rankValue = rfprintConf.getString("rank-value")
    rfconf.item_type_ctr = rfprintConf.getString("item-type-ctr")
    rfconf.needMddVec = rfprintConf.getString("need-mdd-vec")
    rfconf.modelUrl = rfprintConf.getString("model-url")
    rfconf.needFeatureLog = rfprintConf.getString("need-feature-log")
    rfconf.missingValue = rfprintConf.getString("missing-value")
    rfconf.needLogFeatureNum = rfprintConf.getString("need-log-feature-num")
    rfconf.featureSampleNum = rfprintConf.getString("feature-sample-num")
    rfconf.needSampleUser = rfprintConf.getString("need-sample-user")
    val fs = new mutable.ListBuffer[OnlineFeature]
    val uws = rfprintConf.getConfig("user-realtime-feature").root().keySet().asScala
    for (w <- uws) {
      val surfFeatureMap = getFeatureMap(w, surfConf)
      val surfName = rfprintConf.getStringList("user-realtime-feature." + w).asScala
      for (name <- surfName) {
        val f = surfFeatureMap.getOrElse(name, null)
        if (f == null) {
          println(name + " feature missing config")
          System.exit(1)
        }
        val feaDefault = f.defaultValue
        val feaName = f.name
        val feaType = "raw_feature"
        val feaExpression = "user:" + f.mark + f.id.toString
        val of = OnlineFeature(
          feaDefault,
          feaType,
          feaName,
          feaExpression
        )
        fs.append(of)
      }
    }
    val iws = rfprintConf.getConfig("item-realtime-feature").root().keySet().asScala
    for (w <- iws) {
      val sirfFeatureMap = getFeatureMap(w, sirfConf)
      val sirfName = rfprintConf.getStringList("item-realtime-feature." + w).asScala
      for (name <- sirfName) {
        val f = sirfFeatureMap.getOrElse(name, null)
        if (f == null) {
          println(name + " feature missing config")
          System.exit(1)
        }
        val feaDefault = f.defaultValue
        val feaName = f.name
        val feaType = "raw_feature"
        val feaExpression = "item:" + f.mark + f.id.toString
        val of = OnlineFeature(
          feaDefault,
          feaType,
          feaName,
          feaExpression
        )
        fs.append(of)
      }
    }
    rfconf.features = fs.toList
    rfconf
  }

}
