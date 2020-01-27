package basic

import scala.beans.BeanProperty

object JavaGetterSetterDemo {

  def main(args: Array[String]): Unit = {
    val s = new Student
    s.setName("leo")
    println(s.getName)
    val t = new Teacher("jack")
    println(t.getName)
  }

  class Student {

    @BeanProperty var name: String = _

  }

  class Teacher(@BeanProperty var name: String)

}
