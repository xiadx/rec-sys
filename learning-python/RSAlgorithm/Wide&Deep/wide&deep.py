#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""wide&deep model"""


from __future__ import division
import pandas as pd
import tensorflow as tf


def preprocessing(train_file, test_file, train_path, test_path):
    """
    preprocessing
    Args:
        train_file: train file
        test_file: test file
        train_path: train path
        test_path: test path
    """
    train_df = pd.read_csv(train_file, sep=", ")
    test_df = pd.read_csv(test_file, sep=", ")

    train_df.to_csv(train_path, sep=",", index=False)

    test_df["class"] = test_df["class"].apply(lambda r: r[:-1])
    test_df.to_csv(test_path, sep=",", index=False)


def test_label(test_file):
    """
    test label
    Args:
        test_file: test file
    Return:
        test label
    """
    labels = []
    with open(test_file, "rb") as fp:
        n = 0
        for line in fp:
            if n == 0:
                n += 1
                continue
            if "?" in line.strip():
                continue
            cls = line.strip().split(",")[-1]
            if cls == ">50K":
                labels.append(1)
            elif cls == "<=50K":
                labels.append(0)
            else:
                print "label error"
    return labels


def auc(y_true, y_pred):
    """
    calculate auc
    Args:
        y_true: label
        y_pred: predict
    Return:
        auc
    """
    l = [(i, t[0], t[1]) for i, t in enumerate(sorted(zip(y_true, y_pred), key=lambda x: x[1]))]
    # p = sum([x[1] for x in l])
    # n = len(l) - p
    # t = sum([x[0] for x in l if x[1] == 1])
    n, p, t = 0, 0, 0
    for i, y, r in l:
        if y == 0:
            n += 1
        else:
            p += 1
            t += i
    return (t-(p*(p+1)/2)) / (n*p)


def feature_column():
    """
    feature column
    age, workclass, education, education-num,
    marital-status, occupation, relationship,
    race, sex, capital-gain, capital-loss,
    hours-per-week, native-country
    Return:
        wide columns and deep columns
    """
    age = tf.feature_column.numeric_column("age")
    education_num = tf.feature_column.numeric_column("education-num")
    capital_gain = tf.feature_column.numeric_column("capital-gain")
    capital_loss = tf.feature_column.numeric_column("capital-loss")
    hours_per_week = tf.feature_column.numeric_column("hours-per-week")

    workclass = tf.feature_column.categorical_column_with_hash_bucket("workclass", hash_bucket_size=512)
    education = tf.feature_column.categorical_column_with_hash_bucket("education", hash_bucket_size=521)
    marital_status = tf.feature_column.categorical_column_with_hash_bucket("marital-status", hash_bucket_size=512)
    occupation = tf.feature_column.categorical_column_with_hash_bucket("occupation", hash_bucket_size=512)
    relationship = tf.feature_column.categorical_column_with_hash_bucket("relationship", hash_bucket_size=512)
    race = tf.feature_column.categorical_column_with_hash_bucket("race", hash_bucket_size=512)
    sex = tf.feature_column.categorical_column_with_hash_bucket("sex", hash_bucket_size=512)

    age_bucket = tf.feature_column.bucketized_column(age, boundaries=[18, 25, 30, 35, 40, 45, 50, 55, 60, 65])
    gain_bucket = tf.feature_column.bucketized_column(capital_gain, boundaries=[0, 1000, 2000, 3000, 10000])
    loss_bucket = tf.feature_column.bucketized_column(capital_loss, boundaries=[0, 1000, 2000, 3000, 5000])

    cross_columns = [
        tf.feature_column.crossed_column([age_bucket, gain_bucket], hash_bucket_size=36),
        tf.feature_column.crossed_column([gain_bucket, loss_bucket], hash_bucket_size=16)
    ]

    base_columns = [workclass, education, marital_status, occupation,
                    relationship, race, sex, age_bucket, gain_bucket, loss_bucket]

    wide_columns = base_columns + cross_columns

    deep_columns = [age,
                    education_num,
                    capital_gain,
                    capital_loss,
                    hours_per_week,
                    tf.feature_column.embedding_column(workclass, 9),
                    tf.feature_column.embedding_column(education, 9),
                    tf.feature_column.embedding_column(marital_status, 9),
                    tf.feature_column.embedding_column(occupation, 9),
                    tf.feature_column.embedding_column(relationship, 9),
                    tf.feature_column.embedding_column(race, 9),
                    tf.feature_column.embedding_column(sex, 9)]

    return wide_columns, deep_columns


