package basic

object CurryingDemo {

  def main(args: Array[String]): Unit = {
    def sum1(a: Int, b: Int) = a + b
    println(sum1(1, 1))
    def sum2(a: Int) = (b: Int) => a + b
    println(sum2(1)(1))
    def sum3(a: Int)(b: Int) = a + b
    println(sum3(1)(1))
  }

}
