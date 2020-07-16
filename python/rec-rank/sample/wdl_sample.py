import os
import sys
import json
import datetime
from pyspark.sql import *
from pyspark.sql.functions import *
from pyspark.sql.types import *
# from pyspark.mllib.regression import LabeledPoint
# from pyspark.mllib.linalg import Vectors
# from pyspark.mllib.util import MLUtils


class OneHotFeature(object):

    def __init__(self, name, value, sep):
        self.name = name
        self.value = value
        self.sep = sep


def get_fs(spark):
    sc = spark.sparkContext
    return sc._jvm.org.apache.hadoop.fs.FileSystem.get(sc._jsc.hadoopConfiguration())


def get_path(spark, filepath):
    sc = spark.sparkContext
    return sc._jvm.org.apache.hadoop.fs.Path(filepath)


def exists(spark, filepath):
    fs = get_fs(spark)
    path = get_path(spark, filepath)
    return fs.exists(path)


def delete(spark, filepath):
    fs = get_fs(spark)
    path = get_path(spark, filepath)
    return fs.delete(path, True)


def get_days_list(start, end, fmt="%Y%m%d"):
    r = []
    cur = end
    while datetime.datetime.strptime(cur, fmt) >= datetime.datetime.strptime(start, fmt):
        r.append(cur)
        cur = (datetime.datetime.strptime(cur, fmt) + datetime.timedelta(days=-1)).strftime(fmt)
    return r


def one_hot_df(df, onehot_conf):
    rst = df
    for f in onehot_conf:
        rst = one_hot_column(rst, f.name, f.value, f.sep)
    return rst


def one_hot_column(df, name, value, sep, sym="#"):
    rst = df
    for v in value:

        @udf(returnType=DoubleType())
        def contain_value(s):
            if not s:
                return 0.0
            elif v in str(s).split(sep):
                return 1.0
            else:
                return 0.0

        c = name + sym + v
        rst = rst.withColumn(c, contain_value(name))

    return rst


def get_onehot_columns(one_hot, sym="#"):
    onehot_cols = []
    for f in one_hot:
        name = f["name"]
        value = f["value"]
        for v in value:
            c = name + sym + v
            onehot_cols.append(c)
    return onehot_cols


def get_feature_map(feature_columns, onehot_columns, sym="#"):
    repair = "0\trepair=0\ti"
    feature_map = [repair]
    for i, f in enumerate(feature_columns, start=1):
        if f not in onehot_columns:
            feature_map.append("%d\t%s\tq" % (i, f))
        else:
            feature_map.append("%d\t%s\ti" % (i, f.replace(sym, "=")))
    return "\n".join(feature_map)


def save_feature_map(spark, feature_map, path):
    if exists(spark, path):
        delete(spark, path)
    spark.sparkContext.parallelize([feature_map]).repartition(1).saveAsTextFile(path)


# def save_libsvm_file(df, spark, feature_columns, label_columns, path):
#
#     def label_point(r):
#         label = r[label_columns]
#         features = Vectors.dense([r[c] for c in feature_columns])
#         return LabeledPoint(label, features)
#
#     rdd = df.rdd.map(lambda r: label_point(r))
#     if exists(spark, path):
#         delete(spark, path)
#     MLUtils.saveAsLibSVMFile(rdd, path)


def save_libsvm_file(df, spark, feature_columns, label_columns, path, npartitions):

    def deal_sample(r):
        label = float(r[label_columns])
        s = str(label) + " "
        for i, c in enumerate(feature_columns, start=1):
            f = 0
            try:
                f = float(r[c])
            except Exception as e:
                print(e)
            if f != 0:
                s += "%d:%f " % (i, f)
        s = s[:-1]
        return s

    rdd = df.repartition(npartitions).rdd.map(lambda r: deal_sample(r))

    if exists(spark, path):
        delete(spark, path)

    rdd.saveAsTextFile(path)


def save_tfrecord_file(df, spark, path, npartitions):
    if exists(spark, path):
        delete(spark, path)

    df.repartition(npartitions).write.format("tfrecord") \
        .option("recordType", "Example") \
        .option("codec", "org.apache.hadoop.io.compress.GzipCodec") \
        .save(path)


