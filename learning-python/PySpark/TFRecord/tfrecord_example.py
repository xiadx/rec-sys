import os
from pyspark.sql import *
from pyspark.sql.types import *


def main():
    spark = SparkSession.builder \
        .appName("dump_sample") \
        .enableHiveSupport() \
        .getOrCreate()

    job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../"))
    path = os.path.join(job_path, "data/learning-python/pyspark/tfrecord/tfrecord-example/test-output.tfrecord")

    fields = [StructField("id", IntegerType()), StructField("IntegerCol", IntegerType()),
              StructField("LongCol", LongType()), StructField("FloatCol", FloatType()),
              StructField("DoubleCol", DoubleType()), StructField("VectorCol", ArrayType(DoubleType(), True)),
              StructField("StringCol", StringType())]
    schema = StructType(fields)
    rows = [[11, 1, 23, 10.0, 14.0, [1.0, 2.0], "r1"], [21, 2, 24, 12.0, 15.0, [2.0, 2.0], "r2"]]
    rdd = spark.sparkContext.parallelize(rows)
    df = spark.createDataFrame(rdd, schema)
    df.write.mode("overwrite").format("tfrecord").option("recordType", "Example").save(path)
    df = spark.read.format("tfrecord").option("recordType", "Example").load(path)
    df.show()


if __name__ == "__main__":
    main()
