package streaming.scala.state

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.util.Collector

object CountWindowAverage {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    env.fromCollection(List(
      (1L, 3L),
      (1L, 5L),
      (1L, 7L),
      (1L, 4L),
      (1L, 2L)
    )).keyBy(_._1)
      .flatMap(new RichFlatMapFunction[(Long, Long), (Long, Long)] {
        private var sum: ValueState[(Long, Long)] = _

        override def flatMap(input: (Long, Long), out: Collector[(Long, Long)]): Unit = {
          val tmpCurrentSum = sum.value

          val currentSum = if (tmpCurrentSum != null) tmpCurrentSum else (0L, 0L)

          val newSum = (currentSum._1 + 1, currentSum._2 + input._2)

          sum.update(newSum)

          if (newSum._1 >= 2) {
            out.collect((input._1, newSum._2 / newSum._1))
            sum.clear()
          }
        }

        override def open(parameters: Configuration): Unit = {
          sum = getRuntimeContext.getState(
            new ValueStateDescriptor[(Long, Long)]("average", createTypeInformation[(Long, Long)])
          )
        }
      })
      .print()

    env.execute("CountWindowAverage")
  }

}
