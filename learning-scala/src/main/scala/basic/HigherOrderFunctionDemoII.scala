package basic

object HigherOrderFunctionDemoII {

  def main(args: Array[String]): Unit = {
    def greeting(func: (String) => Unit, name: String) { func(name) }
    greeting((name: String) => println("Hello, " + name), "Leo")
    greeting((name) => println("Hello, " + name), "Leo")
    greeting(name => println("Hello, " + name), "Leo")

    def triple(func: (Int) => Int) = { func(3) }
    println(triple(3 * _))
  }

}
