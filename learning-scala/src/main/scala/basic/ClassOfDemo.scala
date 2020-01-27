package basic

object ClassOfDemo {

  def main(args: Array[String]): Unit = {
    val p: Person = new Student
    println(p.isInstanceOf[Person])
    println(p.getClass == classOf[Person])
    println(p.getClass == classOf[Student])
  }

  class Person

  class Student extends Person

}
