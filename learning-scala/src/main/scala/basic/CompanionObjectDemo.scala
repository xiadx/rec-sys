package basic

object CompanionObjectDemo {

  def main(args: Array[String]): Unit = {
    val p = new Person("leo", 30)
    p.sayHello
  }

  object Person {

    private var eyeNum = 2

    println("this person object")

    def getEyeNum = eyeNum

  }

  class Person(val name: String, val age: Int) {

    def sayHello = println("Hi, " + name + ", I guess you are " +
      age + " years old!" + ", and usually you must have " + Person.eyeNum + " eyes.")

  }

}
