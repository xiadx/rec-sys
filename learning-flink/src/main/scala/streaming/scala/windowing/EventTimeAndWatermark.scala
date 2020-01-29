package streaming.scala.windowing

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.watermark.Watermark

object EventTimeAndWatermark {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val input = List(("a", 1L, 1), ("b", 1L, 1), ("b", 3L, 1))

    // SourceFunction
    val source: DataStream[(String, Long, Int)] = env.addSource(
      new SourceFunction[(String, Long, Int)] {
        // run
        override def run(ctx: SourceContext[(String, Long, Int)]): Unit = {
          input.foreach(value => {
            // collectWithTimestamp
            ctx.collectWithTimestamp(value, value._2)
            // emitWatermar
            ctx.emitWatermark(new Watermark(value._2 - 1))
          })

          // emitWatermar
          ctx.emitWatermark(new Watermark(Long.MaxValue))
        }

        // cancel
        override def cancel(): Unit = {}
      }
    )

    source.print()

    env.execute("EventTimeAndWatermark")
  }

}
