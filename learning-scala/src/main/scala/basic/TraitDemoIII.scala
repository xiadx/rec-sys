package basic

object TraitDemoIII {

  def main(args: Array[String]): Unit = {
    val s = new Student("leo")
    s.sayHello
  }

  trait Person {

    val eyeNum: Int = 2

  }

  class Student(val name: String) extends Person {

    def sayHello = println("Hi, I'm " + name + ", I have " + eyeNum + " eyes.")

  }

}
