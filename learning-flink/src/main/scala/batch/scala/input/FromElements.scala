package batch.scala.input

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment, _}

object FromElements {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val elementDataSet: DataSet[String] = env.fromElements("hadoop", "spark", "flink")
    elementDataSet.print()
  }

}
