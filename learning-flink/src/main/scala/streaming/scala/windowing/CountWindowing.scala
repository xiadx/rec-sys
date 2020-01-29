package streaming.scala.windowing

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

object CountWindowing {

  val WORDS = Array(
    "c", "c", "c", "c", "c",
    "java", "java", "java",
    "python", "python"
  )

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(1)

    val text = env.fromElements(WORDS: _*)

    val counts = text.map((_, 1))
      .keyBy(0)
      .countWindow(2, 1)
      .sum(1)

    counts.print()

    env.execute("CountWindowing")
  }

}
