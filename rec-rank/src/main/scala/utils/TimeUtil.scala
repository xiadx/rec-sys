package utils

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import scala.collection.mutable

object TimeUtil {

  def getTodayStr(pattern: String = "yyyyMMdd"): String = {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat(pattern)
    val now = new Date()
    cal.setTime(now)
    sdf.format(cal.getTime)
  }

  def getYesterdayStr(pattern: String = "yyyyMMdd"): String = {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat(pattern)
    val now = new Date()
    cal.setTime(now)
    cal.add(Calendar.DATE, -1)
    sdf.format(cal.getTime)
  }

  def getFewDaysAgo(dt: String, count: Int, pattern: String = "yyyyMMdd"): String = {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat(pattern)
    cal.setTime(sdf.parse(dt))
    cal.add(Calendar.DATE, -count)
    sdf.format(cal.getTime)
  }

  def getDaysList(startDate: String, endDate: String, pattern: String = "yyyyMMdd"): Array[String] = {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat(pattern)
    var currentTime = sdf.parse(startDate)
    val endTime = sdf.parse(endDate)
    val daysList = mutable.ArrayBuffer[String]()
    while (currentTime.before(endTime)) {
      daysList += sdf.format(currentTime)
      cal.setTime(currentTime)
      cal.add(Calendar.DATE, 1)
      currentTime = cal.getTime
    }
    daysList += endDate
    daysList.toArray
  }

}
