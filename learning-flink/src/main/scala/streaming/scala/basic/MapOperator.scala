package streaming.scala.basic

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

object MapOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val dataStream = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))

    // Map
    val mapStream_01: DataStream[(String, Int)] = dataStream.map(t => (t._1, t._2 + 1))

    // MapFunction
    val mapStream_02: DataStream[(String, Int)] = dataStream.map(new MapFunction[(String, Int), (String, Int)] {
      override def map(t: (String, Int)): (String, Int) = {
        (t._1, t._2 + 1)
      }
    })

    mapStream_01.print()

    mapStream_02.print()

    env.execute("MapOperator")
  }

}
