package utils

object GLUtil {

  private val EARTH_RADIUS: Double = 6378.137

  private def rad(d: Double): Double = d * Math.PI / 180.0

  def getDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double = {
    if (Math.abs(lat1) > 90 || Math.abs(lng1) > 180 || Math.abs(lat2) > 90 || Math.abs(lng2) > 180) {
      return -1.0
    }
    val radLat1 = rad(lat1)
    val radLat2 = rad(lat2)
    val radLng1 = rad(lng1)
    val radLng2 = rad(lng2)
    val a = radLat1 - radLat2
    val b = radLng1 - radLng2
    2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))) * EARTH_RADIUS
  }

}
