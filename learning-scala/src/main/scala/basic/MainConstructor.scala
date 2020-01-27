package basic

object MainConstructor {

  def main(args: Array[String]): Unit = {
    val s = new Student("leo", 30)
    val t = new Teacher
  }

  class Student(val name: String, val age: Int) {

    println("your name is " + name + ", your age is " + age)

  }

  class Teacher(val name: String = "jack", val age: Int = 35) {

    println("your name is " + name + ", your age is " + age)

  }

}
