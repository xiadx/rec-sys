package batch.scala.input

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment, _}

object FromCollection {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val collectionDataSet: DataSet[String] = env.fromCollection(Seq("hadoop", "spark", "flink"))
    collectionDataSet.print()
  }

}
