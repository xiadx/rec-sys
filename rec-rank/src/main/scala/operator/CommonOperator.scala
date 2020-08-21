package operator

object CommonOperator {

  def calculateCtr(click: Long, show: Long): Double = {
    val alpha = 0
    val beta = 1
    (click + alpha).toDouble / (show + alpha + beta)
  }

}
