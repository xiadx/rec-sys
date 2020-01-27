package basic

object BoundsDemoI {

  def main(args: Array[String]): Unit = {
    class Person(val name: String) {
      def sayHello = println("Hello, I'm " + name)
      def makeFriends(p: Person): Unit = {
        sayHello
        p.sayHello
      }
    }
    class Student(name: String) extends Person(name)
    class Party[T <: Person](p1: T, p2: T) {
      def play = p1.makeFriends(p2)
    }
    val s1 = new Student("leo")
    val s2 = new Student("jack")
    val p = new Party[Student](s1, s2)
    p.play
  }

}
