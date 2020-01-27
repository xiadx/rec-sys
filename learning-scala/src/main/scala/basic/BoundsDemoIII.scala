package basic

object BoundsDemoIII {

  def main(args: Array[String]): Unit = {
    class Person(val name: String) {
      def sayHello = println("Hello, I'm " + name)
      def makeFriends(p: Person): Unit = {
        sayHello
        p.sayHello
      }
    }
    class Student(name: String) extends Person(name)
    class Dog(val name: String) { def sayHello = println("Wang Wang, I'm " + name) }
    implicit def dog2person(dog: Object): Person = if (dog.isInstanceOf[Dog]) { val _dog = dog.asInstanceOf[Dog]; new Person(_dog.name ) } else null
    class Party[T <% Person](p1: T, p2: T) {
      def play = p1.makeFriends(p2)
    }
    val s = new Student("leo")
    val d = new Dog("jack")
    val p = new Party[Person](s, d)
    p.play
  }

}
