package basic

object PrivateThisDemo {

  def main(args: Array[String]): Unit = {
    val leo = new Student
    leo.age = 30
    val jack = new Student
    jack.age = 18
    println(leo.older(jack))
  }

  class Student {

    private var myAge = 0
//      private[this] var myAge = 0

    def age_=(newValue: Int): Unit = {
      if (newValue > 0) myAge = newValue
      else print("illegal age!")
    }

    def age = myAge

    def older(s: Student) = {
      myAge > s.myAge
    }

  }

}
