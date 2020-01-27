package basic

object ArrayDemo {

  def main(args: Array[String]): Unit = {
    val a = new Array[Int](10)
    println(a(0))
    a(0) = 1
    println(a)
    val b = Array("hello", "world")
    b(0) = "hi"
    println(b)
    val c = Array("leo", 30)
    println(c)

    val d = Array(1, 2, 3, 4, 5)
    for (i <- 0 until d.length) {
      print(d(i) + " ")
    }
    println()
    for (i <- 0 until (d.length, 2)) {
      print(d(i) + " ")
    }
    println()
    for (i <- (0 until d.length).reverse) {
      print(d(i) + " ")
    }
    println()
    for (i <- d) {
      print(i + " ")
    }
    println()
    val e = Array(4, 2, 3, 1, 5)
    val s = e.sum
    val m = e.max
    println("sum -> " + s)
    println("max -> " + m)
    scala.util.Sorting.quickSort(e)
    println(e.mkString(","))
    println(e.mkString("<", ",", ">"))
    println(e.toString)
  }

}
