package basic

import scala.actors.Actor

object ActorDemoIII {

  def main(args: Array[String]): Unit = {
    val leoTelephoneActor = new LeoTelephoneActor
    leoTelephoneActor.start()
    val jackTelephoneActor = new JackTelephoneActor(leoTelephoneActor)
    jackTelephoneActor.start()
  }

  case class Message(content: String, sender: Actor)

  class LeoTelephoneActor extends Actor {

    def act() {
      while (true) {
        receive {
          case Message(content, sender) => { println("leo telephone: " + content); sender ! "I'm leo, please call me after 10 minutes." }
        }
      }
    }

  }

  class JackTelephoneActor(val leoTelephoneActor: Actor) extends Actor {

    def act() {
      leoTelephoneActor ! Message("Hello, Leo, I'm Jack", this)
      receive {
        case response: String => println("jack telephone: " + response)
      }
    }

  }

}
