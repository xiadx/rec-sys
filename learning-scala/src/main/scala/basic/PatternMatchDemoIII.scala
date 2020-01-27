package basic

object PatternMatchDemoIII {

  def main(args: Array[String]): Unit = {
    import java.io._
    def processException(e: Exception): Unit = {
      e match {
        case e1: IllegalArgumentException => println("you have illegal arguments! exception is: " + e1)
        case e2: FileNotFoundException => println("cannot find the file you need read or write! exceptiotn is: " + e2)
        case e3: IOException => println("you got an error while you were doing IO operation! exception is: " + e3)
        case _: Exception => println("cannot know which exception you have!")
      }
    }
    processException(new IllegalArgumentException("illegal argument!"))
    processException(new IOException("io exception!"))
  }

}
