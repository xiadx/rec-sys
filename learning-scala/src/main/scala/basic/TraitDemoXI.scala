package basic

object TraitDemoXI {

  def main(args: Array[String]): Unit = {
    val p = new Person
    println(p.getClass)
  }

  trait SayHello {

    lazy val msg: String = null
    println(msg.toString)

  }

  class Person extends SayHello {

    override lazy val msg: String = "init"

  }

}
