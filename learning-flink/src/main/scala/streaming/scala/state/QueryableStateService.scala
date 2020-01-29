//package streaming.scala.state
//
//import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.api.common.functions.RichFlatMapFunction
//import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
//import org.apache.flink.configuration.Configuration
//import org.apache.flink.util.Collector
//
//object QueryableStateService {
//
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setParallelism(1)
//
//    val inputStream: DataStream[(Int, Long)] = env.fromElements(
//      (2, 21L),
//      (4, 1L),
//      (5, 4L)
//    )
//
//    inputStream.keyBy(_._1).flatMap {
//      new RichFlatMapFunction[(Int, Long), (Int, Long, Long)] {
//        private var leastValueState: ValueState[Long] = _
//
//        override def open(parameters: Configuration): Unit = {
//          val leastValueStateDescriptor = new ValueStateDescriptor[Long]("leastValue", classOf[Long])
//          leastValueStateDescriptor.setQueryable("leastQueryValue")
//          leastValueState = getRuntimeContext.getState(leastValueStateDescriptor)
//        }
//
//        override def flatMap(t: (Int, Long), collector: Collector[(Int, Long, Long)]): Unit = {
//          val leastValue = leastValueState.value()
//          if (t._2 > leastValue) {
//            collector.collect((t._1, t._2, leastValue))
//          } else {
//            leastValueState.update(t._2)
//            collector.collect((t._1, t._2, t._2))
//          }
//        }
//      }
//    }
//
//    val maxInputStream: DataStream[(Int, Long)] = inputStream
//      .map(r => (r._1, r._2))
//      .keyBy(_._1)
//      .timeWindow(Time.seconds(5))
//      .max(1)
//
//    maxInputStream
//      .keyBy(_._1).asQueryableState("maxInputState")
//  }
//
//}
