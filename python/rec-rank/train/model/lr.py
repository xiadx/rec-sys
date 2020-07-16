import os
import pickle
import numpy as np
from sklearn.metrics import roc_auc_score
import tensorflow as tf


job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
sample_path = os.path.join(job_path, "data/rec-rank/sample")
train_file = os.path.join(sample_path, "sample.rec.lr.v1.train.shuf")
test_file = os.path.join(sample_path, "sample.rec.lr.v1.test.shuf")


def cache(cache_path, fn, *args, **kwargs):
    """
    Cache-wrapper for a function or class. If the cache-file exists
    then the data is reloaded and returned, otherwise the function
    is called and the result is saved to cache. The fn-argument can
    also be a class instead, in which case an object-instance is
    created and saved to the cache-file.
    Args:
        cache_path: File-path for the cache-file.
        fn: Function or class to be called.
        args: Arguments to the function or class-init.
        kwargs: Keyword arguments to the function or class-init.
    Return:
        The result of calling the function or creating the object-instance.
    """
    # If the cache-file exists.
    if os.path.exists(cache_path):
        # Load the cached data from the file.
        with open(cache_path, mode="rb") as file:
            obj = pickle.load(file)

        print("- Data saved to cache-file: " + cache_path)
    else:
        # The cache-file does not exist.

        # Call the function / class-init with the supplied arguments.
        obj = fn(*args, **kwargs)

        # Save the data to a cache-file.
        with open(cache_path, mode="wb") as file:
            pickle.dump(obj, file)

        print("- Data saved to cache-file: " + cache_path)

    return obj


def load_data():
    x, y = [], []
    with open(os.path.join(sample_path, "sample.rec.v1.train"), "r") as fp:
        for line in fp:
            fs = line.split(" ")
            try:
                y.append(float(fs[0].split(":")[1]))
                x.append([float(_.split(":")[1]) for _ in fs[1:]])
            except Exception as e:
                print(e)
                print(fs)
    return np.array(x), np.array(y)


@tf.function
def prepare_features_and_labels(x, y):
    x = tf.cast(x, np.float32)
    y = tf.cast(y, np.int64)
    return x, y


def dataset():
    x, y = cache(os.path.join(sample_path, "sample.rec.v1.train.np"), load_data)
    ds = tf.data.Dataset.from_tensor_slices((x, y))
    ds = ds.map(prepare_features_and_labels)
    ds = ds.take(20000).shuffle(20000).batch(100)
    return ds


def create_dataset():
    job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
    ds = tf.data.TextLineDataset([os.path.join(job_path, "data/mllib/sample_libsvm_data.txt")])
    for line in ds:
        label = float(line.split(" ")[0])
        feature = {f.split(":")[0]: f.split(":")[1] for f in line.spalit(" ")[1:]}


def main():
    # create_dataset()
    # # ds = dataset()
    # x, y = cache(os.path.join(sample_path, "sample.rec.v1.train.np"), load_data)
    # print(len(y), sum(y))
    # import xgboost as xgb
    # param = {
    #     "objective": "binary:logistic",
    #     "learning_rate": 0.1,
    #     "max_depth": 7,
    #     "min_child_weight": 1,
    #     "gamma": 0,
    #     "subsample": 0.8,
    #     "colsample_bytree": 0.8,
    #     "scale_pos_weight": 1,
    #     "n_estimators": 100,
    # }
    # sk_xgb = xgb.XGBClassifier(**param)
    # sk_xgb.fit(x, y)
    # # inputs = tf.keras.Input(shape=(11, ))
    # # outputs = tf.keras.layers.Dense(1, activation=tf.nn.sigmoid)(inputs)
    # # model = tf.keras.Model(inputs=inputs, outputs=outputs)
    # # model.compile(optimizer=tf.keras.optimizers.Adam(0.001),
    # #               loss=tf.losses.BinaryCrossentropy(),
    # #               metrics=["binary_crossentropy"])
    # # model.fit(x, y, epochs=1, batch_size=1024)
    # #
    # y_pred = sk_xgb.predict(x).flatten()
    # print(sum(y_pred))
    # print(roc_auc_score(y, y_pred))

    # x = tf.constant([2.0, 3.0], [1.0, 4.0])
    # print(x)
    pass




if __name__ == "__main__":
    main()
