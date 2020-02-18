package example.scala

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object ClickShowRealtimeFeatureDemoIII {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val clickShowStream = env.fromElements(
      ("d", 1L, 0),
      ("d", 2L, 1),
      ("d", 3L, 1),
      ("d", 4L, 0),
      ("d", 5L, 1),
      ("c", 2L, 1),
      ("c", 3L, 1),
      ("c", 4L, 0),
      ("c", 5L, 1),
      ("b", 3L, 1),
      ("b", 4L, 0),
      ("b", 5L, 1),
      ("a", 3L, 1),
      ("a", 4L, 0),
      ("a", 5L, 1),
      ("a", 6L, 0)
    ).assignAscendingTimestamps(_._2)

    val realtimeFeatureStream = clickShowStream
      .keyBy(_._1)
      .timeWindow(Time.milliseconds(5), Time.milliseconds(1))
      .process(
        new ProcessWindowFunction[
          (String, Long, Int),
          (String, Long, Long, Double, Long, Long, Long, Long),
          String,
          TimeWindow] {
          override def process(key: String,
                               ctx: Context,
                               vals: Iterable[(String, Long, Int)],
                               out: Collector[(String, Long, Long, Double, Long, Long, Long, Long)]): Unit = {
            val windowStartTime = ctx.window.getStart
            val windowEndTime = ctx.window.getEnd
            var eventStartTime = Long.MaxValue
            var eventEndTime = Long.MinValue
            var clickCount = 0
            var showCount = 0
            for (v <- vals) {
              if (v._3 == 1 && v._2 > windowStartTime + 1) {
                clickCount += 1
              }
              if (v._3 == 0 && v._2 > windowStartTime + 1) {
                showCount += 1
              }
              if (eventStartTime > v._2) {
                eventStartTime = v._2
              }
              if (eventEndTime < v._2) {
                eventEndTime = v._2
              }
            }

            val rf = (key, clickCount, showCount, clickCount.toDouble/showCount, eventStartTime, eventEndTime, windowStartTime, windowEndTime)
            out.collect((rf._1, rf._2, rf._3, rf._4, rf._5, rf._6, ctx.window.getStart, ctx.window.getEnd))
          }
        }
      )

    realtimeFeatureStream.addSink(new SinkFunction[(String, Long, Long, Double, Long, Long, Long, Long)] {
      override def invoke(in: (String, Long, Long, Double, Long, Long, Long, Long)): Unit = {
        println(s"id->${in._1},c->${in._2},s->${in._3},r->${in._4},es->${in._5},ee->${in._6},ws->${in._7},we->${in._8}")
      }
    })

    env.execute("ClickShowRealtimeFeatureDemoIII")
  }

}