def build(wide_columns, deep_columns, model_folder):
    """
    build model
    Args:
         wide_columns: wide columns
         deep_columns: deep columns
         model_folder: model folder
    Return:
        model_es, serving_input_fn
    """
    model_es = tf.estimator.DNNLinearCombinedClassifier(
        model_dir=model_folder,
        linear_feature_columns=wide_columns,
        linear_optimizer=tf.train.FtrlOptimizer(0.1, l2_regularization_strength=1.0),
        dnn_feature_columns=deep_columns,
        dnn_optimizer=tf.train.ProximalAdagradOptimizer(learning_rate=0.1,
                                                        l1_regularization_strength=0.001,
                                                        l2_regularization_strength=0.001),
        dnn_hidden_units=[128, 64, 32, 16]
    )
    feature_columns = wide_columns + deep_columns
    feature_spec = tf.feature_column.make_parse_example_spec(feature_columns)
    serving_input_fn = (tf.estimator.export.build_parsing_serving_input_receiver_fn(feature_spec))
    return model_es, serving_input_fn


def input(data_file, re_time, shuffle, batch_num, predict):
    """
    input function
    Args:
        data_file: train data, test data
        re_time: time to repeat the data file
        shuffle: true or false shuffle
        batch_num: batch numbers
        predict: true or false train or test
    Return:
        train feature train label or test feature
    """
    _CSV_COLUMN_DEFAUTL = [[0], [''], [0], [''], [0], [''], [''], [''], [''], [''],
                           [0], [0], [0], [''], ['']]

    _CSV_COLUMNS = ['age', 'workclass', 'fnlwgt', 'education',
                    'education-num', 'marital-status', 'occupation',
                    'relationship', 'race', 'sex', 'capital-gain',
                    'capital-loss', 'hours-per-week', 'native-country',
                    'class']

    def parse_csv(value):
        columns = tf.decode_csv(value, record_defaults=_CSV_COLUMN_DEFAUTL)
        features = dict(zip(_CSV_COLUMNS, columns))
        labels = features.pop('class')
        classes = tf.equal(labels, '>50K')
        return features, classes

    def parse_csv_predict(value):
        columns = tf.decode_csv(value, record_defaults=_CSV_COLUMN_DEFAUTL)
        features = dict(zip(_CSV_COLUMNS, columns))
        labels = features.pop('class')
        return features

    data_set = tf.data.TextLineDataset(data_file).skip(1).filter(lambda line: tf.not_equal(tf.strings.regex_full_match(line, ".*\?.*"), True))

    if shuffle:
        data_set = data_set.shuffle(buffer_size=30000)
    if predict:
        data_set = data_set.map(parse_csv_predict, num_parallel_calls=5)
    else:
        data_set = data_set.map(parse_csv, num_parallel_calls=5)

    data_set = data_set.repeat(re_time)
    data_set = data_set.batch(batch_num)

    return data_set


def train(model_es, train_file, test_file, model_export_folder, serving_input_fn):
    """
    model train
    Args:
        model_es: wide&deep estimator
        train_file: train file
        test_file: test file
        model_export_folder: model export folder
        serving_input_fn: model export function
    """
    model_es.train(input_fn=lambda: input(train_file, 20, True, 100, False))
    print model_es.evaluate(input_fn=lambda: input(test_file, 1, False, 100, False))
    model_es.export_savedmodel(model_export_folder, serving_input_fn)


def test(model_es, test_file):
    """
    model test
    Args:
        model_es: wide&deep estimator
        test_file: test file
    """
    result = model_es.predict(input_fn=lambda: input(test_file, 1, False, 100, True))
    predict = []
    for r in result:
        if "probabilities" in r:
            predict.append(r["probabilities"][1])
    label = test_label(test_file)
    print auc(label, predict)


def run(train_file, test_file, model_folder, model_export_folder):
    """
    run model
    Args:
        train_file: train data
        test_file: test data
        model_folder: model folder
        model_export_folder: model export folder
    """
    wide_columns, deep_columns = feature_column()
    model_es, serving_input_fn = build(wide_columns, deep_columns, model_folder)
    train(model_es, train_file, test_file, model_export_folder, serving_input_fn)
    test(model_es, test_file)


def main():
    preprocessing("../dataset/adult/adult.data",
                  "../dataset/adult/adult.test",
                  "../dataset/adult/train.data",
                  "../dataset/adult/test.data")
    run("../dataset/adult/train.data", "../dataset/adult/test.data", "wide&deep", "wide&deep_export")


if __name__ == "__main__":
    main()
