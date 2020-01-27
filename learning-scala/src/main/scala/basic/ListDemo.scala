package basic

object ListDemo {

  def main(args: Array[String]): Unit = {
    def decorator(l: List[Int], prefix: String): Unit = {
      if (l != Nil) {
        print(prefix + l.head + " ")
        decorator(l.tail, prefix)
      }
    }
    val l = List(1, 2, 3, 4, 5)
    decorator(l, "#")
  }

}
