package batch.scala.transformation

import org.apache.flink.api.scala._

object MapOperator {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromElements("hadoop", "spark", "flink")
    text.map(_.toUpperCase()).print()
  }

}
