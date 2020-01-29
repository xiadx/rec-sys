package batch.scala.broadcast

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

import scala.collection.JavaConverters._

object BroadcastOperator {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val dataSet1: DataSet[Int] = env.fromElements(1, 2, 3, 4)
    val dataSet2: DataSet[String] = env.fromElements("flink", "spark")
    dataSet2.map(new RichMapFunction[String, String] {
      var broadcastSet: Traversable[Int] = null

      override def open(config: Configuration): Unit = {
        broadcastSet = getRuntimeContext.getBroadcastVariable[Int]("broadcastSet-1").asScala
      }

      def map(input: String): String = {
        input + broadcastSet.toList
      }
    }).withBroadcastSet(dataSet1, "broadcastSet-1")
      .print()
  }

}
