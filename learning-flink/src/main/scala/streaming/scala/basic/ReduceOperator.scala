package streaming.scala.basic

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala.{KeyedStream, StreamExecutionEnvironment, _}

object ReduceOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val dataStream = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))

    // keyBy
    val keyedStream: KeyedStream[(String, Int), Tuple] = dataStream.keyBy(0)

    // reduce
    val reduceStream_01 = keyedStream.reduce { (t1, t2) =>
      (t1._1, t1._2 + t2._2)
    }

    val reduceStream_02 = keyedStream.reduce(new ReduceFunction[(String, Int)] {
      override def reduce(t1: (String, Int), t2: (String, Int)): (String, Int) = {
        (t1._1, t1._2 + t2._2)
      }
    })

    reduceStream_01.print()

    reduceStream_02.print()

    env.execute("ReduceOperator")
  }

}
