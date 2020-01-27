package basic

object ProtectedDemo {

  def main(args: Array[String]): Unit = {
    val s1 = new Student
    s1.sayHello
    val s2 = new Student
    s2.sayHello
    s1.makeFriends(s2)
  }

  class Person {

    protected var name: String = "leo"
    //  protected[this] var hobby: String = "game"
    protected var hobby: String = "game"

  }

  class Student extends Person {

    def sayHello = println("Hello, " + name)

    def makeFriends(s: Student): Unit = {
      println("my hobby is " + hobby + ", your hobby is " + s.hobby)
    }

  }

}
