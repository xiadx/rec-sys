package batch.scala.input

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment, _}

object GenerateSequence {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val numbers: DataSet[Long] = env.generateSequence(1, 10)
    numbers.print()
  }

}
