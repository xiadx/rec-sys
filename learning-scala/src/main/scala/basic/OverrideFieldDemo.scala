package basic

object OverrideFieldDemo {

  def main(args: Array[String]): Unit = {
    val s = new Student
    println(s.name)
    println(s.age)
  }

  class Person {

    val name: String = "person"

    def age: Int = 0

  }

  class Student extends Person {

    override val name: String = "leo"

    override def age: Int = 30

  }

}
