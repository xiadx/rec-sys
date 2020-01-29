package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

object WindowsAggregationFunction {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val input = env.fromElements(("a", 1L, 3), ("a", 2L, 1), ("b", 1L, 4), ("b", 2L, 6))
      .assignAscendingTimestamps(_._2)
      .keyBy(_._1)
      .timeWindow(Time.milliseconds(3))
      .aggregate(new AverageAggregate)

    input.print()

    env.execute("WindowsAggregationFunction")
  }

  class AverageAggregate extends AggregateFunction[(String, Long, Int), (Long, Long), Double] {

    override def createAccumulator(): (Long, Long) = (0L, 0L)

    override def add(value: (String, Long, Int), accumulator: (Long, Long)): (Long, Long) =
      (accumulator._1 + value._3, accumulator._2 + 1L)

    override def getResult(accumulator: (Long, Long)): Double = accumulator._1.toDouble / accumulator._2

    override def merge(a: (Long, Long), b: (Long, Long)): (Long, Long) = (a._1 + b._1, a._2 + b._2)

  }

}
