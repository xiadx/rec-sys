package basic

object TraitDemoIX {

  def main(args: Array[String]): Unit = {
    val s = new Student
    println(s.getClass)
  }

  class Person { println("Person's constructor!") }

  trait Logger { println("Logger's constructor!") }

  trait MyLogger extends Logger { println("MyLogger's constructor!") }

  trait TimeLogger extends Logger { println("TimeLogger's constructor!") }

  class Student extends Person with MyLogger with TimeLogger {

    println("Student's constructor!")

  }

}
