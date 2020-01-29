package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows
import org.apache.flink.streaming.api.windowing.time.Time

object SessionWindowJoin {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val orangeStream: DataStream[(String, Long, Int)] = env.fromElements(
      ("a", 2L, 1),
      ("a", 3L, 2),
      ("a", 6L, 5),
      ("a", 7L, 6),
      ("a", 9L, 8),
      ("a", 10L, 9),
      ("b", 2L, 1),
      ("b", 3L, 2),
      ("b", 6L, 5),
      ("b", 7L, 6),
      ("b", 9L, 8),
      ("b", 10L, 9)
    ).assignAscendingTimestamps(_._2)

    val greenStream: DataStream[(String, Long, Int)] = env.fromElements(
      ("a", 1L, 0),
      ("a", 5L, 4),
      ("a", 6L, 5),
      ("b", 1L, 0),
      ("b", 5L, 4),
      ("b", 6L, 5)
    ).assignAscendingTimestamps(_._2)

    orangeStream.join(greenStream)
      .where(_._1)
      .equalTo(_._1)
      .window(EventTimeSessionWindows.withGap(Time.milliseconds(1)))
      .apply { (e1, e2) => e1 + "," + e2 }
      .print()

    env.execute("SessionWindowJoin")
  }

}
