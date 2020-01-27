package basic

object MapDemo {

  def main(args: Array[String]): Unit = {
    val ages1 = Map("Leo" -> 30, "Jen" -> 25, "Jack" -> 23)
    //ages1("Leo") = 31
    println(ages1)
    val ages2 = scala.collection.mutable.Map("Leo" -> 30, "Jen" -> 25, "Jack" -> 23)
    ages2("Leo") = 31
    println(ages2)
    val ages3 = Map(("Leo", 30), ("Jen", 25), ("Jack", 23))
    println(ages3)
    val ages4 = new scala.collection.mutable.HashMap[String, Int]
    val leoAge1 = ages1("Leo")
    val leoAge2 = if (ages1.contains("leo")) ages1("leo") else 0
    val leoAge3 = ages1.getOrElse("leo", 0)
    println(leoAge1, leoAge2, leoAge3)
    ages4("Leo") = 31
    ages4 += ("Mike" -> 35, "Tom" -> 40)
    ages4 -= "Mike"
    val ages5 = ages1 + ("Mike" -> 36, "Tom" -> 40)
    val ages6 = ages5 - "Tom"
    println(ages4)
    println(ages5)
    println(ages6)
    for ((key, value) <- ages1) println(key + " " + value)
    for (key <- ages1.keySet) println(key)
    for (value <- ages1.values) println(value)
    val ages7 = for ((key, value) <- ages1) yield (value, key)
    for ((k, v) <- ages7) println(k, v)
  }

}
