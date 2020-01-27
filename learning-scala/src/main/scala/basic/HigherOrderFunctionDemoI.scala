package basic

object HigherOrderFunctionDemoI {

  def main(args: Array[String]): Unit = {
    val sayHelloFunc = (name: String) => println("Hello, " + name)
    def greeting(func: (String) => Unit, name: String) { func(name) }
    greeting(sayHelloFunc, "Leo")

    println(Array(1, 2, 3, 4, 5).map((num: Int) => num * num).mkString(","))

    def getGreetingFunc(msg: String) = (name: String) => println(msg + ", " + name)
    val greetingFunc = getGreetingFunc("hello")
    greetingFunc("leo")
  }

}
