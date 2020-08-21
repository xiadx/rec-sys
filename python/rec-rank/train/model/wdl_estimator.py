import os
import re
import sys
import json
import time
import logging
import functools
import inspect
import datetime
import tensorflow as tf

job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../"))
entity_path = os.path.join(job_path, "frame/entity")
sys.path.append(os.path.abspath(entity_path))

from TFFeature import TFFeature
from TFBucketizedFeature import TFBucketizedFeature


def get_days_list(start, end, fmt="%Y%m%d"):
    r = []
    cur = end
    while datetime.datetime.strptime(cur, fmt) >= datetime.datetime.strptime(start, fmt):
        r.append(cur)
        cur = (datetime.datetime.strptime(cur, fmt) + datetime.timedelta(days=-1)).strftime(fmt)
    return r


def get_tfrecord_files(list_dirs):
    tfrecord_files = []
    for dir in list_dirs:
        files = tf.io.gfile.listdir(dir)
        tfrecord_files.extend([os.path.join(dir, f) for f in files if f.endswith(".tfrecord.gz")])
    return tfrecord_files


def get_tf_features(features):
    tf_features = dict()
    for f in features:
        name = f["name"]
        alias = f["alias"]
        column = f["column"]
        dtype = f["dtype"]
        shape = f["shape"]
        default_value = f["default_value"]
        scope = f["scope"]
        tf_features[name] = TFFeature(name,
                                      alias,
                                      column,
                                      dtype,
                                      shape,
                                      default_value,
                                      scope)
    return tf_features


def get_tf_bucketized_features(features):
    tf_bucketized_features = dict()
    for f in features:
        name = f["name"]
        boundaries = f["boundaries"]
        scope = f["scope"]
        tf_bucketized_features[name] = TFBucketizedFeature(name,
                                                           boundaries,
                                                           scope)
    return tf_bucketized_features


def get_feature_description(tf_features):
    pattern = re.compile(r"\w{0,2}int\d{1,2}|\w{0,2}float\d{1,2}")
    dtype_dict = {"tf.%s" % k: v for k, v in inspect.getmembers(tf) if pattern.match(k)}
    return {k: tf.io.FixedLenFeature(dtype=dtype_dict[v.dtype], shape=v.shape, default_value=v.default_value) for k, v in tf_features.items()}


def get_feature_column(tf_features, bucketized_features, embedding_features, cross_features):
    wide_columns, deep_columns = [], []
    for f in tf_features.values():
        if f.scope.find("wide") != -1:
            wide_columns.append(getattr(tf.feature_column, f.column)(f.name))
        if f.scope.find("deep") != -1:
            deep_columns.append(getattr(tf.feature_column, f.column)(f.name))
    for f in bucketized_features.values():
        if f.scope.find("wide") != -1:
            wide_columns.append(tf.feature_column.bucketized_column(
                getattr(tf.feature_column, tf_features[f.name].column)(f.name),
                boundaries=f.boundaries))
        if f.scope.find("deep") != -1:
            deep_columns.append(tf.feature_column.bucketized_column(
                getattr(tf.feature_column, tf_features[f.name].column)(f.name),
                boundaries=f.boundaries))
    # for f in embedding_features.values():
    #     pass
    # for f in cross_features.values():
    #     pass
    return wide_columns, deep_columns


def input_fn_tfrecord(filenames, feature_description, label=None, batch_size=256, num_epochs=1, num_parallel_calls=8,
                      shuffle_factor=10, prefetch_factor=1, compression_type="GZIP"):
    def _parse_examples(serial_exmp):
        features = tf.io.parse_single_example(serial_exmp, features=feature_description)
        if label is not None:
            labels = features.pop(label)
            return features, labels
        return features

    def input_fn():
        dataset = tf.data.TFRecordDataset(filenames, compression_type=compression_type)
        dataset = dataset.map(_parse_examples, num_parallel_calls=num_parallel_calls)
        if shuffle_factor > 0:
            dataset = dataset.shuffle(buffer_size=batch_size * shuffle_factor)

        dataset = dataset.repeat(num_epochs).batch(batch_size)

        if prefetch_factor > 0:
            dataset = dataset.prefetch(buffer_size=batch_size * prefetch_factor)

        return dataset

    return input_fn


