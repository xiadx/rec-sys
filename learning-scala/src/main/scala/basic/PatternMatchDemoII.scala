package basic

object PatternMatchDemoII {

  def main(args: Array[String]): Unit = {
    def judgeGrade(name: String, grade: String): Unit = {
      grade match {
        case "A" => println(name + ", you are excellent")
        case "B" => println(name + ", you are good")
        case "C" => println(name + ", your are just so so")
        case _ if name == "leo" => println(name + ", you are a good boy, come on")
        case _ => println(name + ", you need to work harder")
      }
    }
    judgeGrade("jack", "A")
    judgeGrade("leo", "D")
    judgeGrade("jen", "D")
  }

}
