package basic

object CovariantDemo {

  def main(args: Array[String]): Unit = {
    class Master
    class Professional extends Master
    class Card[+T](val name: String)
    def enterMeet(card: Card[Master]): Unit = {
      println("welcom to have this meeting!")
    }
    enterMeet(new Card[Master]("leo"))
    enterMeet(new Card[Professional]("jack"))
  }

}
