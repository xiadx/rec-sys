package basic

object ExtendsDemo {

  def main(args: Array[String]): Unit = {
    val s = new Student
    println(s.getScore)
    println(s.getName)
  }

  class Person {

    private var name = "leo"

    def getName = name

  }

  class Student extends Person {

    private var score = "A"

    def getScore = score

  }

}
