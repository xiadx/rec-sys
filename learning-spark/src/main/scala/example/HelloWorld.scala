package example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable

object HelloWorld {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("HelloWorld")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .config(conf)
      .master("local")
      .appName("HelloWorld")
      .getOrCreate()

    import spark.implicits._

//    val s = "[{\"n\":1,\"c\":2},{\"n\":3,\"c\":4}]"
//    val ml = mapper.readValue(s, classOf[Array[Map[String, Any]]])
//    for (m <- ml) {
//      print(m)
//    }

//    var df = Seq(
//      ("a", "20200701", "[{\"n\":1,\"c\":2},{\"n\":3,\"c\":4}]")
//    ).toDF("open_udid", "dt", "profile")
//
//    df.printSchema()
//
//    df = df.select("open_udid", "dt", "profile").rdd.flatMap { r =>
//      val ml = mapper.readValue(r.getAs[String]("profile"), classOf[Array[Map[String, Any]]])
//      ml.map { m =>
//        (r.getAs[String]("open_udid"), r.getAs[String]("dt"), m.getOrElse("n", -1).toString.toInt, m.getOrElse("c", -1).toString.toInt)
//      }
//    }.toDF("open_udid", "dt", "n", "c")
//
//    df.show(false)

//    val df = Seq(
//      ("a", "b"),
//      ("c", "d"),
//      ("e", "f")
//    ).toDF("key", "value")
//
//    val rdd = df.repartition(2).rdd.mapPartitions { rows =>
//      rows.map { row =>
//        val key = row.getAs[String]("key")
//        val value = row.getAs[String]("value")
//        (key, value)
//      }
//    }
//
//    println(rdd.take(1)(0)._1)
//    println(rdd.take(1)(0)._2)

//    val allTag = Array(2001, 2002, 2003, 2004)
//
//    var profiledf = Seq(
//      ("a", 2001, 0.3),
//      ("a", 2003, 0.4),
//      ("a", 2004, 0.2),
//      ("a", 2002, 0.0),
//      ("b", 2003, 0.3),
//      ("b", 2001, 0.0),
//      ("b", 2002, 0.0),
//      ("b", 2004, 0.0),
//      ("c", 2001, 1.0),
//      ("c", 2002, 1.0),
//      ("c", 2003, 1.0),
//      ("c", 2004, 1.0)
//
//    ).toDF("open_udid", "tag_id", "profile")
//
//    var orderdf = Seq(
//      ("a", 2001, 3),
//      ("a", 2002, 4),
//      ("b", 2003, 3),
//      ("b", 2004, 1),
//      ("a", 2003, 0),
//      ("a", 2004, 0),
//      ("b", 2001, 0),
//      ("b", 2002, 0)
//    ).toDF("open_udid", "tag_id", "order")
//
//
//    profiledf.join(orderdf, Seq("open_udid", "tag_id"), "full").show(false)


//    profiledf.show()
//    orderdf.show()


//   profiledf.rdd.map { r=>
//     (r(0), r)
//   }.groupByKey().map { x =>
//     (x._1, x._2.map(i => i.getAs[String]("tag_id")).toArray)
//   }.toDF("a", "b")

//    val df = profiledf.rdd.map { r =>
//      (r(0), r)
//    }.groupByKey().map { x=>
//      (x._1, x._2.map(i => i.get(1).toString).toArray.mkString(","))
//    }.toDF("a", "b")
//
//    df.show()

//    val triggerSim = whiteListItemSimilarity.rdd.map { r =>
//      val triggerId = r(0).toString
//      val simId = r(1).toString
//      val score = r(2).toString.toDouble
//      val simItemId = simId.split("_")(0)
//      val simItemType = simId.split("_")(1)
//      val key = prefix.format(simItemType) + triggerId
//      (key, simItemId, simItemType, score)
//    }.toDF("key", "simItemId", "simItemType", "score").rdd.groupBy(r => r(0)).map {
//      case (triggerId, simItemIdList) =>
//        val simArray = simItemIdList.toArray.sortBy(r => r(3).toString.toDouble).reverse.map { r =>
//          r(1).toString + "_" + r(2).toString + "_" + r(3).toString.toDouble.formatted(decimal)
//        }
//        if (truncate == -1 || simArray.length <= truncate) (triggerId.toString, simArray.mkString(";"))
//        else (triggerId.toString, simArray.slice(0, truncate).mkString(";"))
//    }.toDF("trigger", "sim")

//    val profile = Seq(
//      ("a", 2001, 0.3),
//      ("a", 2003, 0.4),
//      ("a", 2004, 0.2),
//      ("b", 2001, 0.0),
//      ("c", 2003, 1.0),
//      ("c", 2004, 1.0)
//    ).toDF("open_udid", "tag_id", "profile")
//
//    val order = Seq(
//      ("a", 2001, 3),
//      ("a", 2002, 4),
//      ("b", 2003, 3),
//      ("b", 2004, 1)
//    ).toDF("open_udid", "tag_id", "order")
//
//    val eProfile = profile.rdd.groupBy(r => r.getAs[String]("open_udid")).flatMap {
//      case (open_udid, profiledfList) =>
//        val m = new mutable.HashMap[Int, Double]
//        profiledfList.toArray.foreach { r=>
//          m.put(r.getAs[Int]("tag_id"), r.getAs[Double]("profile"))
//        }
//        val allTag = Array(2001, 2002, 2003, 2004)
//        val aList = allTag.map { a=>
//          (open_udid, a, m.getOrElse(a, 0.0))
//        }
//        aList
//    }.toDF("open_udid", "tag_id", "profile")
//
//    val eOrder = order.rdd.groupBy(r => r.getAs[String]("open_udid")).flatMap {
//      case (open_udid, profiledfList) =>
//        val m = new mutable.HashMap[Int, Int]
//        profiledfList.toArray.foreach { r=>
//          m.put(r.getAs[Int]("tag_id"), r.getAs[Int]("order"))
//        }
//        val allTag = Array(2001, 2002, 2003, 2004)
//        val aList = allTag.map { a=>
//          (open_udid, a, m.getOrElse(a, 0))
//        }
//        aList
//    }.toDF("open_udid", "tag_id", "order")
//
//    eProfile.join(eOrder, Seq("open_udid", "tag_id"), "full").show()

    import org.apache.spark.sql.functions.lit
    var df = Seq(
      ("a", 18),
      ("b", 19)
    ).toDF("name", "age")

    df = df.withColumn("sex", lit(1))

    df.show()
  }

}
