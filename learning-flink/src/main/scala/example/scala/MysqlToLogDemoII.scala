package example.scala

import java.sql.ResultSet

import example.scala.utils.JdbcUtil
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

import scala.collection.mutable

object MysqlToLogDemoII {

  val BASE_FEATURE_MAP = getBaseFeatureAsMap()

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val fld = "MysqlToLogDemoII"

    val dataStream = env.fromElements(1, 2, 3)

    dataStream.addSink(new SinkFunction[Int] {
      override def invoke(in: Int): Unit = {
        println(fld + " --> " + in.toString)
        println(BASE_FEATURE_MAP.keys.mkString(","))
      }
    })

    println(fld)

    env.execute("MysqlToLogDemoII")
  }

  def getBaseFeatureAsMap(): mutable.Map[String, BaseFeature] = {
    val sql =
      """
        |select
        | id
        | ,feature_mark
        | ,feature_name
        | ,feature_source
        | ,feature_details
        | ,feature_scene
        | ,expression
        | ,feature_value_type
        | ,d_value_str
        | ,d_value_type
        | ,status
        | ,online_status
        |from
        | search_feature_config.t_rank_feature
      """.stripMargin
    val baseFeatureMap = new mutable.HashMap[String, BaseFeature]
    var rs: ResultSet = null
    try {
      rs = JdbcUtil.select(sql)
      while (rs.next()) {
        val featureId = rs.getLong("id")
        val featureMark = rs.getString("feature_mark")
        val featureName = rs.getString("feature_name")
        val featureSource = rs.getString("feature_source")
        val featureDetail = rs.getString("feature_details")
        val featureScene = rs.getString("feature_scene")
        val expression = rs.getString("expression")
        val featureType = rs.getString("feature_value_type")
        val defaultValue = rs.getString("d_value_str")
        val defaultType = rs.getString("d_value_type")
        val status = rs.getString("status")
        val onlineStatus = rs.getString("online_status")
        val baseFeature = BaseFeature(
          featureId,
          featureMark,
          featureName,
          featureSource,
          featureDetail,
          featureScene,
          expression,
          featureType,
          defaultValue,
          defaultType,
          status,
          onlineStatus
        )
        baseFeatureMap.put(baseFeature.featureName, baseFeature)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      JdbcUtil.close()
    }
    baseFeatureMap
  }

  case class BaseFeature(featureId: Long,
                         featureMark: String,
                         featureName: String,
                         featureSource: String,
                         featureDetail: String,
                         featureScene: String,
                         expression: String,
                         featureType: String,
                         defaultValue: String,
                         defaultType: String,
                         status: String,
                         onlineStatus: String)

}
