package basic

object TupleDemo {

  def main(args: Array[String]): Unit = {
    val t = ("Leo", 30)
    println(t._1, t._2)
    val names = Array("Leo", "Jack", "Mike")
    val ages = Array(30, 24, 26)
    val nameAges = names.zip(ages)
    for ((name, age) <- nameAges) println(name + " -> " + age)
  }

}
