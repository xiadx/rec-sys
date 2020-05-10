package feature.scala

import java.util.Properties

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import com.mafengwo.recommend.flink.event.algo.{MobileClickEvent, MobileShowEvent}
import com.mafengwo.recommend.flink.schema.algo.{ClickEventSchema, ShowEventSchema}
import com.typesafe.config.Config
import feature.scala.event.SimplifyBaseEvent
import feature.scala.process.SIRFProcess
import feature.scala.sink.SIRFSink
import feature.scala.utils.ConfigUtil

import scala.collection.JavaConverters._
import scala.collection.mutable

object SIRFJob {

  val sourceBrokerList: String = ConfigUtil.sirfConf.getString("kafka.source-broker-list")
  val group: String = ConfigUtil.sirfConf.getString("kafka.group")
  val account: String = ConfigUtil.sirfConf.getString("kafka.account")
  val password: String = ConfigUtil.sirfConf.getString("kafka.password")

  val parallelism: Int = ConfigUtil.sirfConf.getInt("job.parallelism")
  val jobName: String = ConfigUtil.sirfConf.getString("job.job-name")
  val sinkParallelism: Int = ConfigUtil.sirfConf.getInt("job.sink-parallelism")
  val sinkName: String = ConfigUtil.sirfConf.getString("job.sink-name")

  val windowConf: Config = ConfigUtil.sirfConf.getConfig("window")
  val ws: mutable.Set[String] = windowConf.root().keySet().asScala

  val properties: Properties = getProperties()

  def getProperties(): Properties = {
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", sourceBrokerList)
    properties.setProperty("group.id", group)
    properties.setProperty("flink.partition-discovery.interval-millis", "600000")
    properties.setProperty("auto.commit.interval.ms", "1000")
    properties.setProperty("enable.auto.commit", "true")
    properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name)
    properties.setProperty(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512")
    properties.setProperty(SaslConfigs.SASL_JAAS_CONFIG, String.format("org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";", account, password))
    properties
  }

  def createKafkaClickConsumer(): SourceFunction[MobileClickEvent] = {
    val kafkaConsumer = new FlinkKafkaConsumer[MobileClickEvent]("recommend-mobile-event-click", new ClickEventSchema, properties).setStartFromLatest()
    kafkaConsumer
  }

  def createKafkaShowConsumer(): SourceFunction[MobileShowEvent] = {
    val kafkaConsumer = new FlinkKafkaConsumer[MobileShowEvent]("recommend-mobile-event-show", new ShowEventSchema, properties).setStartFromLatest()
    kafkaConsumer
  }

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(parallelism)
    register(env)
    env.execute(jobName)
  }

  def register(env: StreamExecutionEnvironment): Unit = {
    val clickConsumer = createKafkaClickConsumer()
    val showConsumer = createKafkaShowConsumer()

    val clickStream = env.addSource(clickConsumer)
      .filter(value => value != null && value.getOpenUdid != null && !"".equals(value.getOpenUdid) && value.getItemId != null && !"".equals(value.getItemId) && value.getItemType != null && !"".equals(value.getItemType) && !"-1".equals(value.getItemType))
      .map { value =>
        SimplifyBaseEvent(
          value.getOpenUdid,
          value.getItemId,
          value.getItemType,
          value.getItemId + "_" + value.getItemType,
          value.getLat,
          value.getLng,
          value.getEventCode,
          "1",
          value.getEventTime * 1000L,
          value.getDt,
          value.getHour,
          value.getMinute,
          value.getAbtest,
          value.getPageId,
          value.getPageName,
          value.getPageUrl,
          value.getParentPageName,
          value.getParentPageUrl,
          value.getReferencePageName,
          value.getReferencePageUrl,
          value.getOrigianlTopic,
          value.getItemSource,
          value.getChannelId,
          value.getPrmId,
          value.getPosId,
          value.getAlgInfo,
          value.getAlgPos,
          value.getAlgUuid,
          value.getSourceItemId,
          value.getShowCycleId
        )
      }

    val showStream = env.addSource(showConsumer)
      .filter(value => value != null && value.getOpenUdid != null && !"".equals(value.getOpenUdid) && value.getItemId != null && !"".equals(value.getItemId) && value.getItemType != null && !"".equals(value.getItemType) && !"-1".equals(value.getItemType))
      .map { value =>
        SimplifyBaseEvent(
          value.getOpenUdid,
          value.getItemId,
          value.getItemType,
          value.getItemId + "_" + value.getItemType,
          value.getLat,
          value.getLng,
          value.getEventCode,
          "0",
          value.getEventTime * 1000L,
          value.getDt,
          value.getHour,
          value.getMinute,
          value.getAbtest,
          value.getPageId,
          value.getPageName,
          value.getPageUrl,
          value.getParentPageName,
          value.getParentPageUrl,
          value.getReferencePageName,
          value.getReferencePageUrl,
          value.getOrigianlTopic,
          value.getItemSource,
          value.getChannelId,
          value.getPrmId,
          value.getPosId,
          value.getAlgInfo,
          value.getAlgPos,
          value.getAlgUuid,
          value.getSourceItemId,
          value.getShowCycleId
        )
      }

    for (w <- ws) {
      val size = windowConf.getLong(w + ".size")
      val slide = windowConf.getLong(w + ".slide")
      val sirfStream = clickStream
        .union(showStream)
        .keyBy(_.itemUniqueId)
        .timeWindow(
          Time.milliseconds(size + slide), Time.milliseconds(slide))
        .process(new SIRFProcess(slide))

      sirfStream.addSink(new SIRFSink(w))
        .setParallelism(sinkParallelism)
        .name(sinkName)
    }
  }

}
