package streaming.scala.state

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.runtime.state.{FunctionInitializationContext, FunctionSnapshotContext}
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.util.Collector

import scala.collection.JavaConverters._

object CheckpointStateFunction {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val inputStream: DataStream[(Int, Long)] = env.fromElements(
      (2, 21L),
      (2, 1L),
      (2, 4L),
      (4, 21L),
      (4, 1L),
      (4, 4L),
      (5, 21L),
      (5, 1L),
      (5, 4L)
    )

    inputStream.keyBy(_._1).flatMap {
      new FlatMapFunction[(Int, Long), (Int, Long, Long)] with CheckpointedFunction {
        private var operatorCount: Long = _
        private var keyedState: ValueState[Long] = _
        private var operatorState: ListState[Long] = _

        override def flatMap(t: (Int, Long), collector: Collector[(Int, Long, Long)]): Unit = {
          val keyedCount = keyedState.value() + 1
          keyedState.update(keyedCount)
          operatorCount = operatorCount + 1
          collector.collect((t._1, keyedCount, operatorCount))
        }

        override def initializeState(context: FunctionInitializationContext): Unit = {
          keyedState = context.getKeyedStateStore.getState(
            new ValueStateDescriptor[Long]("keyedState", createTypeInformation[Long])
          )
          operatorState = context.getOperatorStateStore.getListState(
            new ListStateDescriptor[Long]("operatorState", createTypeInformation[Long])
          )
          if (context.isRestored) {
            operatorCount = operatorState.get().asScala.sum
          }
        }

        override def snapshotState(context: FunctionSnapshotContext): Unit = {
          operatorState.clear()
          operatorState.add(operatorCount)
        }
      }
    }.print()

    env.execute("CheckpointStateFunction")
  }

}
