package batch.scala.transformation

import org.apache.flink.api.scala._

object CrossOperator {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val dataSet1 = env.fromElements((12, "flink"), (22, "spark"))
    val dataSet2 = env.fromElements("flink")
    dataSet1.cross(dataSet2).print()
  }

}
