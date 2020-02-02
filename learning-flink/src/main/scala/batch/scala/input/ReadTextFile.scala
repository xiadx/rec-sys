package batch.scala.input

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment, _}

object ReadTextFile {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val textFileDataSet: DataSet[String] = env.readTextFile("README.md")
    textFileDataSet.print()
  }

}
