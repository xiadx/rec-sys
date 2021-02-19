package example

import scala.collection.mutable

object HelloWorld {

  def main(args: Array[String]): Unit = {
//    val s: String = null
//    println(s == null)
//    val a = "1"
//    println(s"${a}_$a")
//    println("1".toInt)
//    val staPath = "/user/xiadx"
//    val dt = "20200801"
//
//    var path = "%s/%s".format(staPath, dt)
//    println(path)
//
//    print(s"$staPath/$dt")
//
//    println("abc".split(",").mkString(";"))

    val s = "农历九月十九\u0001观音菩萨出家日\u0001,祈福求平安"

    val a: Array[String] = Array[String]("a", "b", "c")

    var b: Array[String] = _

    b = scala.util.Random.shuffle(a.toList).toArray

  }

}
