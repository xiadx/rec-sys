import os
import numpy as np
import pandas as pd
import tensorflow as tf


job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
sample_path = os.path.join(job_path, "data/rec-rank/sample")
train_file = os.path.join(sample_path, "sample.rec.lr.v1.train.shuf")
test_file = os.path.join(sample_path, "sample.rec.lr.v1.test.shuf")
model_file = os.path.join(job_path, "data/rec-rank/model/rec.lr.v1")
num_features = 474
batch = 1024
repeat = 1
encoding = "utf-8"


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


def deal_libsvm_line(line: tf.Tensor):
    l = line.numpy().decode(encoding)
    label = float(l.split(" ")[0])
    feature_dict = {int(f.split(":")[0]): float(f.split(":")[1]) for f in l.split(" ")[1:]}
    feature_list = [feature_dict[i] if i in feature_dict else 0 for i in range(num_features)]
    return np.array(feature_list), label


def create_dataset(list_files):
    ds = tf.data.TextLineDataset(list_files)
    ds = ds.map(lambda x: tf.py_function(func=deal_libsvm_line, inp=[x], Tout=(tf.float32, tf.float32)))
    return ds


def WDL():
    pass


def main():
    ds = create_dataset([train_file]).shuffle(batch * 10).batch(batch).repeat(repeat).prefetch(2)
    ds_val = create_dataset([test_file]).batch(1).repeat(repeat)
    inputs = tf.keras.Input(shape=(num_features, ))
    # bn = tf.keras.layers.BatchNormalization(axis=-1, center=True, scale=True, trainable=True)(inputs)
    # ln = tf.keras.layers.LayerNormalization(axis=-1, center=True, scale=True, trainable=True)(inputs)
    outputs = tf.keras.layers.Dense(1, bias_initializer=tf.keras.initializers.GlorotUniform(), kernel_regularizer=tf.keras.regularizers.l2(0.002), name="outputs")(inputs)

    ac = tf.keras.layers.Activation(tf.nn.sigmoid)(outputs)
    model = tf.keras.Model(inputs=inputs, outputs=ac)
    model.compile(optimizer=tf.keras.optimizers.SGD(1e-7),
                  loss=tf.losses.BinaryCrossentropy())

    model.fit(ds, epochs=5, validation_data=ds_val)

    y_pred = model.predict(ds, batch_size=1000).flatten()
    print(y_pred)
    y_true = []
    for x, y in ds.as_numpy_iterator():
        y_true.extend(y)

    print(auc(y_true, y_pred))

    from sklearn.metrics import roc_auc_score
    print(roc_auc_score(y_true, y_pred))


if __name__ == "__main__":
 main()
