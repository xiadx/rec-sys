package basic

object InstanceOfDemo {

  def main(args: Array[String]): Unit = {
    val p: Person = new Student
    var s: Student = null
    println(s)
    if (p.isInstanceOf[Student]) s = p.asInstanceOf[Student]
    println(s)
  }

  class Person

  class Student extends Person

}