def serving_input_fn(feature_columns):
    feature_spec = tf.feature_column.make_parse_example_spec(feature_columns)
    serving_input_receiver_fn = (tf.estimator.export.build_parsing_serving_input_receiver_fn(feature_spec))
    return serving_input_receiver_fn


def WDLEstimator(model_dir, wide_columns, deep_columns, linear_optimizer, dnn_optimizer, dnn_hidden_units):
    model_es = tf.estimator.DNNLinearCombinedClassifier(
        model_dir=model_dir,
        linear_feature_columns=wide_columns,
        linear_optimizer=linear_optimizer,
        dnn_feature_columns=deep_columns,
        dnn_optimizer=dnn_optimizer,
        dnn_hidden_units=dnn_hidden_units)
    return model_es


def timeit(fn):
    # create logger
    logger = logging.getLogger('timeit')
    logger.setLevel(logging.INFO)

    # create console handler and set level to info
    ch = logging.StreamHandler()
    ch.setLevel(logging.INFO)

    # create formatter
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')

    # add formatter to ch
    ch.setFormatter(formatter)

    # add ch to logger
    logger.addHandler(ch)

    @functools.wraps(fn)
    def wrapper(*args, **kwargs):
        begin = time.time()
        result = fn(*args, **kwargs)
        logger.info("cost %.2fs for %s" % (time.time() - begin, fn.__name__))
        return result

    return wrapper

@timeit
def main():
    if len(sys.argv) != 2:
        print("parameter error")
        sys.exit(1)

    arg = json.loads(sys.argv[1])

    scene = arg["scene"]
    version = arg["version"]
    model = arg["model"]
    fmt = arg["format"]
    train_path = arg["train-path"].replace("{scene}.{model}.{version}.{format}",
                                           "%s.%s.%s.%s" % (scene, model, version, fmt))
    test_path = arg["test-path"].replace("{scene}.{model}.{version}.{format}",
                                         "%s.%s.%s.%s" % (scene, model, version, fmt))
    model_dir = arg["model-dir"]
    model_export_dir = arg["model-export-dir"]
    days_list = arg["days-list"]
    dt_part = arg["dt-part"]
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
    if dt_part:
        train_tfrecord_files = get_tfrecord_files([os.path.join(train_path, day) for day in train_days_list])
        test_tfrecord_files = get_tfrecord_files([os.path.join(test_path, day) for day in test_days_list])
    else:
        train_tfrecord_files = get_tfrecord_files([train_path])
        test_tfrecord_files = get_tfrecord_files([test_path])
    train_dataset_param = arg["ds-param"]["train"]
    test_dataset_param = arg["ds-param"]["test"]
    model_param = arg["model-param"]
    label = arg["label"]
    features = arg["features"]
    bucketized_features = arg["bucketized"]

    tf_features = get_tf_features(features)
    tf_bucketized_features = get_tf_bucketized_features(bucketized_features)

    wide_columns, deep_columns = get_feature_column(tf_features, tf_bucketized_features, None, None)

    feature_description = get_feature_description(tf_features)

    print(feature_description)

    sys.exit(1)

    train_model_input = input_fn_tfrecord(train_tfrecord_files,
                                          feature_description,
                                          label,
                                          **train_dataset_param)
    test_model_input = input_fn_tfrecord(test_tfrecord_files,
                                         feature_description,
                                         label,
                                         **test_dataset_param)

    linear_optimizer = getattr(tf.optimizers, model_param["linear-optimizer"]["name"])(**model_param["linear-optimizer"])
    dnn_optimizer = getattr(tf.optimizers, model_param["dnn-optimizer"]["name"])(**model_param["dnn-optimizer"])
    dnn_hidden_units = model_param["dnn-hidden-units"]

    model_es = WDLEstimator(model_dir=model_dir,
                            wide_columns=wide_columns,
                            deep_columns=deep_columns,
                            linear_optimizer=linear_optimizer,
                            dnn_optimizer=dnn_optimizer,
                            dnn_hidden_units=dnn_hidden_units)
    model_es.train(input_fn=train_model_input)

    serving_input_receiver_fn = serving_input_fn(wide_columns + deep_columns)
    model_es.export_saved_model(model_export_dir, serving_input_receiver_fn)

    eval_result = model_es.evaluate(test_model_input)
    print(eval_result)


if __name__ == "__main__":
    main()
