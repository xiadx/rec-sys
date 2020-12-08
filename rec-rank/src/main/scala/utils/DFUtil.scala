package utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.ml.feature.{MaxAbsScaler, MinMaxScaler, StandardScaler}
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.sql.functions.{col, struct, udf}
import utils.entity.{DataTypeEnum, DiscreteTypeEnum, ScalerTypeEnum, VectorTypeEnum}

import scala.collection.mutable
import scala.util.control.Breaks

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

  def decimal(df: DataFrame, inputColumns: String, outputColumns: String, decl: String): DataFrame = {
    val declFloat = udf((v: Float) => {
      v.formatted(decl).toFloat
    })
    val declDouble = udf((v: Double) => {
      v.formatted(decl).toDouble
    })
    val declVector = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      Vectors.dense(v.toArray.map(x => x.formatted(decl).toDouble))
    })
    val schema = df.schema
    var rst = df
    if (schema(inputColumns).dataType.getClass == CMUtil.typesMap("float").getClass) {
      rst = rst.withColumn(outputColumns, declFloat(col(inputColumns)))
    }
    if (schema(inputColumns).dataType.getClass == CMUtil.typesMap("double").getClass) {
      rst = rst.withColumn(outputColumns, declDouble(col(inputColumns)))
    }
    if (schema(inputColumns).dataType.getClass == classOf[org.apache.spark.mllib.linalg.VectorUDT]) {
      rst = rst.withColumn(outputColumns, declVector(col(inputColumns)))
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

  def vector(df: DataFrame, usingColumns: Array[String], outputColumns: String, typ: String = "array"): DataFrame = {
    var rst = df
    def vec: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(usingColumns.map { c =>
        r.get(r.fieldIndex(c)).toString.toDouble
      })
    })
    rst = rst.withColumn(outputColumns, vec(struct(usingColumns.map(c => col(c)): _*)))
    VectorTypeEnum.withName(typ) match {
      case VectorTypeEnum.ARRAY => rst = vec2arr(rst, outputColumns, outputColumns)
      case VectorTypeEnum.VECTOR => rst = vec2dense(rst, outputColumns, outputColumns)
      case VectorTypeEnum.SPARSE => rst = vec2sparse(rst, outputColumns, outputColumns)
      case VectorTypeEnum.JSON => rst = vec2json(rst, outputColumns, outputColumns)
      case _ => rst = vec2arr(rst, outputColumns, outputColumns)
    }
    rst
  }

  def vec2arr(df: DataFrame, usingColumns: Array[String], outputColumns: Array[String]): DataFrame = {
    def v2a: UserDefinedFunction = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      v.toArray
    })
    var rst = df
    for (i <- usingColumns.indices) {
      rst = rst.withColumn(outputColumns(i), v2a(col(usingColumns(i))))
    }
    rst
  }

  def vec2arr(df: DataFrame, usingColumns: String, outputColumns: String): DataFrame = {
    def v2a: UserDefinedFunction = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      v.toArray
    })
    df.withColumn(outputColumns, v2a(col(usingColumns)))
  }

  def vec2dense(df: DataFrame, usingColumns: String, outputColumns: String): DataFrame = {
    def v2d: UserDefinedFunction = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      v.toDense
    })
    df.withColumn(outputColumns, v2d(col(usingColumns)))
  }

  def vec2sparse(df: DataFrame, usingColumns: String, outputColumns: String): DataFrame = {
    def v2p: UserDefinedFunction = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      v.toSparse
    })
    df.withColumn(outputColumns, v2p(col(usingColumns)))
  }

  def vec2json(df: DataFrame, usingColumns: String, outputColumns: String): DataFrame = {
    def v2j: UserDefinedFunction = udf((v: org.apache.spark.mllib.linalg.Vector) => {
      v.toJson
    })
    df.withColumn(outputColumns, v2j(col(usingColumns)))
  }

  def scaler(df: DataFrame, usingColumns: String, outputColumns: String, typ: String = "standard"): DataFrame = {
    var rst = df
    rst = convertVectorColumnsToML(rst, usingColumns)
    ScalerTypeEnum.withName(typ) match {
      case ScalerTypeEnum.MINMAX => {
        val slr = new MinMaxScaler().setInputCol(usingColumns).setOutputCol(outputColumns)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
      case ScalerTypeEnum.STANDARD => {
        val slr = new StandardScaler().setInputCol(usingColumns).setOutputCol(outputColumns).setWithStd(true).setWithMean(true)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
      case ScalerTypeEnum.MAXABS => {
        val slr = new MaxAbsScaler().setInputCol(usingColumns).setOutputCol(outputColumns)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
      case _ => {
        val slr = new MinMaxScaler().setInputCol(usingColumns).setOutputCol(outputColumns)
        val model = slr.fit(rst)
        rst = model.transform(rst)
      }
    }
    convertVectorColumnsFromML(rst, outputColumns)
  }

  def discrete(df: DataFrame, usingColumns: Array[String], outputColumns: String, typ: String = "normalize", vector: String = "array", numBuckets: Int = 10, relativeError: Double = 0.001, decl: String = "%.6f"): DataFrame = {
    var rst = df
    val probs = (0.0 to 1.0 by 1.0 / numBuckets).toArray
    val splits = df.stat.approxQuantile(usingColumns, probs, relativeError).map(_.distinct.sorted)
    def disc: UserDefinedFunction = udf((r: Row) => {
      Vectors.dense(usingColumns.indices.toArray.map { i =>
        val v = r.get(r.fieldIndex(usingColumns(i)))
        val n = splits(i).length
        var left = 0
        var right = n - 1
        while (left <= right) {
          val mid = (left + right) / 2
          if (v.toString.toDouble > splits(i)(mid)) left = mid + 1 else right = mid - 1
        }
        DiscreteTypeEnum.withName(typ) match {
          case DiscreteTypeEnum.BUCKET => left
          case DiscreteTypeEnum.NORMALIZE => (left.toDouble / n).formatted(decl).toDouble
          case DiscreteTypeEnum.INTEGRAL => {
            val index = if (left == 0) 0 else left - 1
            ((index.toDouble / n) + ((v.toString.toDouble - splits(i)(index)) / (splits(i)(index + 1) - splits(i)(index)) / n)).formatted(decl).toDouble
          }
          case _ => left
        }
      })
    })
    rst = rst.withColumn(outputColumns, disc(struct(usingColumns.map(c => col(c)): _*)))
    VectorTypeEnum.withName(vector) match {
      case VectorTypeEnum.ARRAY => rst = vec2arr(rst, outputColumns, outputColumns)
      case VectorTypeEnum.VECTOR => rst = vec2dense(rst, outputColumns, outputColumns)
      case VectorTypeEnum.SPARSE => rst = vec2sparse(rst, outputColumns, outputColumns)
      case VectorTypeEnum.JSON => rst = vec2json(rst, outputColumns, outputColumns)
      case _ => rst = vec2arr(rst, outputColumns, outputColumns)
    }
    rst
  }

  def discrete(df: DataFrame, usingColumns: Array[String], outputColumns: Array[String], typ: String, numBuckets: Int, relativeError: Double, decl: String): DataFrame = {
    val probs = (0.0 to 1.0 by 1.0 / numBuckets).toArray
    var rst = df
    for (i <- usingColumns.indices) {
      val splits = df.stat.approxQuantile(usingColumns(i), probs, relativeError).distinct.sorted
      def search: UserDefinedFunction = udf(f = (v: Any) => {
        val n = splits.length
        var left = 0
        var right = n - 1
        while (left <= right) {
          val mid = (left + right) / 2
          if (v.toString.toDouble > splits(mid)) left = mid + 1 else right = mid - 1
        }
        DiscreteTypeEnum.withName(typ) match {
          case DiscreteTypeEnum.BUCKET => left
          case DiscreteTypeEnum.NORMALIZE => (left.toDouble / n).formatted(decl).toDouble
          case DiscreteTypeEnum.INTEGRAL => {
            val index = if (left == 0) 0 else left - 1
            ((index.toDouble / n) + ((v.toString.toDouble - splits(index)) / (splits(index + 1) - splits(index)) / n)).formatted(decl).toDouble
          }
          case _ => left
        }
      })
      rst = rst.withColumn(outputColumns(i), search(col(usingColumns(i))))
    }
    rst
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

  def convertVectorColumnsToML(df: DataFrame, cols: String): DataFrame = {
    MLUtils.convertVectorColumnsToML(df, cols)
  }

  def convertVectorColumnsFromML(df: DataFrame, cols: Array[String]): DataFrame = {
    MLUtils.convertVectorColumnsFromML(df, cols: _*)
  }

  def convertVectorColumnsFromML(df: DataFrame, cols: String): DataFrame = {
    MLUtils.convertVectorColumnsFromML(df, cols)
  }

//  def saveDataFrameAsHiveTable(rawInput: DataFrame, tableName: String, dt: String, spark: SparkSession): Unit = {
//    import org.apache.spark.sql.functions.lit
//    if (spark.catalog.tableExists(tableName)) {
//      val dropPartitionSql =
//        """
//          |alter table %s drop if exists partition (dt='%s')
//        """.stripMargin.format(tableName, dt)
//      spark.sql(dropPartitionSql)
//    }
//    val withDateDF = rawInput.withColumn("dt", lit(dt))
//    withDateDF.write
//      .partitionBy("dt")
//      .format("hive")
//      .mode("append")
//      .saveAsTable(tableName)
//    successToHive(tableName, dt, spark)
//  }

  def saveDataFrameAsHiveTable(rawInput: DataFrame, tableName: String, dt: String, spark: SparkSession): Unit = {
    import org.apache.spark.sql.functions.lit
    val withDateDF = rawInput.withColumn("dt", lit(dt)).drop("dt")
    withDateDF.createOrReplaceTempView("temp_view")
    spark.sql(s"insert overwrite table $tableName partition(dt='$dt') select * from temp_view")
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

  def saveDataFrameAsHiveTable(rawInput: DataFrame, tableName: String, part: mutable.HashMap[String, String], spark: SparkSession): Unit = {
    import org.apache.spark.sql.functions.lit
    var withPartDF = rawInput
    for ((k, v) <- part) {
      withPartDF = withPartDF.withColumn(k, lit(v)).drop(k)
    }
    val ps = part.map(p => s"${p._1}='${p._2}'").mkString(",")
    withPartDF.createOrReplaceTempView("temp_view")
    spark.sql(s"insert overwrite table $tableName partition($ps) select * from temp_view")
    successToHive(tableName, part, spark)
  }

  def successToHive(tableName: String, part: mutable.HashMap[String, String], spark: SparkSession): Unit = {
    val ps = part.map(p => s"${p._1}='${p._2}.done'").mkString(",")
    val dropPartitionSql =
      """
        |alter table %s drop if exists partition (%s)
      """.stripMargin.format(tableName, ps)
    val addPartitionSql =
      """
        |alter table %s add partition (%s)
      """.stripMargin.format(tableName, ps)
    spark.sql(dropPartitionSql)
    spark.sql(addPartitionSql)
  }

  def queryValidDate(dt: String, path: String, repl: String = "{dt}", pattern: String = "yyyyMMdd", interval: Int = 30): String = {
    var rst: String = null
    val loop = new Breaks()
    loop.breakable {
      for (i <- 0 to interval) {
        val date = TimeUtil.getFewDaysAgo(dt, i, pattern)
        if (exist(path.replace(repl, date))) {
          rst = date
          loop.break()
        }
      }
    }
    rst
  }

}
