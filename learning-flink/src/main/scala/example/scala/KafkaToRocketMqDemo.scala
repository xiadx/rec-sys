package example.scala

import java.util
import java.util.Properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import com.typesafe.config.{Config, ConfigFactory}
import com.mafengwo.recommend.flink.event.algo.{MobileClickEvent, MobileShowEvent}
import com.mafengwo.recommend.flink.schema.algo.{ClickEventSchema, ShowEventSchema}
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.rocketmq.flink.{RocketMQConfig, RocketMQSink}
import org.apache.rocketmq.flink.common.selector.DefaultTopicSelector
import org.apache.rocketmq.flink.common.serialization.SimpleKeyValueSerializationSchema
import org.apache.rocketmq.flink.common.serialization.KeyValueSerializationSchema

import scala.collection.mutable
import scala.collection.JavaConverters._

object KafkaToRocketMqDemo {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  val parallelism = 1
  val jobName = "KafkaToRocketMqDemo"
  val sinkParallelism = 1
  val sinkName = "kafka to rocketmq demo"

  val kafkaConf: Config = ConfigFactory.parseResources("kafka.conf")

  val sourceBrokerList: String = kafkaConf.getString("kafka.source-broker-list")
  val group: String = kafkaConf.getString("kafka.group")
  val account: String = kafkaConf.getString("kafka.account")
  val password: String = kafkaConf.getString("kafka.password")

  val properties: Properties = getProperties()
  val producerProps: Properties = new Properties()
  producerProps.setProperty(RocketMQConfig.NAME_SERVER_ADDR, "192.168.4.64:9876")
  val msgDelayLevel: Int = RocketMQConfig.MSG_DELAY_LEVEL05
  producerProps.setProperty(RocketMQConfig.MSG_DELAY_LEVEL, String.valueOf(msgDelayLevel))

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
    val clickStream = env.addSource(clickConsumer)
    clickStream.map { t =>
      val r: mutable.HashMap[String, String] = new mutable.HashMap[String, String]()
      r.put("key", t.getItemId)
      r.put("value", mapper.writeValueAsString(t))
      r
    }.addSink(new RocketMQSink[mutable.HashMap[String, String]](new SimpleKeyValueSerializationSchema("key", "value"),
      new DefaultTopicSelector("kafka-to-rocketmq-demo"), producerProps))
      .setParallelism(sinkParallelism)
      .name(sinkName)
  }

  class SimpleKeyValueSerializationSchema(keyField: String, valueField: String) extends KeyValueSerializationSchema[mutable.HashMap[String, String]] {

    override def serializeKey(t: mutable.HashMap[String, String]): Array[Byte] = {
      val ab = new mutable.ArrayBuffer[Byte]()
      val k = t.getOrElse(keyField, null)
      if (t != null && k != null) {
        for (b <- k.getBytes("UTF-8")) {
          ab.append(b.toByte)
        }
      }
      ab.toArray
    }

    override def serializeValue(t: mutable.HashMap[String, String]): Array[Byte] = {
      val ab = new mutable.ArrayBuffer[Byte]()
      val v = t.getOrElse(valueField, null)
      if (t != null && v != null) {
        for (b <- v.getBytes("UTF-8")) {
          ab.append(b.toByte)
        }
      }
      ab.toArray
    }
  }

}
