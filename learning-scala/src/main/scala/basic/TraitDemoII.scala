package basic

object TraitDemoII {

  def main(args: Array[String]): Unit = {
    val p1 = new Person("leo")
    val p2 = new Person("jack")
    p1.makeFriends(p2)
  }

  trait Logger {

    def log(message: String) = println(message)

  }

  class Person(val name: String) extends Logger {

    def makeFriends(p: Person): Unit = {
      println("Hi, I'm " + name + ", I'm glad to make friends with you, " + p.name)
      log("makeFriends method is invoked with parameter Person[name=" + p.name + "]")
    }

  }

}
