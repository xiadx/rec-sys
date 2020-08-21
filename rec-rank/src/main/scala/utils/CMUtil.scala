package utils

import org.apache.spark.storage.StorageLevel
import org.apache.spark.sql.types._

import scala.collection.mutable

object CMUtil {

  val storageLevel: mutable.HashMap[String, StorageLevel] = mutable.HashMap[String, StorageLevel](
    "none" -> StorageLevel.NONE,
    "disk_only" -> StorageLevel.DISK_ONLY,
    "disk_only_2" -> StorageLevel.DISK_ONLY_2,
    "memory_only" -> StorageLevel.MEMORY_ONLY,
    "memory_only_2" -> StorageLevel.MEMORY_ONLY_2,
    "memory_only_ser" -> StorageLevel.MEMORY_ONLY_SER,
    "memory_only_ser_2" -> StorageLevel.MEMORY_ONLY_SER_2,
    "memory_and_disk" -> StorageLevel.MEMORY_AND_DISK,
    "memory_and_disk_2" -> StorageLevel.MEMORY_AND_DISK_2,
    "memory_and_disk_ser" -> StorageLevel.MEMORY_AND_DISK_SER,
    "memory_and_disk_ser_2" -> StorageLevel.MEMORY_ONLY_SER_2,
    "off_heap" -> StorageLevel.OFF_HEAP
  )

  val typesMap = Map(
    "long" -> LongType,
    "integer" -> IntegerType,
    "double" -> DoubleType,
    "float" -> FloatType,
    "string" -> StringType
  )

  def main(args: Array[String]): Unit = {
    print(storageLevel("none"))
  }
  
}
