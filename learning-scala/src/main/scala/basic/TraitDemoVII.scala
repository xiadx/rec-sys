package basic

object TraitDemoVII {

  def main(args: Array[String]): Unit = {
    println(classOf[Logger])
    println(classOf[MyLogger])
  }

  trait Logger {

    def log(msg: String)

  }

  trait MyLogger extends Logger {

    abstract override def log(msg: String) { super.log(msg) }

  }

}
