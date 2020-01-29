package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

object WindowsFoldFunction {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val inputStream = env.fromElements((1, 1L), (1, 2L), (3, 4L), (5, 8L))
      .assignAscendingTimestamps(_._2)

    val foldWindowStream = inputStream
      .keyBy(_._1)
      .timeWindow(Time.milliseconds(100))
      .fold("flink") { (acc, v) => acc + v._2 }

    foldWindowStream.print()

    env.execute("WindowsFoldFunction")
  }

}
