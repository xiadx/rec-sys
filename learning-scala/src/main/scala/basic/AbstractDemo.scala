package basic

object AbstractDemo {

  def main(args: Array[String]): Unit = {
    val s = new Student("leo")
    s.sayHello
  }

  abstract class Person(val name: String) {

    def sayHello: Unit

  }

  class Student(name: String) extends Person(name) {

    def sayHello: Unit = println("hello, " + name)

  }

}
