package basic

object GenericDemo {

  def main(args: Array[String]): Unit = {
    class Student[T](val localId: T) {
      def getSchoolId(hukouId: T) = "S-" + hukouId + "-" + localId
    }
    val leo = new Student[Int](111)
    println(leo.getSchoolId(100))

    def getCard[T](content: T) = {
      if (content.isInstanceOf[Int]) "card: 001, " + content
      else if (content.isInstanceOf[String]) "card: this is your card, " + content
      else "card: " + content
    }
    println(getCard[String]("hello world"))
  }

}
