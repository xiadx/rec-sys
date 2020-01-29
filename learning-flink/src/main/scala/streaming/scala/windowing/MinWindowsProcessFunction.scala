package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object MinWindowsProcessFunction {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val inputStream = env.fromCollection(
      List(
        ("a", 1L, 1),
        ("b", 1L, 1),
        ("b", 3L, 1),
        ("b", 5L, 1),
        ("c", 6L, 1),
        ("a", 10L, 1),
        ("c", 11L, 1)
      )
    ).assignAscendingTimestamps(t => t._2)

    val staticStream = inputStream.keyBy(_._1).timeWindow(Time.seconds(10)).process(
      new ProcessWindowFunction[(String, Long, Int), (String, Long, Long, Long, Long, Long), String, TimeWindow] {
        override def process(key: String,
                             ctx: Context,
                             vals: Iterable[(String, Long, Int)],
                             out: Collector[(String, Long, Long, Long, Long, Long)]): Unit = {
          val sum = vals.map(_._2).sum
          val min = vals.map(_._2).min
          val max = vals.map(_._2).max
          val avg = sum / vals.size
          val windowEnd = ctx.window.getEnd

          out.collect((key, min, max, sum, avg, windowEnd))
        }
      })

    staticStream.print()

    env.execute("MinWindowsProcessFunction")
  }

}
