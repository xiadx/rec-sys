package basic

object AbstractFieldDemo {

  def main(args: Array[String]): Unit = {
    val s = new Student
    println(s.name)
  }

  abstract class Person {

    val name: String

  }

  class Student extends Person {

    val name: String = "leo"

  }

}
