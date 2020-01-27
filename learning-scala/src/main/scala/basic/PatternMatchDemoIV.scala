package basic

object PatternMatchDemoIV {

  def main(args: Array[String]): Unit = {
//    def greeting(arr: Array[String]): Unit = {
//      arr match {
//        case Array("Leo") => println("Hi, Leo!")
//        case Array(girl1, girl2, girl3) => println("Hi, girls, nice to meet you. " + girl1 + " and " + girl2 + " and " + girl3)
//        case Array("Leo", _*) => println("Hi, Leo, please introduce your friends to me.")
//        case _ => println("hey who are you?")
//      }
//    }
//    greeting(Array("Leo"))
//    greeting(Array("Jen", "Marry", "Penny"))
//    greeting(Array("Leo", "Jack"))
//    greeting(Array("Jack"))

    def greeting(list: List[String]): Unit = {
      list match {
        case "Leo" :: Nil => println("Hi, Leo!")
        case girl1 :: girl2 :: girl3 :: Nil => println("Hi, girls, nice to meet you. " + girl1 + " and " + girl2 + " and " + girl3)
        case "Leo" :: tail => println("Hi, Leo, please introduce your friends to me.")
        case _ => println("hey, who are you!")
      }
    }
    greeting(List("Leo"))
    greeting(List("Jen", "Marry", "Penny"))
    greeting(List("Leo", "Jack"))
    greeting(List("Jack"))
  }

}
