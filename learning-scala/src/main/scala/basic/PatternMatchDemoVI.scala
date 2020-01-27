package basic

object PatternMatchDemoVI {

  def main(args: Array[String]): Unit = {
    val grades = Map("Leo" -> "A", "Jack" -> "B", "Jen" -> "C")
    def getGrade(name: String): Unit = {
      val grade = grades.get(name)
      grade match {
        case Some(grade) => println("your grade is " + grade)
        case None => println("sorry, your grade information is not in the system")
      }
    }
    getGrade("Leo")
    getGrade("Marry")
  }

}
