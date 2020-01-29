package streaming.scala.basic

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}

object UnionOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(1)

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val dataStream_01: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))

    val dataStream_02: DataStream[(String, Int)] = env.fromElements(("d", 1), ("s", 2), ("a", 4), ("e", 5), ("a", 6))

    val dataStream_03: DataStream[(String, Int)] = env.fromElements(("a", 2), ("d", 1), ("s", 2), ("c", 3), ("b", 1))

    val unionStream_01 = dataStream_01.union(dataStream_02)

    val unionStream_02 = dataStream_01.union(dataStream_02, dataStream_03)

//    unionStream_01.print()

    unionStream_02.print()

    env.execute("UnionOperator")
  }

}
