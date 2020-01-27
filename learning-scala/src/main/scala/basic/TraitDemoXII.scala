package basic

object TraitDemoXII {

  def main(args: Array[String]): Unit = {
    val p = new Person("leo")
    p.sayHello
  }

  class MyUtil {

    def printMessage(msg: String) = println(msg)

  }

  trait Logger extends MyUtil {

    def log(msg: String) = printMessage("log: " + msg)

  }

  class Person(val name: String) extends Logger {

    def sayHello: Unit = {
      log("Hi, I'm " + name)
      printMessage("Hi, I'm " + name)
    }

  }

}
