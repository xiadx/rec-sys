package batch.scala.transformation

import org.apache.flink.api.java.aggregation.Aggregations
import org.apache.flink.api.scala._

object AggregateOperator {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val data = env.fromElements(
      (12, "Alice", 34),
      (12, "Alice", 34),
      (12, "Alice", 34)
    )
    data.aggregate(Aggregations.SUM, 0).aggregate(Aggregations.MIN, 2).print()
    data.sum(0).min(2).print()
  }

}
