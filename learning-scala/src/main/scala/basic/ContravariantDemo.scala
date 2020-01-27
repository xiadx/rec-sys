package basic

object ContravariantDemo {

  def main(args: Array[String]): Unit = {
    class Master
    class Professional extends Master
    class Card[-T](val name: String)
    def enterMeet(card: Card[Professional]): Unit = {
      println("welcom to have this meeting!")
    }
    enterMeet(new Card[Professional]("leo"))
    enterMeet(new Card[Master]("jack"))
  }

}
