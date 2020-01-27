package basic

object GetterSetterDemo {

  def main(args: Array[String]): Unit = {
    val leo = new Student
    println(leo.name)
    leo.name = "leo1"
    println(leo.name)
  }

  class Student {

    var name = "Leo"

  }

}
