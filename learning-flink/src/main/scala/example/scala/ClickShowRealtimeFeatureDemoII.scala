package example.scala

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object ClickShowRealtimeFeatureDemoII {

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
      .aggregate(
        new AggregateFunction[(
          String, Long, Int),
          (String, Long, Long, Long, Long),
          (String, Long, Long, Double, Long, Long)] {
          override def createAccumulator(): (String, Long, Long, Long, Long) = ("", 0, 0, Long.MaxValue, Long.MinValue)

          override def add(
                            value: (String, Long, Int),
                            accumulator: (String, Long, Long, Long, Long)): (String, Long, Long, Long, Long) = {
            val id = value._1
            val startTime = Math.min(value._2, accumulator._4)
            val endTime = Math.max(value._2, accumulator._5)
            val clickCount = if (value._3 == 1) accumulator._2 + 1 else accumulator._2
            val showCount = if (value._3 == 0) accumulator._3 + 1 else accumulator._3

            (id, clickCount, showCount, startTime, endTime)
          }

          override def merge(
                              a: (String, Long, Long, Long, Long),
                              b: (String, Long, Long, Long, Long)): (String, Long, Long, Long, Long) = {
            (a._1, a._2 + b._2, a._3 + b._3, Math.min(a._4, b._4), Math.max(a._5, b._5))
          }

          override def getResult(accumulator: (String, Long, Long, Long, Long)): (String, Long, Long, Double, Long, Long) = {
            (accumulator._1, accumulator._2, accumulator._3, accumulator._2.toDouble/accumulator._3, accumulator._4, accumulator._5)
          }
        },
        new ProcessWindowFunction[
          (String, Long, Long, Double, Long, Long),
          (String, Long, Long, Double, Long, Long, Long, Long),
          String,
          TimeWindow] {
          override def process(
                                key: String,
                                ctx: Context,
                                vals: Iterable[(String, Long, Long, Double, Long, Long)],
                                out: Collector[(String, Long, Long, Double, Long, Long, Long, Long)]): Unit = {
            val rf = vals.iterator.next()
            out.collect((rf._1, rf._2, rf._3, rf._4, rf._5, rf._6, ctx.window.getStart, ctx.window.getEnd))
          }
        }
      )

    realtimeFeatureStream.addSink(new SinkFunction[(String, Long, Long, Double, Long, Long, Long, Long)] {
      override def invoke(in: (String, Long, Long, Double, Long, Long, Long, Long)): Unit = {
        println(s"id->${in._1},c->${in._2},s->${in._3},r->${in._4},es->${in._5},ee->${in._6},ws->${in._7},we->${in._8}")
      }
    })

    env.execute("ClickShowRealtimeFeatureImprove")
  }

}
