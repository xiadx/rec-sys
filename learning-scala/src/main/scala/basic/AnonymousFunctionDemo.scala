package basic

object AnonymousFunctionDemo {

  def main(args: Array[String]): Unit = {
    val sayHelloFunc = (name: String) => println("Hello, " + name)
    sayHelloFunc("Leo")
  }

}
