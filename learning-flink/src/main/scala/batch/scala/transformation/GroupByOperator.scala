package batch.scala.transformation

import org.apache.flink.api.scala._

object GroupByOperator {

  case class Persion(name: String, age: Int)

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val dataSet = env.fromElements(("hello", 1), ("flink", 3))
    dataSet.groupBy(0).max(1).print()
    env.fromElements(new Persion("Alex", 18), new Persion("Peter", 43))
      .groupBy("name").max(1).print()
  }

}
