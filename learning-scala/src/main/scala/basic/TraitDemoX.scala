package basic

object TraitDemoX {

  def main(args: Array[String]): Unit = {
    class Person1
    val p1 = new {
      val msg: String = "init"
    } with Person1 with SayHello
    class Person2 extends {
      val msg: String = "init"
    } with SayHello
    val p2 = new Person2
    println(p1.getClass)
    println(p2.getClass)
  }

  trait SayHello {

    val msg: String
    println(msg.toString)

  }

}
