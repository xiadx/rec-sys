package example.scala

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

import scala.util.Random

object ClickShowRealtimeFeatureDemoI {

  val USERS = List("U1", "U2", "U3", "U4", "U5", "U6", "U7", "U8", "U9", "U10")
  val ITEMS = List("I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9", "I10")

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val clickAndShowStream: DataStream[(String, String, Int, Long)] = env.addSource(
      new SourceFunction[(String, String, Int, Long)] {
        override def run(ctx: SourceContext[(String, String, Int, Long)]): Unit = {
          val startTime = System.currentTimeMillis()

          val steps = 2000
          var count = 0

          while (count <= steps) {
            Thread.sleep(10)

            val i = Random.nextInt(USERS.length)
            val uid = USERS(i)
            val j = Random.nextInt(ITEMS.length)
            val iid = ITEMS(j)

            ctx.collect((uid, iid, 1, System.currentTimeMillis()))
            ctx.collect((uid, iid, 0, System.currentTimeMillis()))

            var n = 0

            while (n <= 2) {
              Thread.sleep(10)

              val k = Random.nextInt(ITEMS.length)
              val id = ITEMS(k)

              ctx.collect((uid, id, 0, System.currentTimeMillis()))

              n += 1
            }

            count += 1
          }

          val endTime = System.currentTimeMillis()

          println(s"Click Stream Took ${endTime - startTime} msecs for ${steps} values")
        }

        override def cancel(): Unit = {}
      }).assignAscendingTimestamps(_._4)

    val itemRealtimeFeatureStream = clickAndShowStream
      .keyBy(_._2)
      .timeWindow(Time.milliseconds(10000), Time.milliseconds(100))
      .aggregate(
        new AggregateFunction[(String, String, Int, Long), (String, Long, Long), (String, Long, Long, Double)] {
          override def createAccumulator(): (String, Long, Long) = ("", 0, 0)

          override def add(value: (String, String, Int, Long), accumulator: (String, Long, Long)): (String, Long, Long) = {
            if (value._3 == 1) {
              (value._2, accumulator._2 + 1, accumulator._3)
            } else {
              (value._2, accumulator._2, accumulator._3 + 1)
            }
          }

          override def merge(a: (String, Long, Long), b: (String, Long, Long)): (String, Long, Long) = (a._1, a._2 + b._2, a._3 + b._3)

          override def getResult(accumulator: (String, Long, Long)): (String, Long, Long, Double) = (accumulator._1, accumulator._2, accumulator._3, accumulator._2.toDouble / (accumulator._3+1))
        }
      )

    itemRealtimeFeatureStream.addSink(new SinkFunction[(String, Long, Long, Double)] {
      override def invoke(in: (String, Long, Long, Double)): Unit = {
        println(s"iid->${in._1},ic->${in._2},is->${in._3},ir->${in._4}")
      }
    })

    env.execute("ClickShowRealtimeFeatureDemoI")
  }

}
