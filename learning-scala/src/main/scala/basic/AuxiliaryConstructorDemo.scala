package basic

object AuxiliaryConstructorDemo {

  def main(args: Array[String]): Unit = {
    val s1 = new Student
    println(s1.getName)
    println(s1.getAge)
    val s2 = new Student("leo")
    println(s2.getName)
    println(s2.getAge)
    val s3 = new Student("leo", 30)
    println(s3.getName)
    println(s3.getAge)
  }

  class Student {

    private var name = ""
    private var age = 0

    def this(name: String) {
      this()
      this.name = name
    }

    def this(name: String, age: Int) {
      this(name)
      this.age = age
    }

    def getName = name

    def setName(newName: String): Unit = {
      this.name = newName
    }

    def getAge = age

    def setAge(newAge: Int): Unit = {
      this.age = age
    }

  }

}
