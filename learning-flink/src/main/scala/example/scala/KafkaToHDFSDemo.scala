package example.scala

import java.time.ZoneId
import java.util.Properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.mafengwo.recommend.flink.event.algo.{MobileClickEvent, MobileShowEvent}
import com.mafengwo.recommend.flink.schema.algo.{ClickEventSchema, ShowEventSchema}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.flink.core.fs.Path
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.connectors.fs.bucketing.{BucketingSink, DateTimeBucketer}
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol

object KafkaToHDFSDemo {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  val kafkaToHDFSConf: Config = ConfigFactory.parseResources("kafka-to-hdfs.conf")

  val parallelism: Int = kafkaToHDFSConf.getInt("job.parallelism")
  val jobName: String = kafkaToHDFSConf.getString("job.job-name")
  val sinkParallelism: Int = kafkaToHDFSConf.getInt("job.sink-parallelism")
  val sinkName: String = kafkaToHDFSConf.getString("job.sink-name")

  val checkpointPath: String = kafkaToHDFSConf.getString("checkpoint.output-path")
  val checkpointInterval: Long = kafkaToHDFSConf.getLong("checkpoint.interval")

  val outputPath: String = kafkaToHDFSConf.getString("bucket.output-path")
  val datetimeFormat: String = kafkaToHDFSConf.getString("bucket.datetime-format")
  val zone: String = kafkaToHDFSConf.getString("bucket.zone")
  val batchRolloverInterval: Long = kafkaToHDFSConf.getLong("bucket.batch-rollover-interval")
  val inactiveBucketCheckInterval: Long = kafkaToHDFSConf.getLong("bucket.inactive-bucket-check-interval")
  val inactiveBucketThreshold: Long = kafkaToHDFSConf.getLong("bucket.inactive-bucket-threshold")

  val sourceBrokerList: String = kafkaToHDFSConf.getString("kafka.source-broker-list")
  val group: String = kafkaToHDFSConf.getString("kafka.group")
  val account: String = kafkaToHDFSConf.getString("kafka.account")
  val password: String = kafkaToHDFSConf.getString("kafka.password")

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
    env.enableCheckpointing(checkpointInterval)
    env.setStateBackend(new FsStateBackend(new Path(checkpointPath)))
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE)
    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    env.setParallelism(1)
    env.setParallelism(parallelism)
    register(env)
    env.execute(jobName)
  }

  def register(env: StreamExecutionEnvironment): Unit = {
    val clickConsumer = createKafkaClickConsumer()
    val clickStream = env.addSource(clickConsumer)
      .map(in => mapper.writeValueAsString(in))
    val bucketingSink: BucketingSink[String] = new BucketingSink[String](outputPath)
    bucketingSink.setBucketer(new DateTimeBucketer[String](datetimeFormat, ZoneId.of(zone)))
    bucketingSink.setBatchRolloverInterval(batchRolloverInterval)
    bucketingSink.setInactiveBucketCheckInterval(inactiveBucketCheckInterval)
    bucketingSink.setInactiveBucketThreshold(inactiveBucketThreshold)
    clickStream.addSink(bucketingSink)
      .setParallelism(sinkParallelism)
      .name(sinkName)
  }

}
