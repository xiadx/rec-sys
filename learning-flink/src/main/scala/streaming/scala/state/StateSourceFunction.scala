//package streaming.scala.state
//
//import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
//
//object StateSourceFunction {
//
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setParallelism(1)
//
//    env.addSource(new CounterSource).print()
//
//    env.execute("StateSourceFunction")
//  }
//
//}
