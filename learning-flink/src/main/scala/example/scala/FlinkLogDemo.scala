package example.scala

import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

object FlinkLogDemo {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val fld = "FlinkLogDemo"

    val dataStream = env.fromElements(1, 2, 3)

    dataStream.addSink(new SinkFunction[Int] {
      override def invoke(in: Int): Unit = {
        println(fld + " --> " + in.toString)
      }
    })

    println(fld)

    env.execute("FlinkLogDemo")
  }

}
