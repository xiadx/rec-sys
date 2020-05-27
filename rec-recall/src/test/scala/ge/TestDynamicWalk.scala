package ge

import org.junit.Test
import utils.ConfigUtil

import scala.collection.mutable

class TestDynamicWalk {

  val transferProbA: Double = ConfigUtil.dynamicwalkConf.getDouble("transfer-prob.a")
  val transferProbB: Double = ConfigUtil.dynamicwalkConf.getDouble("transfer-prob.b")
  val transferProbC: Double = ConfigUtil.dynamicwalkConf.getDouble("transfer-prob.c")
  val timeSensitivity: Double = ConfigUtil.dynamicwalkConf.getDouble("time-sensitivity")

  def transferProb(freq: Long, direction: Int): Double = {
    direction * Math.pow(freq, -transferProbB) / transferProbA + transferProbC
  }

  def initTransferMatrix(matrix: Array[Array[Double]], index2Node: mutable.HashMap[Int, EntityNode]): Unit = {
    val capacity = index2Node.size
    for (i <- 0 until capacity) {
      matrix(i) = new Array[Double](capacity)
      for (j <- 0 until capacity) {
        if ((i == 0 && j == 0) || i == j) matrix(i)(j) = 0
        else if (i == capacity - 1 && j == 0) matrix(i)(j) = 1.0
        else if (i == 0) matrix(i)(j) = 1.0 / (capacity - 1)
        else if (j == 0) matrix(i)(j) = transferProb(index2Node(i).getFreq, -1)
        else if (i != j - 1) matrix(i)(j) = 0
        else matrix(i)(j) = transferProb(index2Node(i).getFreq, 1)
      }
    }
  }

  def getTimeScore(pre: Long, next: Long): Double = {
    val deltaMinute = Math.abs(pre - next) / 60.0
    val sensitivity = timeSensitivity
    -1.0 * Math.log(0.1 + sensitivity * deltaMinute)
  }

  @Test
  def testTransferProb(): Unit = {
    println(transferProb(1, -1))
  }

  @Test
  def testInitTransferMatrix(): Unit = {
    val e1 = new EntityNode("poi", "p1", System.currentTimeMillis())
    val e2 = new EntityNode("weng", "w2", System.currentTimeMillis())
    val e3 = new EntityNode("sale", "s3", System.currentTimeMillis())
    val index2Node = new mutable.HashMap[Int, EntityNode]
    index2Node.put(1, e1)
    index2Node.put(2, e2)
    index2Node.put(3, e3)
    val matrix = new Array[Array[Double]](index2Node.size)
    initTransferMatrix(matrix, index2Node)
    for (r <- matrix) {
      for (c <- r) {
        print(c + " ")
      }
      println()
    }
  }

  @Test
  def testGetTimeScore(): Unit = {
    val pre = System.currentTimeMillis() / 1000
    val next = System.currentTimeMillis() / 1000 + 20 * 60
    println(getTimeScore(pre, next))
  }

  @Test
  def testDiscreteDistributionSampling(): Unit = {
    val probs: Array[Double] = Array(0, 0.5, 0.5)
    var accumulate = .0
    val rand = new java.util.Random()
    val r = rand.nextDouble()
    var next = 0
    while (next < probs.length && r >= accumulate) {
      accumulate += probs(next)
      next += 1
    }
    println(r)
    println(next - 1)
  }

  @Test
  def testArraySum(): Unit = {
    println(Array(1, 2, 3).sum)
  }

}
