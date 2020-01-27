package basic

object ArrayBufferDemo {

  def main(args: Array[String]): Unit = {
//    import scala.collection.mutable.ArrayBuffer
//    val b = ArrayBuffer[Int]()
//    b += 1
//    b += (2, 3, 4, 5)
//    b ++= Array(6, 7, 8, 9, 10)
//    println(b)
//    b.trimEnd(5)
//    println(b)
//    b.insert(5, 6)
//    println(b)
//    b.insert(6, 7, 8, 9, 10)
//    println(b)
//    b.remove(1)
//    println(b)
//    b.remove(1, 3)
//    println(b)
//    val a = b.toArray
//    println(a)
//    println(a.toBuffer)

//    import scala.collection.mutable.ArrayBuffer
//    val a = Array(1, 2, 3, 4, 5)
//    val b = for (e <- a) yield e * e
//    println(b.mkString(","))
//    val c = ArrayBuffer[Int]()
//    c += (1, 2, 3, 4, 5)
//    val d = for (e <- c) yield e * e
//    println(d.mkString(","))
//    val e = for (e <- c if e %2 == 0) yield e * e
//    println(a.filter(_ % 2 == 0).map(2 * _).mkString(","))
//    println(a.filter { _ % 2 == 0 } map { 2 * _ } mkString { "," })

//    import scala.collection.mutable.ArrayBuffer
//    val a = ArrayBuffer[Int]()
//    a += (1, 2, 3, 4, 5, -1, -3, -5, -9)
//    var foundFirstNegative = false
//    var arrayLength = a.length
//    var index = 0
//    while (index < arrayLength) {
//      if (a(index) >= 0) {
//        index += 1
//      } else {
//        if (!foundFirstNegative) { foundFirstNegative = true; index += 1 }
//        else { a.remove(index); arrayLength -= 1 }
//      }
//    }
//    println(a)

//    import scala.collection.mutable.ArrayBuffer
//    val a = ArrayBuffer[Int]()
//    a += (1, 2, 3, 4, 5, -1, -3, -5, -9)
//    var foundFirstNegative = false
//    val keepIndexes = for (i <- 0 until a.length if !foundFirstNegative || a(i) >= 0) yield {
//      if (a(i) < 0) foundFirstNegative = true
//      i
//    }
//    for (i <- 0 until keepIndexes.length) { a(i) = a(keepIndexes(i)) }
//    a.trimEnd(a.length - keepIndexes.length)
//    println(a)
  }

}
