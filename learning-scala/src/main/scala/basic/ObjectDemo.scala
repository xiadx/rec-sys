package basic

object ObjectDemo {

  def main(args: Array[String]): Unit = {
    val p = Person
    println(p.getEyeNum)
    println(Person.getEyeNum)
  }

  object Person {

    private var eyeNum = 2

    println("this person object")

    def getEyeNum = eyeNum

  }

}
