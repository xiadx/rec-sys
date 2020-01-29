package batch.scala.transformation

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment, _}

object BulkIterateOperator {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val initial = env.fromElements(0)
    val count = initial.iterate(10000) { iterationInput: DataSet[Int] =>
      val result = iterationInput.map { i =>
        val x = Math.random()
        val y = Math.random()
        i + (if (x * x + y * y < 1) 1 else 0)
      }
      result
    }
    val result = count map { c => c / 10000.0 * 4 }
    result.print()
  }

}
