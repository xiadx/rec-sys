package streaming.scala.basic

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.util.Collector

object FlatMapOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val dataStream = env.fromElements("a,b,c", "d,f,e", "c,d,a", "c,b,d")

    // flatMap
    val flatMapStream_01: DataStream[String] = dataStream.flatMap(_.split(","))

    // FlatMapFunction
    val flatMapStream_02: DataStream[String] = dataStream.flatMap(new FlatMapFunction[String, String] {
      override def flatMap(t: String, collector: Collector[String]): Unit = {
        t.split(",").foreach(collector.collect)
      }
    })

    flatMapStream_01.print()

    flatMapStream_02.print()

    env.execute("FlatMapOperator")
  }

}
