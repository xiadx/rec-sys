package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

object IntervalJoin {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val orangeStream: DataStream[(String, Long, Int)] = env.fromElements(
      ("a", 0L, 0),
      ("a", 2L, 2),
      ("a", 3L, 3),
      ("a", 4L, 4),
      ("a", 5L, 5),
      ("a", 7L, 7),
      ("b", 0L, 0),
      ("b", 2L, 2),
      ("b", 3L, 3),
      ("b", 4L, 4),
      ("b", 5L, 5),
      ("b", 7L, 7)
    ).assignAscendingTimestamps(_._2)

    val greenStream: DataStream[(String, Long, Int)] = env.fromElements(
      ("a", 0L, 0),
      ("a", 1L, 1),
      ("a", 6L, 6),
      ("a", 7L, 7),
      ("b", 0L, 0),
      ("b", 1L, 1),
      ("b", 6L, 6),
      ("b", 7L, 7)
    ).assignAscendingTimestamps(_._2)

    orangeStream
      .keyBy(_._1)
      .intervalJoin(greenStream.keyBy(_._1))
      .between(Time.milliseconds(-2), Time.milliseconds(1))
      .process(new ProcessJoinFunction[(String, Long, Int), (String, Long, Int), String] {
        override def processElement(left: (String, Long, Int),
                                    right: (String, Long, Int),
                                    ctx: ProcessJoinFunction[(String, Long, Int), (String, Long, Int), String]#Context,
                                    out: Collector[String]): Unit = {
          out.collect(left + "," + right)
        }
      })
      .print()

    env.execute("IntervalJoin")
  }

}
