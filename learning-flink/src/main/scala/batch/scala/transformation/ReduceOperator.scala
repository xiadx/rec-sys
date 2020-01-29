package batch.scala.transformation

import org.apache.flink.api.scala._

object ReduceOperator {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val num = env.fromElements(123, 212, 123, 243, 89)
    num.filter(x => x > 100).reduce((x, y) => x + y).print()
  }

}
