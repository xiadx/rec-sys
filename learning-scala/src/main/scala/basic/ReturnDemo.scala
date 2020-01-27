package basic

object ReturnDemo {

  def main(args: Array[String]): Unit = {
    def greeting(name: String) = {
      def sayHello(name: String): String = {
        return "Hello, " + name
      }
      sayHello(name)
    }
    println(greeting("Leo"))
  }

}
