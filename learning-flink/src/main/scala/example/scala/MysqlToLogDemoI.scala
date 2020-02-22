package example.scala

import java.sql.ResultSet

import example.scala.utils.JdbcUtil
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

import scala.collection.mutable

object MysqlToLogDemoI {

  val USER_MAP = getUserAsMap()

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val fld = "MysqlToLogDemoI"

    val dataStream = env.fromElements(1, 2, 3)

    dataStream.addSink(new SinkFunction[Int] {
      override def invoke(in: Int): Unit = {
        println(fld + " --> " + in.toString)
        println(USER_MAP.keys.mkString(","))
      }
    })

    println(fld)

    env.execute("MysqlToLogDemoI")
  }

  def getUserAsMap(): mutable.Map[String, User] = {
    val sql =
      """
        |select
        | id
        | ,name
        | ,age
        |from
        | test.user
      """.stripMargin
    val userMap = new mutable.HashMap[String, User]
    var rs: ResultSet = null
    try {
      rs = JdbcUtil.select(sql)
      while (rs.next()) {
        val id = rs.getInt("id")
        val name = rs.getString("name")
        val age = rs.getInt("age")
        val user = User(id, name, age)
        userMap.put(user.name, user)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      JdbcUtil.close()
    }
    userMap
  }

  case class User(id: Int,
                  name: String,
                  age: Int)

}
