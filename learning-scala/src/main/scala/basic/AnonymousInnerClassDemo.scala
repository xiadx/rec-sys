package basic

object AnonymousInnerClassDemo {

  def main(args: Array[String]): Unit = {
    val p = new Person("leo") {
      override def sayHello = "Hi, I'm " + name
    }
    greeting(p)
  }

  def greeting(p: Person {def sayHello: String}): Unit = {
    println(p.sayHello)
  }

  class Person(protected val name: String) {

    def sayHello = "Hello, I'm " + name

  }

}
