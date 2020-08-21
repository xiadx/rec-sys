package utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.sql.functions.{col, struct, udf}
import utils.entity.DataTypeEnum

import scala.collection.mutable

object DFUtil {

  def exist(path: String): Boolean = {
    val fs = FileSystem.get(new Configuration())
    val p = new Path(path)
    fs.exists(p)
  }

  def delete(path: String): Unit = {
    val fs = FileSystem.get(new Configuration())
    val p = new Path(path)
    if (fs.exists(p)) {
      fs.delete(p, true)
    }
  }

  def save(df: DataFrame, path: String, mode: SaveMode = SaveMode.Overwrite): Unit = {
    df.write.mode(mode).save(path)
  }

  def read(path: String, spark: SparkSession): DataFrame = {
    spark.read.parquet(path)
  }

  def read(paths: Array[String], spark: SparkSession): DataFrame = {
    spark.read.parquet(paths: _*)
  }

  def cast(df: DataFrame, cols: Array[String], tmap: mutable.HashMap[String, String]): DataFrame = {
    val cs = cols.map { c =>
      col(c).cast(CMUtil.typesMap(tmap(c)))
    }
    df.select(cs: _*)
  }

  def fill(df: DataFrame, cols: Array[String], dmap: mutable.HashMap[String, Any]): DataFrame = {
    val dm = cols.map { c =>
      c -> dmap(c)
    }.toMap
    df.na.fill(dm)
  }

  def decimal(df: DataFrame, cols: Array[String], tmap: mutable.HashMap[String, String], decl: String = "%.6f"): DataFrame = {
    val declFloat = udf((v: Float) => {
      v.formatted(decl).toFloat
    })
    val declDouble = udf((v: Double) => {
      v.formatted(decl).toDouble
    })
    var rst = df
    cols.filter(c => tmap(c).equals(DataTypeEnum.FLOAT.toString)).foreach { c =>
      rst = rst.withColumn(c, declFloat(col(c)))
    }
    cols.filter(c => tmap(c).equals(DataTypeEnum.DOUBLE.toString)).foreach { c =>
      rst = rst.withColumn(c, declDouble(col(c)))
    }
    rst
  }

  def sparse(df: DataFrame, featureColumns: Array[String], sparseColumns: String): DataFrame = {
    def spar: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(featureColumns.map { c =>
        r.get(r.fieldIndex(c)).toString.toDouble
      }).toSparse
    })
    df.withColumn(sparseColumns, spar(struct(featureColumns.map(c => col(c)): _*)))
  }

  def saveAsLibSVMFile(df: DataFrame, labelColumns: String, sparseColumns: String, npartitions: Int, path: String): Unit = {
    val rdd = df.rdd.map { r =>
      LabeledPoint(
        r.get(r.fieldIndex(labelColumns)).toString.toDouble,
        r.getAs[Vector](sparseColumns)
      )
    }.repartition(npartitions)
    delete(path)
    MLUtils.saveAsLibSVMFile(rdd, path)
  }

  def convertVectorColumnsToML(df: DataFrame, cols: Array[String]): DataFrame = {
    MLUtils.convertVectorColumnsToML(df, cols: _*)
  }

  def saveDataFrameAsHiveTable(rawInput: DataFrame, tableName: String, dt: String, spark: SparkSession): Unit = {
    import org.apache.spark.sql.functions.lit
    if (spark.catalog.tableExists(tableName)) {
      val dropPartitionSql =
        """
          |alter table %s drop if exists partition (dt='%s')
        """.stripMargin.format(tableName, dt)
      spark.sql(dropPartitionSql)
    }
    val withDateDF = rawInput.withColumn("dt", lit(dt))
    withDateDF.write
      .partitionBy("dt")
      .format("hive")
      .mode("append")
      .saveAsTable(tableName)
    successToHive(tableName, dt, spark)
  }

  def successToHive(tableName: String, dt: String, spark: SparkSession): Unit = {
    val dropPartitionSql =
      """
        |alter table %s drop if exists partition (dt='%s.done')
      """.stripMargin.format(tableName, dt)
    val addPartitionSql =
      """
        |alter table %s add partition (dt='%s.done')
      """.stripMargin.format(tableName, dt)
    spark.sql(dropPartitionSql)
    spark.sql(addPartitionSql)
  }

}
