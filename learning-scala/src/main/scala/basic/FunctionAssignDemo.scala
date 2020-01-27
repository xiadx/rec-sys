package basic

object FunctionAssignDemo {

  def main(args: Array[String]): Unit = {
    val sayHelloFunc = sayHello _
    sayHelloFunc("leo")
  }

  def sayHello(name: String) { println("Hello, " + name) }

}
