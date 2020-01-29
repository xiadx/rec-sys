package batch.scala.transformation

import org.apache.flink.api.common.operators.base.JoinOperatorBase.JoinHint
import org.apache.flink.api.scala._
import org.apache.flink.util.Collector

object JoinOperator {

  case class Person(var id: Int, var name: String)

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val dataSet1 = env.fromElements(Person(1, "Peter"), Person(2, "Alice"))
    val dataSet2 = env.fromElements((12.3, 1), (22.3, 3))
    dataSet1.join(dataSet2).where("id").equalTo(1).print()
    dataSet1.join(dataSet2).where("id").equalTo(1) {
      (left, right) => (left.id, left.name, right._1 + 1)
    }.print()
    dataSet1.join(dataSet2).where("id").equalTo(1) {
      (left, right, collector: Collector[(String, Double, Int)]) =>
        collector.collect(left.name, right._1 + 1, right._2)
        collector.collect("prefix_" + left.name, right._1 + 2, right._2)
    }/*.withForwardedFieldsFirst("_2->_3")*/.print()
    dataSet1.join(dataSet2, JoinHint.BROADCAST_HASH_FIRST).where("id").equalTo(1).print()
    dataSet1.join(dataSet2, JoinHint.BROADCAST_HASH_SECOND).where("id").equalTo(1).print()
    dataSet1.join(dataSet2, JoinHint.OPTIMIZER_CHOOSES).where("id").equalTo(1).print()
    dataSet1.join(dataSet2, JoinHint.REPARTITION_HASH_FIRST).where("id").equalTo(1).print()
    dataSet1.join(dataSet2, JoinHint.REPARTITION_HASH_SECOND).where("id").equalTo(1).print()
    dataSet1.join(dataSet2, JoinHint.REPARTITION_SORT_MERGE).where("id").equalTo(1).print()
    dataSet1.leftOuterJoin(dataSet2).where("id").equalTo(1) {
      (left, right) =>
        if (right == null) {
          (left.id, 1)
        } else {
          (left.id, right._1)
        }
    }.print()
    dataSet1.leftOuterJoin(dataSet2, JoinHint.BROADCAST_HASH_SECOND).where("id").equalTo(1) {
      (left, right) =>
        if (right == null) {
          (left.id, 1)
        } else {
          (left.id, right._1)
        }
    }.print()
    dataSet1.leftOuterJoin(dataSet2, JoinHint.REPARTITION_HASH_SECOND).where("id").equalTo(1) {
      (left, right) =>
        if (right == null) {
          (left.id, 1)
        } else {
          (left.id, right._1)
        }
    }.print()
  }

}
