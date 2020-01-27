package basic

object EnumerationDemoI {

  def main(args: Array[String]): Unit = {
    println(Season.SPRING)
    println(Season.values)
    for (e <- Season.values) println(e)
  }

  object Season extends Enumeration {

    val SPRING, SUMMER, AUTUMN, WINTER = Value

  }

}
