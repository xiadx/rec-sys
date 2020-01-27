package basic

import scala.collection.mutable.ArrayBuffer

object InnerClassDemo {

  def main(args: Array[String]): Unit = {
    val c1 = new Class
    val s1 = c1.getStudents("leo")
    c1.students += s1
    val c2 = new Class
    val s2 = c2.getStudents("leo")
//    c1.students += s2
    c2.students += s2
    println(c1.students)
    println(c2.students)
  }

  class Class {

    class Student(val name: String) {}

    val students = new ArrayBuffer[Student]

    def getStudents(name: String) = {
      new Student(name)
    }

  }

}
