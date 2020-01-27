package basic

object HigherOrderFunctionDemoIII {

  def main(args: Array[String]): Unit = {
    println(Array(1, 2, 3, 4, 5).map(2 * _).mkString(","))
    (1 to 9).map("*" * _).foreach(println _)
    println((1 to 20).filter(_ % 2 == 0).mkString(","))
    println((1 to 5).reduceLeft(_ * _))
    println(Array(3, 2, 5, 4, 10, 1).sortWith(_ < _).mkString(","))
  }

}
