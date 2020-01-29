package streaming.scala.windowing

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.{AssignerWithPeriodicWatermarks, AssignerWithPunctuatedWatermarks}
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time

object TimeAssigner {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val input = env.fromCollection(List(("a", 1L, 1), ("b", 4L, 2), ("b", 3L, 1)))

//    val withTimestampsAndWatermarks_01 = input.assignAscendingTimestamps(t => t._2)
//
//    val withTimestampsAndWatermarks_02 = input.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[(String, Long, Int)](Time.seconds(10)) {
//      override def extractTimestamp(t: (String, Long, Int)): Long = t._2
//    })

    val withTimestampsAndWatermarks_03 = input.assignTimestampsAndWatermarks(new PeriodicAssigner)

//    val result_01 = withTimestampsAndWatermarks_01.keyBy(0).timeWindow(Time.seconds(10)).sum("_2")
//
//    result_01.print()
//
//    val result_02 = withTimestampsAndWatermarks_02.keyBy(0).timeWindow(Time.seconds(10)).sum("_2")
//
//    result_02.print()

    val result_03 = withTimestampsAndWatermarks_03.keyBy(0).timeWindow(Time.seconds(10)).sum("_2")

    result_03.print()

    env.execute("TimeAssigner")

  }

  class PeriodicAssigner extends AssignerWithPeriodicWatermarks[(String, Long, Int)] {
    val maxOutOfOrderness = 1000L
    var currentMaxTimestamp: Long = _

    override def extractTimestamp(event: (String, Long, Int), previousEventTimestamp: Long): Long = {
      val currentTimestamp = event._2
      currentMaxTimestamp = Math.max(currentTimestamp, currentMaxTimestamp)
      currentTimestamp
    }

    override def getCurrentWatermark: Watermark = {
      new Watermark(currentMaxTimestamp - maxOutOfOrderness)
    }
  }

  class TimeLagWatermarkGenerator extends AssignerWithPeriodicWatermarks[(String, Long, Int)] {
    val maxTimeLag = 5000L

    override def extractTimestamp(element: (String, Long, Int), previousElementTimestamp: Long): Long = {
      element._2
    }

    override def getCurrentWatermark(): Watermark = {
      new Watermark(System.currentTimeMillis() - maxTimeLag)
    }
  }

  class PunctuatedAssigner extends AssignerWithPunctuatedWatermarks[(String, Long, Int)] {
    override def extractTimestamp(element: (String, Long, Int), previousElementTimestamp: Long): Long = {
      element._2
    }

    override def checkAndGetNextWatermark(lastElement: (String, Long, Int), extractedTimestamp: Long): Watermark = {
      if (lastElement._3 == 0) new Watermark(extractedTimestamp) else null
    }
  }

}
