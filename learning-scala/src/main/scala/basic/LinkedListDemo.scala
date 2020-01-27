package basic

object LinkedListDemo {

  def main(args: Array[String]): Unit = {
//    val l = scala.collection.mutable.LinkedList(1, 2, 3, 4, 5)
//    var current = l
//    while (current != Nil) {
//      current.elem = current.elem * 2
//      current = current.next
//    }
//    println(l.mkString(","))

    val l = scala.collection.mutable.LinkedList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var current = l
    var first = true
    while (current != Nil && current.next != Nil) {
      if (first) { current.elem = current.elem * 2; first = false }
      current = current.next.next
      if (current != Nil) current.elem = current.elem * 2
    }
    println(l.mkString(","))
  }

}
