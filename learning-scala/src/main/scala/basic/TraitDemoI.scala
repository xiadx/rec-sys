package basic

object TraitDemoI {

  def main(args: Array[String]): Unit = {
    val p1 = new Person("leo")
    val p2 = new Person("jack")
    p1.sayHello("jack")
    p2.sayHello("leo")
    p1.makeFriends(p2)
  }

  trait HelloTrait {

    def sayHello(name: String)

  }

  trait MakeFriendsTrait {

    def makeFriends(p: Person)

  }

  class Person(val name: String) extends HelloTrait with MakeFriendsTrait {

    def sayHello(otherName: String) = println("Hello, " + otherName + ", I'm " + name)

    def makeFriends(p: Person) = println("Hello " + p.name + ", I'm " + name + ", I want to make friends with you.")

  }

}
