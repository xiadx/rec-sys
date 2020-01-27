package basic

object TraitDemoVIII {

  def main(args: Array[String]): Unit = {
    val p1 = new Person("leo")
    val p2 = new Person("jack")
    println(p1.getClass)
    println(p2.getClass)
  }

  trait Valid {

    def getName: String

    def valid: Boolean = {
      getName == "leo"
    }

  }

  class Person(val name: String) extends Valid {

    println(valid)

    def getName = name

  }

}
