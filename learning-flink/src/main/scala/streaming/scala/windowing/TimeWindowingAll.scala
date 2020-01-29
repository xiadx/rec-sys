package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

object TimeWindowingAll {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val input = env.fromElements(
      ("a", 1L, 1),
      ("a", 2L, 1),
      ("a", 3L, 1),
      ("a", 4L, 1)
    ).assignAscendingTimestamps(_._2)

    input
      .timeWindowAll(Time.milliseconds(2), Time.milliseconds(1))
      .sum(2)
      .print()

    env.execute("TimeWindowingAll")
  }

}
