package basic

object ClosureDemo {

  def main(args: Array[String]): Unit = {
    def getGreetingFunc(msg: String) = (name: String) => println(msg + ", " + name)
    val greetingFuncHello = getGreetingFunc("hello")
    val greetingFuncHi = getGreetingFunc("hi")
    greetingFuncHello("leo")
    greetingFuncHi("leo")
  }

}
