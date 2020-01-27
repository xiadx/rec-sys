package basic

object PatternMatchDemoI {

  def main(args: Array[String]): Unit = {
    def judgeGrade(grade: String): Unit = {
      grade match {
        case "A" => println("Excellent")
        case "B" => println("Good")
        case "C" => println("Just so so")
        case _ => println("You need work harder")
      }
    }
    judgeGrade("A")
    judgeGrade("D")
  }

}
