package streaming.scala.windowing

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.ContinuousEventTimeTrigger

object WindowTrigger {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val inputStream = env.fromElements((2, 10L), (2, 30L), (4, 1L), (4, 4L))
      .assignAscendingTimestamps(_._2)

    val windowStream = inputStream
      .keyBy(_._1)
      .window(EventTimeSessionWindows.withGap(Time.milliseconds(10)))
      .trigger(ContinuousEventTimeTrigger.of(Time.seconds(5)))
      .reduce(new ReduceFunction[(Int, Long)] {
        override def reduce(t1: (Int, Long), t2: (Int, Long)): (Int, Long) = {
          (t1._1, t1._2 + t2._2)
        }
      })

    windowStream.print()

    env.execute("WindowTrigger")
  }

}
