package basic

object BoundsDemoII {

  def main(args: Array[String]): Unit = {
    class Father(val name: String)
    class Child(name: String) extends Father(name)
    def getIDCard[R >: Child](person: R): Unit = {
      if (person.getClass == classOf[Child]) println("please tell us your parents' names.")
      else if (person.getClass == classOf[Father]) println("sign your name for your child's id card.")
      else println("sorry, you are not allowed to get id card.")
    }
    getIDCard[Child](new Child("leo"))
    getIDCard[Father](new Father("jack"))
  }

}
