package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object ReduceCombineProcessFunciton {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val inputStream = env.fromCollection(
      List(
        ("a", 1L, 1),
        ("b", 1L, 1),
        ("b", 3L, 1))
    ).assignAscendingTimestamps(t => t._2)

    inputStream.keyBy(_._1).timeWindow(Time.seconds(10))

    val result = inputStream
      .keyBy(_._1)
      .timeWindow(Time.seconds(10))
      .reduce(
        (r1: (String, Long, Int), r2: (String, Long, Int)) => {
          if (r1._2 > r2._2) r2 else r1
        }
        ,
        (key: String,
         window: TimeWindow,
         minReadings: Iterable[(String, Long, Int)],
         out: Collector[(Long, (String, Long, Int))]) => {
          val min = minReadings.iterator.next()
          out.collect((window.getEnd, min))
        })

    result.print()

    env.execute("ReduceCombineProcessFunciton")
  }

}
