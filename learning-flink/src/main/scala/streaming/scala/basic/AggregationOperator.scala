package streaming.scala.basic

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

object AggregationOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val dataStream = env.fromElements((1, 5), (2, 4), (2, 2), (1, 3), (1, 3))

    val keyedStream: KeyedStream[(Int, Int), Tuple] = dataStream.keyBy(0)

    // sum
    val sumStream: DataStream[(Int, Int)] = keyedStream.sum(1)

    // min
    val minStream: DataStream[(Int, Int)] = keyedStream.min(1)

    // max
    val maxStream: DataStream[(Int, Int)] = keyedStream.max(1)

    // minBy
    val minByStream: DataStream[(Int, Int)] = keyedStream.minBy(1)

    // maxBy
    val maxByStream: DataStream[(Int, Int)] = keyedStream.maxBy(1)

//    sumStream.print()

//    minStream.print()

//    maxStream.print()

//    minByStream.print()

    maxByStream.print()

    env.execute("AggregationOperator")
  }

}
