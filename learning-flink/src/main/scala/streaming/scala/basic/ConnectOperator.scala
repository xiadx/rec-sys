package streaming.scala.basic

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.functions.co.{CoFlatMapFunction, CoMapFunction}
import org.apache.flink.streaming.api.scala.{BroadcastConnectedStream, ConnectedStreams, DataStream, StreamExecutionEnvironment, _}
import org.apache.flink.util.Collector

object ConnectOperator {

  def main(args: Array[String]): Unit = {
    // Checking input parameters
    val params = ParameterTool.fromArgs(args)

    // Set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    env.setParallelism(1)

    val dataStream_01: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))

    val dataStream_02: DataStream[Int] = env.fromElements(1, 2, 4, 5, 6)

    val connectedStream: ConnectedStreams[(String, Int), Int] = dataStream_01.connect(dataStream_02)

    val resultStream_01 = connectedStream.map(new CoMapFunction[(String, Int), Int, (Int, String)] {
      override def map1(in1: (String, Int)): (Int, String) = {
        (in1._2, in1._1)
      }

      override def map2(in2: Int): (Int, String) = {
        (in2, "default")
      }
    })

    resultStream_01.print()

//    val resultStream_02 = connectedStream.flatMap(new CoFlatMapFunction[(String, Int), Int, (String, Int, Int)] {
//
//      var number = 0
//
//      override def flatMap1(in1: (String, Int), collector: Collector[(String, Int, Int)]): Unit = {
//        collector.collect((in1._1, in1._2, number))
//      }
//
//      override def flatMap2(in2: Int, collector: Collector[(String, Int, Int)]): Unit = {
//        number = in2
//      }
//    })
//
//    resultStream_02.print()

//    val keyedConnect: ConnectedStreams[(String, Int), Int] = dataStream_01.connect(dataStream_02).keyBy(1, 0)
//
//    val broadcastConnect: BroadcastConnectedStream[(String, Int), Int] = dataStream_01.connect(dataStream_02.broadcast())

    env.execute("ConnectOperator")
  }

}
