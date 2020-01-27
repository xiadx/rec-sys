package basic

object CustomGetterSetterDemo {

  def main(args: Array[String]): Unit = {
    val leo = new Student
    println(leo.name)
    leo.name = "leo1"
    println(leo.name)
  }

  class Student {

    private var myName = "leo"

    def name = "your name is " + myName

    def name_=(newValue: String): Unit = {
      println("you cannot edit your name!")
    }

  }

}
