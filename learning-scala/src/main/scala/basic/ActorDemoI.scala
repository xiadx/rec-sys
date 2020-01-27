package basic

import scala.actors.Actor

object ActorDemoI {

  def main(args: Array[String]): Unit = {
    val helloActor = new HelloActor
    helloActor.start()
    helloActor ! "leo"
  }

  class HelloActor extends Actor {

    def act() {
      while (true) {
        receive {
          case name: String => println("Hello, " + name)
        }
      }
    }

  }

}
