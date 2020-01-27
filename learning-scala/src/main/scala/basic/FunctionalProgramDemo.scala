package basic

object FunctionalProgramDemo {

  def main(args: Array[String]): Unit = {
    val l1 = List("Leo", "Jen", "Peter", "Jack").map("name is " + _)
    val l2 = List("Hello World", "You Me").flatMap(_.split(" "))
    val l3 = List("I", "have", "a", "beautiful", "house")
    val l4 =  List("Leo", "Jen", "Peter", "Jack").zip(List(100, 90, 75, 83))
    println(l1)
    println(l2)
    println(l3)
    println(l4)
  }

}
