package utils

object MathUtil {

  def sigmoid(d: Double): Double = {
    1.0 / (1 + Math.exp(-d))
  }

  def softmax(a: Array[Double]): Array[Double] = {
    val b = a.map(x => Math.exp(x))
    val z = b.sum
    b.map(x => x / z)
  }

}
