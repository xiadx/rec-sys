package basic

object SetDemo {

  def main(args: Array[String]): Unit = {
    val s1 = new scala.collection.mutable.HashSet[Int]()
    s1 += 1; s1 += 2; s1 += 5
    val s2 = new scala.collection.mutable.LinkedHashSet[Int]()
    s2 += 1; s2 += 2; s2 += 5
    val s3 = scala.collection.mutable.SortedSet[String]("banana", "apple", "candy")
    println(s1)
    println(s2)
    println(s3)
  }

}
