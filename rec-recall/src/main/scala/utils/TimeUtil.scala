package utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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

  def getYesterdayStr(dt: String, pattern: String = "yyyyMMdd"): String = {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat(pattern)
    cal.setTime(sdf.parse(dt))
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

}
