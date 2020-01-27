package basic

object SortedMapDemo {

  def main(args: Array[String]): Unit = {
    val ages = scala.collection.immutable.SortedMap("Leo" -> 30, "Alice" -> 15, "Jen" -> 25)
    for ((k, v) <- ages) println(k, v)
  }

}
