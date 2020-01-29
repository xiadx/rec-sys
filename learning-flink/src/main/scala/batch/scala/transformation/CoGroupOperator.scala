package batch.scala.transformation

import org.apache.flink.api.scala._
import org.apache.flink.util.Collector

object CoGroupOperator {

  case class Person(var id: Int, var name: String)

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val dataSet1: DataSet[Person] = env.fromElements(Person(1, "Peter"), Person(2, "Alice"))
    val dataSet2: DataSet[(Double, Int)] = env.fromElements((12.3, 1), (22.3, 3))
    dataSet1.coGroup(dataSet2).where("id").equalTo(1) {
      (iVals, dVals, out: Collector[Double]) => {
        val ints = iVals.map {
          _.id
        }.toSet
        for (dVal <- dVals) {
          for (i <- ints) {
            out.collect(dVal._2 * i)
          }
        }
      }
    }.print()
  }

}
