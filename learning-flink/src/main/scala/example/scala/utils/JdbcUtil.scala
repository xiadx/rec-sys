package example.scala.utils

import java.sql._

object JdbcUtil {

//  val DRIVER = "com.mysql.jdbc.Driver"
//  val URL = "jdbc:mysql://192.168.3.88:3314/search_feature_config?useUnicode=true&characterEncoding=utf-8&useSSL=false"
//  val USER = "search_feature_config_w"
//  val PASSWORD = "z6miH8c5SiHPgC4A5mcB9tu"
//  val PROXY_HOST = "10.1.7.186"
//  val PROXY_PORT = "333"
//  val PROXY_TYPE = "4"
//  val PROXY_USER = "xiadinxin"
//  val PROXY_PASSWORD = ""

//  val info = new Properties()
//  info.put("proxy_type", PROXY_TYPE)
//  info.put("proxy_host", PROXY_HOST)
//  info.put("proxy_user", PROXY_USER)
//  info.put("proxy_password", PROXY_PASSWORD)
//  info.put("user", USER)
//  info.put("password", PASSWORD)

  val DRIVER = "com.mysql.jdbc.Driver"
  val URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false"
  val USER = "root"
  val PASSWORD = "root123456"

  private var conn: Connection = getConnection
  private var ps: PreparedStatement = _
  private var rs: ResultSet = _

  def getConnection: Connection = {
    try {
      Class.forName(DRIVER)
//      conn = DriverManager.getConnection(URL, info)
      conn = DriverManager.getConnection(URL, USER, PASSWORD)
    } catch {
      case e: ClassNotFoundException => e.printStackTrace()
      case e: SQLException => e.printStackTrace()
    }
    conn
  }

  def select(sql: String): ResultSet = {
    try {
      ps = conn.prepareStatement(sql)
      rs = ps.executeQuery()
    } catch {
      case e: SQLException => e.printStackTrace()
      case e: Exception => e.printStackTrace()
    }
    rs
  }

  def close(): Unit = {
    try {
      if (null != rs) {
        rs.close()
      }
    } catch {
      case e: SQLException => e.printStackTrace()
    }
    try {
      if (null != ps) {
        ps.close()
      }
    } catch {
      case e: SQLException => e.printStackTrace()
    }
    try {
      if (null != conn) {
        conn.close()
      }
    } catch {
      case e: SQLException => e.printStackTrace()
    }
  }

}
