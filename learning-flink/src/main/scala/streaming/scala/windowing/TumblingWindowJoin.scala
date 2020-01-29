package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object TumblingWindowJoin {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val orangeStream: DataStream[(String, Long, Int)] = env.fromElements(
      ("a", 1L, 0),
      ("a", 2L, 1),
      ("a", 4L, 3),
      ("a", 5L, 4),
      ("b", 1L, 0),
      ("b", 2L, 1),
      ("b", 4L, 3),
      ("b", 5L, 4)
    ).assignAscendingTimestamps(_._2)

    val greenStream: DataStream[(String, Long, Int)] = env.fromElements(
      ("a", 1L, 0),
      ("a", 2L, 1),
      ("a", 3L, 2),
      ("a", 4L, 3),
      ("a", 5L, 4),
      ("a", 6L, 5),
      ("a", 7L, 6),
      ("a", 8L, 7),
      ("b", 1L, 0),
      ("b", 2L, 1),
      ("b", 3L, 2),
      ("b", 4L, 3),
      ("b", 5L, 4),
      ("b", 6L, 5),
      ("b", 7L, 6),
      ("b", 8L, 7)
    ).assignAscendingTimestamps(_._2)

    orangeStream.join(greenStream)
      .where(_._1)
      .equalTo(_._1)
      .window(TumblingEventTimeWindows.of(Time.milliseconds(2)))
      .apply { (e1, e2) => e1 + "," + e2 }
      .print()

    env.execute("TumblingWindowJoin")
  }

}
