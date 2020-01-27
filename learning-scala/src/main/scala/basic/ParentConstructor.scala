package basic

object ParentConstructor {

  def main(args: Array[String]): Unit = {
    val s1 = new Student("leo")
    println(s1.name, s1.age, s1.score)
    val s2 = new Student(30)
    println(s2.name, s2.age, s2.score)
  }

  class Person(val name: String, val age: Int)

  class Student(name: String, age: Int, var score: Double) extends Person(name, age) {

    def this(name: String) {
      this(name, 0, 0)
    }

    def this(age: Int) {
      this("leo", age, 0)
    }

  }

}
