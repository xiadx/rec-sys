package streaming.scala.state

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}
import org.apache.flink.util.Collector

object MinimumValueStateFunction {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val inputStream: DataStream[(Int, Long)] = env.fromElements(
      (1, 3L),
      (1, 5L),
      (1, 7L),
      (1, 4L),
      (1, 2L),
      (4, 3L),
      (4, 5L),
      (4, 7L),
      (4, 4L),
      (4, 2L),
      (5, 3L),
      (5, 5L),
      (5, 7L),
      (5, 4L),
      (5, 2L)
    )

    inputStream.keyBy(_._1).flatMap {
      new RichFlatMapFunction[(Int, Long), (Int, Long, Long)] {

        private var minValueState: ValueState[Long] = _

        override def open(parameters: Configuration): Unit = {
          val minValueStateDescriptor = new ValueStateDescriptor[Long]("minValue", classOf[Long], Long.MaxValue)
          minValueState = getRuntimeContext.getState(minValueStateDescriptor)
        }

        override def flatMap(t: (Int, Long), collector: Collector[(Int, Long, Long)]): Unit = {
          val minValue = minValueState.value()
          if (t._2 > minValue) {
            collector.collect((t._1, t._2, minValue))
          } else {
            minValueState.update(t._2)
            collector.collect((t._1, t._2, t._2))
          }
        }
      }
    }.print()

    env.execute("MinimumValueStateFunction")
  }

}
