//package streaming.scala.state

//import org.apache.flink.api.common.state.ValueState
//import org.apache.flink.api.common.state.ValueStateDescriptor
//import org.apache.flink.api.java.tuple.Tuple
//import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
//import org.apache.flink.streaming.api.functions.KeyedProcessFunction
//import org.apache.flink.util.Collector
//
//object CountWithTimeout {
//
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setParallelism(1)
//
//    val stream: DataStream[(String, String)] = env.fromElements(
//      ("a", "1"),
//      ("a", "2"),
//      ("a", "3"),
//      ("b", "1"),
//      ("b", "2"),
//      ("b", "3"),
//      ("c", "1"),
//      ("c", "2"),
//      ("c", "3")
//    )
//
//    val result: DataStream[(String, Long)] = stream
//      .keyBy(0)
//      .process(new CountWithTimeoutFunction())
//
//    result.print()
//
//    env.execute("CountWithTimeout")
//
//  }
//
//  case class CountWithTimestamp(key: String, count: Long, lastModified: Long)
//
//  class CountWithTimeoutFunction extends KeyedProcessFunction[String, (String, String), (String, Long)] {
//
//    lazy val state: ValueState[CountWithTimestamp] = getRuntimeContext.getState(new ValueStateDescriptor[CountWithTimestamp]("myState", classOf[CountWithTimestamp]))
//
//    override def processElement(value: (String, String),
//                                ctx: KeyedProcessFunction[String, (String, String), (String, Long)]#Context,
//                                out: Collector[(String, Long)]): Unit = {
//      val current: CountWithTimestamp = state.value match {
//        case null => CountWithTimestamp(value._1, 1, ctx.timestamp)
//        case CountWithTimestamp(key, count, lastModified) => CountWithTimestamp(key, count + 1, ctx.timestamp)
//      }
//
//      state.update(current)
//
//      ctx.timerService.registerProcessingTimeTimer(current.lastModified + 60000)
//    }
//
//    override def onTimer(timestamp: Long,
//                         ctx: KeyedProcessFunction[String, (String, String), (String, Long)]#OnTimerContext,
//                         out: Collector[(String, Long)]): Unit = {
//      state.value match {
//        case CountWithTimestamp(key, count, lastModified) if (timestamp == lastModified + 60000) => out.collect((key, count))
//        case _ =>
//      }
//    }
//
//  }
//
//}

//import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
//import org.apache.flink.streaming.api.functions.KeyedProcessFunction
//import org.apache.flink.api.common.state.ValueState
//import org.apache.flink.api.common.state.ValueStateDescriptor
//import org.apache.flink.api.java.tuple.Tuple
//import org.apache.flink.util.Collector
//
//object CountWithTimeout {
//
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setParallelism(1)
//
//    // the source data stream
//    val stream: DataStream[Tuple2[String, String]] = env.fromElements(
//      Tuple2("a", "a"),
//      Tuple2("a", "a"),
//      Tuple2("b", "b"),
//      Tuple2("b", "b"))
//
//    // apply the process function onto a keyed stream
//    val result: DataStream[Tuple2[String, Long]] = stream
//      .keyBy(0)
//      .process(new CountWithTimeoutFunction())
//
//    result.print()
//
//    env.execute("CountWithTimeout")
//
//  }
//
//  /**
//   * The data type stored in the state
//   */
//  case class CountWithTimestamp(key: String, count: Long, lastModified: Long)
//
//  /**
//   * The implementation of the ProcessFunction that maintains the count and timeouts
//   */
//  class CountWithTimeoutFunction extends KeyedProcessFunction[Tuple, (String, String), (String, Long)] {
//
//    /** The state that is maintained by this process function */
//    lazy val state: ValueState[CountWithTimestamp] = getRuntimeContext
//      .getState(new ValueStateDescriptor[CountWithTimestamp]("myState", classOf[CountWithTimestamp]))
//
//    override def processElement(
//                                 value: (String, String),
//                                 ctx: KeyedProcessFunction[Tuple, (String, String), (String, Long)]#Context,
//                                 out: Collector[(String, Long)]): Unit = {
//
//      // initialize or retrieve/update the state
//      val current: CountWithTimestamp = state.value match {
//        case null =>
//          CountWithTimestamp(value._1, 1, ctx.timestamp)
//        case CountWithTimestamp(key, count, lastModified) =>
//          CountWithTimestamp(key, count + 1, ctx.timestamp)
//      }
//
//      // write the state back
//      state.update(current)
//
//      // schedule the next timer 60 seconds from the current event time
//      ctx.timerService.registerEventTimeTimer(current.lastModified + 60000)
//    }
//
//    override def onTimer(
//                          timestamp: Long,
//                          ctx: KeyedProcessFunction[Tuple, (String, String), (String, Long)]#OnTimerContext,
//                          out: Collector[(String, Long)]): Unit = {
//
//      state.value match {
//        case CountWithTimestamp(key, count, lastModified) if (timestamp == lastModified + 60000) =>
//          out.collect((key, count))
//        case _ =>
//      }
//    }
//  }
//
//}
