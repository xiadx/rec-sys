package basic

object FunctionalProgramDemoI {

  def main(args: Array[String]): Unit = {
    val line1 = scala.io.Source.fromFile("README.md").mkString
    val line2 = scala.io.Source.fromFile("README.md").mkString
    val lines = List(line1, line2)
    val count = lines.flatMap(_.split("\n")).flatMap(_.split(" ")).map((_, 1)).map(_._2).reduceLeft(_ + _)
    println(count)
  }

}