def main():
    if len(sys.argv) != 2:
        print("parameter error")
        sys.exit(1)

    arg = json.loads(sys.argv[1])

    spark = SparkSession.builder \
        .appName("wdl_sample") \
        .enableHiveSupport() \
        .getOrCreate()

    scene = arg["scene"]
    version = arg["version"]
    model = arg["model"]
    fmt = arg["format"]
    input_path = arg["input-path"].replace("{scene}", scene)
    output_path = arg["output-path"].replace("{scene}.{model}.{version}.{format}",
                                             "%s.%s.%s.%s" % (scene, model, version, fmt))
    fmap_path = arg["fmap-path"].replace("{scene}.{model}.{version}", "%s.%s.%s" % (scene, model, version))
    npartitions = arg["npartitions"]
    days_list = arg["days-list"]
    dt_part = arg["dt-part"]
    sels = arg["select"]
    label = arg["label"]
    ignore = arg["ignore"]
    whe = arg["where"]
    one_hot = arg["one-hot"]
    vec = arg["vector"]

    train_days_list = days_list["train"]["days"]
    test_days_list = days_list["test"]["days"]
    if not train_days_list:
        start = days_list["train"]["start"]
        end = days_list["train"]["end"]
        train_days_list = get_days_list(start, end)
    if not test_days_list:
        start = days_list["test"]["start"]
        end = days_list["test"]["end"]
        test_days_list = get_days_list(start, end)

    onehot_cols = [f["name"] for f in one_hot]
    vector_cols = [f["name"] for f in vec]
    ignore_cols = [c for c in ignore + onehot_cols + vector_cols if c not in sels]

    pure_onehot_cols = [c for c in onehot_cols if c not in sels]

    onehot_conf = [OneHotFeature(f["name"], f["value"], f["sep"]) for f in one_hot]

    df_train = spark.read.parquet(*[os.path.join(input_path, day) for day in train_days_list]) \
        .select(*(sels + pure_onehot_cols + vector_cols)) \
        .where(whe)
    df_train = one_hot_df(df_train, onehot_conf)
    df_test = spark.read.parquet(*[os.path.join(input_path, day) for day in test_days_list]) \
        .select(*(sels + pure_onehot_cols + vector_cols)) \
        .where(whe)
    df_test = one_hot_df(df_test, onehot_conf)

    feature_columns = [c for c in df_train.columns if c not in ignore_cols]
    onehot_columns = get_onehot_columns(one_hot)
    label_columns = label

    feature_map = get_feature_map(feature_columns, onehot_columns)
    save_feature_map(spark, feature_map, fmap_path)

    if fmt == "libsvm":
        if dt_part:
            for day in train_days_list:
                save_libsvm_file(df_train.where("dt='%s'" % day),
                                 spark,
                                 feature_columns,
                                 label_columns,
                                 output_path.replace("[train|test]", "train/%s" % day),
                                 npartitions)
            for day in test_days_list:
                save_libsvm_file(df_test.where("dt='%s'" % day),
                                 spark,
                                 feature_columns,
                                 label_columns,
                                 output_path.replace("[train|test]", "test/%s" % day),
                                 npartitions)
        else:
            save_libsvm_file(df_train,
                             spark,
                             feature_columns,
                             label_columns,
                             output_path.replace("[train|test]", "train"),
                             npartitions)
            save_libsvm_file(df_test,
                             spark,
                             feature_columns,
                             label_columns,
                             output_path.replace("[train|test]", "test"),
                             npartitions)
    if fmt == "tfrecord":
        if dt_part:
            for day in train_days_list:
                save_tfrecord_file(df_train.where("dt='%s'" % day),
                                   spark,
                                   output_path.replace("[train|test]", "train/%s" % day),
                                   npartitions)
            for day in test_days_list:
                save_tfrecord_file(df_test.where("dt='%s'" % day),
                                   spark,
                                   output_path.replace("[train|test]", "test/%s" % day),
                                   npartitions)
        else:
            save_tfrecord_file(df_train,
                               spark,
                               output_path.replace("[train|test]", "train"),
                               npartitions)
            save_tfrecord_file(df_test,
                               spark,
                               output_path.replace("[train|test]", "test"),
                               npartitions)


if __name__ == "__main__":
    main()
