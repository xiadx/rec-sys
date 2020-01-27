package basic

object ClassDemo {

  def main(args: Array[String]): Unit = {
    val hw = new HelloWorld
    hw.sayHello()
    println(hw.getName)
  }

  class HelloWorld {

    private var name = "Leo"

    def sayHello() { println("Hello, " + name) }

    def getName = name

  }

}
