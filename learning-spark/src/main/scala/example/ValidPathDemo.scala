package example

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.util.control.Breaks

object ValidPathDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ValidPathDemo")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("ValidPathDemo")
      .getOrCreate()

    val dt = getYesterdayStr()
    val path = "/user/xiadinxin/rec-sys/rec-rank/feature/ufm/v1/"
    println(getValidPath(path, dt))
  }

  def exist(path: String): Boolean = {
    val fs = FileSystem.get(new Configuration())
    val p = new Path(path)
    fs.exists(p)
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

  def getValidPath(path: String, dt: String, count: Int = 30, pattern: String = "yyyyMMdd"): String = {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat(pattern)
    val endDate = dt
    val startDate = getFewDaysAgo(endDate, count, pattern)
    val endTime = sdf.parse(endDate)
    val startTime = sdf.parse(startDate)
    var currentTime = endTime
    var rst: String = null
    val loop = new Breaks
    loop.breakable {
      while (currentTime.after(startTime)) {
        val currentDate = sdf.format(currentTime)
        val currentPath = if (path.endsWith("/")) s"$path$currentDate" else s"$path/$currentDate"
        if (exist(currentPath)) {
          rst = currentPath
          loop.break()
        }
        cal.setTime(currentTime)
        cal.add(Calendar.DATE, -1)
        currentTime = cal.getTime
      }
    }
    rst
  }

}
