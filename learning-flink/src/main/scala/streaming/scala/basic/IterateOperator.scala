package streaming.scala.basic

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

object IterateOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val dataStream = env.fromElements(3, 1, 2, 1, 5).map { t: Int => t }

    val iterated_01 = dataStream.iterate((input: ConnectedStreams[Int, String]) => {
      val head = input.map(i => (i + 1).toString, s => s)
      (head.filter(_ == "2"), head.filter(_ != "2"))
    }, 1000)

    iterated_01.print()

//    val iterated_02 = dataStream.iterate((input: DataStream[Int]) =>
//      (input.map(_ + 1), input.map(_.toString)), 2000)
//
//    iterated_02.print()

    env.execute("IterateOperator")
  }

}
