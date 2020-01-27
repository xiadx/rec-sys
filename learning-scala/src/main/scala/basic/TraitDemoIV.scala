package basic

object TraitDemoIV {

  def main(args: Array[String]): Unit = {
    val p1 = new Person("leo")
    val p2 = new Person("jack")
    p1.makeFriends(p2)
  }

  trait SayHello {

    val msg: String

    def sayHello(name: String) = println(msg + ", " + name)

  }

  class Person(val name: String) extends SayHello {

    val msg: String = "hello"

    def makeFriends(p: Person): Unit = {
      sayHello(p.name)
      println("I'm " + name + ", I want to make friends with you!")
    }

  }

}
