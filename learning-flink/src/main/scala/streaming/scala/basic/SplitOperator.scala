package streaming.scala.basic

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

object SplitOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val dataStream: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))

    val splitedStream: SplitStream[(String, Int)] = dataStream.split(t => if (t._2 % 2 == 0) Seq("even") else Seq("odd"))

    val evenStream: DataStream[(String, Int)] = splitedStream.select("even")

    val oddStream: DataStream[(String, Int)] = splitedStream.select("odd")

    val allStream: DataStream[(String, Int)] = splitedStream.select("even", "odd")

    evenStream.print()

//    oddStream.print()

//    allStream.print()

    env.execute("SplitOperator")
  }

}
