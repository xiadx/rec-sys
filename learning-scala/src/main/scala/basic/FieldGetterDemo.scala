package basic

object FieldGetterDemo {

  def main(args: Array[String]): Unit = {
    val leo = new Student
    println(leo.name)
    leo.updateName("jack")
    println(leo.name)
    leo.updateName("leo1")
    println(leo.name)
  }

  class Student {

    private var myName = "leo"

    def updateName(newName: String): Unit = {
      if (newName == "leo1") myName = newName
      else println("not accept this new name!")
    }

    def name = "your name is " + myName

  }

}
