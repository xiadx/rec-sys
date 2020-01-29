//package streaming.scala.state
//
//import java.util
//import java.util.Collections
//
//import org.apache.flink.streaming.api.checkpoint.ListCheckpointed
//import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
//
//class CounterSource
//  extends RichParallelSourceFunction[Long]
//    with ListCheckpointed[Long] {
//
//  @volatile
//  private var isRunning = true
//
//  private var offset = 0L
//
//  override def run(ctx: SourceFunction.SourceContext[Long]): Unit = {
//    val lock = ctx.getCheckpointLock
//
//    while (isRunning) {
//      // output and state update are atomic
//      lock.synchronized({
//        ctx.collect(offset)
//
//        offset += 1
//      })
//    }
//  }
//
//  override def cancel(): Unit = isRunning = false
//
//  override def restoreState(state: util.List[Long]): Unit =
//    for (s <- state) {
//      offset = s
//    }
//
//  override def snapshotState(checkpointId: Long, timestamp: Long): util.List[Long] =
//    Collections.singletonList(offset)
//
//}
