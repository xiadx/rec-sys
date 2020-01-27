package basic

object ApplyDemo {

  def main(args: Array[String]): Unit = {
    val p = Person("leo")
    println(p.name)
  }

  class Person(val name: String)

  object Person {

    def apply(name: String) = new Person(name)

  }

}
