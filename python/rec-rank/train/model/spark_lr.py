import sys
import json
from pyspark.sql import *
from pyspark.sql.functions import *
from pyspark.mllib.classification import LogisticRegressionWithLBFGS
from pyspark.mllib.evaluation import BinaryClassificationMetrics
from pyspark.mllib.util import MLUtils


def main():
    if len(sys.argv) != 2:
        print("parameter error")
        sys.exit(1)

    arg = json.loads(sys.argv[1])

    spark = SparkSession.builder \
        .appName("spark_lr") \
        .enableHiveSupport() \
        .getOrCreate()

    scene = arg["scene"]
    version = arg["version"]
    model = arg["model"]
    sample_path = arg["sample-path"]
    num_features = arg["num-features"]
    num_classes = arg["num-classes"]

    train_file = sample_path.replace("{scene}.{model}.{version}.[train|test]", "%s.%s.%s.train" % (scene, model, version))
    test_file = sample_path.replace("{scene}.{model}.{version}.[train|test]", "%s.%s.%s.test" % (scene, model, version))

    train = MLUtils.loadLibSVMFile(spark.sparkContext, train_file, num_features)
    test = MLUtils.loadLibSVMFile(spark.sparkContext, test_file, num_features)

    print(train.count())
    print(test.count())
    print(train.getNumPartitions())
    print(test.getNumPartitions())

    # model = LogisticRegressionWithLBFGS()
    # model.train(train, numClasses=num_classes)
    #
    # predict_and_label = testing.map(lambda r: (model.predict(r.features), r.label))
    # metrics = BinaryClassificationMetrics(predict_and_label)
    #
    # print(metrics.areaUnderROC())


if __name__ == "__main__":
    main()
