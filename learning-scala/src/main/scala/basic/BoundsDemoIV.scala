package basic

object BoundsDemoIV {

  def main(args: Array[String]): Unit = {
    class Calculator[T: Ordering](val number1: T, val number2: T) {
      def max(implicit order: Ordering[T]) = if (order.compare(number1, number2) > 0) number1 else number2
    }
    val c = new Calculator[Int](1, 2)
    println(c.max)
  }

}
