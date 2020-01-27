package basic

object ImplicitParameterDemo {

  def main(args: Array[String]): Unit = {
    class SignPen {
      def write(content: String) = println(content)
    }
    implicit val signPen = new SignPen
    def signForExam(name: String)(implicit signPen: SignPen): Unit = {
      signPen.write(name + " come to exam in time.")
    }
    signForExam("leo")
  }

}
