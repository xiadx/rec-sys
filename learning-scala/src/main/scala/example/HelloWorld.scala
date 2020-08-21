package example

object HelloWorld {

  def main(args: Array[String]): Unit = {
//    val s: String = null
//    println(s == null)
//    val a = "1"
//    println(s"${a}_$a")
//    println("1".toInt)
    val staPath = "/user/xiadx"
    val dt = "20200801"

    var path = "%s/%s".format(staPath, dt)
    println(path)

    print(s"$staPath/$dt")

  }

}
