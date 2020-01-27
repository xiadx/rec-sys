package basic

object ImplicitConversionDemoII {

  def main(args: Array[String]): Unit = {
    class Man(val name: String)
    class Superman(val name: String) {
      def emitLaser = println("emit a laster!")
    }
    implicit def man2supername(man: Man): Superman = new Superman(man.name)
    val leo = new Man("leo")
    leo.emitLaser
  }

}
