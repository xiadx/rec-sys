import org.junit.Test
import entity.OnlineFeature
import sample.OnlineFeatureFactory
import sample.SampleJob.rawColumns
import utils.MapperUtil

import scala.collection.mutable

class TestFeatureConfig {

  @Test
  def testHelloWorld(): Unit = {
    println("hello world")
  }

  @Test
  def testBasicFeatureConfig(): Unit = {
    val conf =
      """
        | |-- open_udid: string (nullable = true)
        | |-- index_user_show_30: long (nullable = true)
        | |-- index_user_click_30: long (nullable = true)
        | |-- index_user_ctr_30: double (nullable = true)
        | |-- index_user_show_15: long (nullable = true)
        | |-- index_user_click_15: long (nullable = true)
        | |-- index_user_ctr_15: double (nullable = true)
        | |-- index_user_show_7: long (nullable = true)
        | |-- index_user_click_7: long (nullable = true)
        | |-- index_user_ctr_7: double (nullable = true)
        | |-- index_user_show_3: long (nullable = true)
        | |-- index_user_click_3: long (nullable = true)
        | |-- index_user_ctr_3: double (nullable = true)
        | |-- index_user_show_1: long (nullable = true)
        | |-- index_user_click_1: long (nullable = true)
        | |-- index_user_ctr_1: double (nullable = true)
        | |-- item_id: long (nullable = true)
        | |-- item_type: integer (nullable = true)
        | |-- index_item_show_30: long (nullable = true)
        | |-- index_item_click_30: long (nullable = true)
        | |-- index_item_ctr_30: double (nullable = true)
        | |-- index_item_show_15: long (nullable = true)
        | |-- index_item_click_15: long (nullable = true)
        | |-- index_item_ctr_15: double (nullable = true)
        | |-- index_item_show_7: long (nullable = true)
        | |-- index_item_click_7: long (nullable = true)
        | |-- index_item_ctr_7: double (nullable = true)
        | |-- index_item_show_3: long (nullable = true)
        | |-- index_item_click_3: long (nullable = true)
        | |-- index_item_ctr_3: double (nullable = true)
        | |-- index_item_show_1: long (nullable = true)
        | |-- index_item_click_1: long (nullable = true)
        | |-- index_item_ctr_1: double (nullable = true)
      """.stripMargin
    val lines = conf.split("\n")
    var uid = 0
    var iid = 0
    var id = 0
    var mark = ""
    var value = ""
    for (line <- lines) {
      val arr = line.split(" ")
      if (arr.length == 7) {
        val name = arr(2).dropRight(1)
        val typ = arr(3)
        if (name.contains("item")) { mark = "i"; iid += 1; id = iid }
        if (name.contains("user")) { mark = "u"; uid += 1; id = uid }
        if (name.contains("open_udid")) { mark = "u"; uid += 1; id = uid }
        val alias = name.split("_").map { w =>
          if (w.contains("30") || w.contains("15") || w.contains("7") || w.contains("3") || w.contains("1")) w else w.charAt(0)
        }.mkString("")
        val exp = ""
        val dtl = ""
        if ("string".equals(typ)) value = "-1"
        if ("long".equals(typ)) value = "0"
        if ("double".equals(typ)) value = ".0"
        val status = "1"
        println(s"""{\n\tid=$id\n\tmark="$mark"\n\tname="$name"\n\talias="$alias"\n\texp="$exp"\n\tdtl="$dtl"\n\ttyp="$typ"\n\tvalue="$value"\n\tstatus="$status"\n}""")
      }
    }
  }

  @Test
  def testAdvanceFeatureConfig(): Unit = {
    val conf =
      """
        | |-- open_udid: string (nullable = true)
        | |-- index_user_show_30: long (nullable = true)
        | |-- index_user_click_30: long (nullable = true)
        | |-- index_user_ctr_30: double (nullable = true)
        | |-- index_user_show_15: long (nullable = true)
        | |-- index_user_click_15: long (nullable = true)
        | |-- index_user_ctr_15: double (nullable = true)
        | |-- index_user_show_7: long (nullable = true)
        | |-- index_user_click_7: long (nullable = true)
        | |-- index_user_ctr_7: double (nullable = true)
        | |-- index_user_show_3: long (nullable = true)
        | |-- index_user_click_3: long (nullable = true)
        | |-- index_user_ctr_3: double (nullable = true)
        | |-- index_user_show_1: long (nullable = true)
        | |-- index_user_click_1: long (nullable = true)
        | |-- index_user_ctr_1: double (nullable = true)
        | |-- item_id: long (nullable = true)
        | |-- item_type: integer (nullable = true)
        | |-- index_item_show_30: long (nullable = true)
        | |-- index_item_click_30: long (nullable = true)
        | |-- index_item_ctr_30: double (nullable = true)
        | |-- index_item_show_15: long (nullable = true)
        | |-- index_item_click_15: long (nullable = true)
        | |-- index_item_ctr_15: double (nullable = true)
        | |-- index_item_show_7: long (nullable = true)
        | |-- index_item_click_7: long (nullable = true)
        | |-- index_item_ctr_7: double (nullable = true)
        | |-- index_item_show_3: long (nullable = true)
        | |-- index_item_click_3: long (nullable = true)
        | |-- index_item_ctr_3: double (nullable = true)
        | |-- index_item_show_1: long (nullable = true)
        | |-- index_item_click_1: long (nullable = true)
        | |-- index_item_ctr_1: double (nullable = true)
      """.stripMargin
    val lines = conf.split("\n")
    var uid = 0
    var iid = 0
    var id = 0
    var mark = ""
    var value = ""
    var source = ""
    for (line <- lines) {
      val arr = line.split(" ")
      if (arr.length == 7) {
        val name = arr(2).dropRight(1)
        val vtyp = arr(3)
        val ftyp = "raw"
        if (name.contains("item")) { mark = "i"; source = "item"; iid += 1; id = iid }
        if (name.contains("user")) { mark = "u"; source = "user"; uid += 1; id = uid }
        if (name.contains("open_udid")) { mark = "u"; uid += 1; id = uid }
        val alias = name.split("_").map { w =>
          if (w.contains("30") || w.contains("15") || w.contains("7") || w.contains("3") || w.contains("1")) w else w.charAt(0)
        }.mkString("")
        val exp = ""
        val dtl = ""
        if ("string".equals(vtyp)) value = "-1"
        if ("long".equals(vtyp)) value = "0"
        if ("double".equals(vtyp)) value = ".0"
        val status = "1"
        println(s"""{\n\tid=$id\n\tmark="$mark"\n\tname="$name"\n\talias="$alias"\n\texp="$exp"\n\tdtl="$dtl"\n\tvtyp="$vtyp"\n\tftyp="$ftyp"\n\tvalue="$value"\n\tstatus="$status"\n\tsource="$source"\n}""")
      }
    }
  }

  @Test
  def testOnlineConfig(): Unit = {
    val of = mutable.ArrayBuffer[OnlineFeature]()
    rawColumns.foreach { c =>
      of.append(OnlineFeatureFactory.getOnlineFeature(c))
    }
    println(MapperUtil.mapper.writeValueAsString(of))
  }

}
