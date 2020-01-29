//package streaming.scala.state
//
//import java.util
//import java.util.Collections
//
//import org.apache.flink.api.common.functions.FlatMapFunction
//import org.apache.flink.streaming.api.checkpoint.ListCheckpointed
//import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
//import org.apache.flink.util.Collector
//
//object ListCheckpoint {
//
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setParallelism(1)
//
//    val inputStream: DataStream[(String, Long)] = env.fromElements(
//      ("a", 21L),
//      ("a", 1L),
//      ("a", 4L),
//      ("b", 21L),
//      ("b", 1L),
//      ("b", 4L),
//      ("c", 21L),
//      ("c", 1L),
//      ("c", 4L)
//    )
//
//    inputStream.keyBy(_._1).flatMap {
//      new FlatMapFunction[(String, Long), (String, Long)] with ListCheckpointed[Long] {
//        private var numberRecords: Long = 0L
//
//        override def flatMap(t: (String, Long), collector: Collector[(String, Long)]): Unit = {
//          numberRecords += 1
//          collector.collect(t._1, numberRecords)
//        }
//
//        override def snapshotState(checkpointId: Long, ts: Long): util.List[Long] = {
//          Collections.singletonList(numberRecords)
//        }
//
//        override def restoreState(list: util.List[Long]): Unit = {
//          numberRecords = 0L
//          for (count <- list) {
//            numberRecords += count
//          }
//        }
//      }
//    }.print()
//
//    env.execute("ListCheckpoint")
//  }
//
//}
