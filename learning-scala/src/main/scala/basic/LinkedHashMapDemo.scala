package basic

import scala.collection.mutable

object LinkedHashMapDemo {

  def main(args: Array[String]): Unit = {
    val ages = new mutable.LinkedHashMap[String, Int]
    ages("Leo") = 30
    ages("Alice") = 15
    ages("Jen") = 25
    for ((k, v) <- ages) println(k, v)
  }

}
