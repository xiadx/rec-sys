import os
import sys

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.metrics import roc_curve
from IPython.display import clear_output
from six.moves import urllib

import tensorflow.compat.v2.feature_column as fc

import tensorflow as tf


def make_input_fn(data_df, label_df, num_epochs=10, shuffle=True, batch_size=32):
    def input_function():
        ds = tf.data.Dataset.from_tensor_slices((dict(data_df), label_df))
        if shuffle:
            ds = ds.shuffle(1000)
        ds = ds.batch(batch_size).repeat(num_epochs)
        return ds
    return input_function


def main():
    # Load dataset.
    dftrain = pd.read_csv('https://storage.googleapis.com/tf-datasets/titanic/train.csv')
    dfeval = pd.read_csv('https://storage.googleapis.com/tf-datasets/titanic/eval.csv')
    y_train = dftrain.pop('survived')
    y_eval = dfeval.pop('survived')

    print(dftrain.head())
    print(dftrain.describe())
    print(dftrain.shape[0], dfeval.shape[0])

    dftrain.age.hist(bins=20)
    plt.show()

    dftrain.sex.value_counts().plot(kind='barh')
    plt.show()

    dftrain['class'].value_counts().plot(kind='barh')
    plt.show()

    pd.concat([dftrain, y_train], axis=1).groupby('sex').survived.mean().plot(kind='barh').set_xlabel('% survive')
    plt.show()

    CATEGORICAL_COLUMNS = ['sex', 'n_siblings_spouses', 'parch', 'class', 'deck',
                           'embark_town', 'alone']
    NUMERIC_COLUMNS = ['age', 'fare']

    feature_columns = []
    for feature_name in CATEGORICAL_COLUMNS:
        vocabulary = dftrain[feature_name].unique()
        feature_columns.append(tf.feature_column.categorical_column_with_vocabulary_list(feature_name, vocabulary))

    for feature_name in NUMERIC_COLUMNS:
        feature_columns.append(tf.feature_column.numeric_column(feature_name, dtype=tf.float32))

    train_input_fn = make_input_fn(dftrain, y_train)
    eval_input_fn = make_input_fn(dfeval, y_eval, num_epochs=1, shuffle=False)

    ds = make_input_fn(dftrain, y_train, batch_size=10)()
    for feature_batch, label_batch in ds.take(1):
        print('Some feature keys:', list(feature_batch.keys()))
        print()
        print('A batch of class:', feature_batch['class'].numpy())
        print()
        print('A batch of Labels:', label_batch.numpy())

    age_column = feature_columns[7]
    print(tf.keras.layers.DenseFeatures([age_column])(feature_batch).numpy())

    gender_column = feature_columns[0]
    print(tf.keras.layers.DenseFeatures([tf.feature_column.indicator_column(gender_column)])(feature_batch).numpy())

    linear_est = tf.estimator.LinearClassifier(feature_columns=feature_columns)
    linear_est.train(train_input_fn)
    result = linear_est.evaluate(eval_input_fn)

    clear_output()
    print(result)

    age_x_gender = tf.feature_column.crossed_column(['age', 'sex'], hash_bucket_size=100)
    derived_feature_columns = [age_x_gender]
    linear_est = tf.estimator.LinearClassifier(feature_columns=feature_columns+derived_feature_columns)
    linear_est.train(train_input_fn)
    result = linear_est.evaluate(eval_input_fn)

    clear_output()
    print(result)

    pred_dicts = list(linear_est.predict(eval_input_fn))
    probs = pd.Series([pred['probabilities'][1] for pred in pred_dicts])

    probs.plot(kind='hist', bins=20, title='predicted probabilities')
    plt.show()

    fpr, tpr, _ = roc_curve(y_eval, probs)
    plt.plot(fpr, tpr)
    plt.title('ROC curve')
    plt.xlabel('false positive rate')
    plt.ylabel('true positive rate')
    plt.xlim(0,)
    plt.ylim(0,)
    plt.show()


if __name__ == "__main__":
    main()
