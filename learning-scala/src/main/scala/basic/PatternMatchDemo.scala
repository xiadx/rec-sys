package basic

object PatternMatchDemo {

  def main(args: Array[String]): Unit = {
    val p: Person = new Student
    p match {
      case per: Person => println("it's Person's object")
      case _ => println("unknown type")
    }
  }

  class Person

  class Student extends Person

}
