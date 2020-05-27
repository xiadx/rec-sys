package ge

import scala.collection.mutable

class EntityNode {

  var itype: String = _
  var iid: String = _
  var ctimeList: mutable.ListBuffer[Long] = mutable.ListBuffer.empty[Long]

  var lat = .0
  var lng = .0

  var itemUuid: String = _
  var freq = 0L

  def this(itype: String, iid: String, ctime: Long) {
    this()
    this.itype = itype
    this.iid = iid
    this.ctimeList.append(ctime)
    this.itemUuid = itype + "-" + iid
    this.freq = 1L
  }

  def add(ctime: Long): Unit = {
    this.ctimeList.append(ctime)
    this.freq += 1
  }

  def getCtimeAverage: Long = {
    val n: Int = this.ctimeList.length
    var t: Long = 0
    for (c <- this.ctimeList) {
      t += c
    }
    ((1.0 * t) / n).toLong
  }

  override def toString: String = this.itemUuid + "-" + this.getCtimeAverage + "\t" + "频次:" + this.ctimeList.length

  def getItype: String = itype

  def setItype(itype: String): Unit = {
    this.itype = itype
  }

  def getIid: String = iid

  def setIid(iid: String): Unit = {
    this.iid = iid
  }

  def getItemUuid: String = itemUuid

  def setItemUuid(itemUuid: String): Unit = {
    this.itemUuid = itemUuid
  }

  def getFreq: Long = freq

  def setFreq(freq: Long): Unit = {
    this.freq = freq
  }

  def getCtimeList: mutable.ListBuffer[Long] = ctimeList

  def setCtimeList(ctimeList: mutable.ListBuffer[Long]): Unit = {
    this.ctimeList = ctimeList
  }

  def getLat: Double = lat

  def setLat(lat: Double): Unit = {
    this.lat = lat
  }

  def getLng: Double = lng

  def setLng(lng: Double): Unit = {
    this.lng = lng
  }

}
