package streaming.scala.state

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}

object MapStateFunction {

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

    val counts: DataStream[(Int, Long)] = inputStream
      .keyBy(_._1)
      .mapWithState(
        (in: (Int, Long), count: Option[Long]) =>
          count match {
            case Some(c) => ((in._1, c), Some(c + in._2))
            case None => ((in._1, 0), Some(in._2))
          }
      )

    counts.print()

    env.execute("MapStateFunction")
  }

}
