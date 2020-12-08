import tensorflow as tf
import pandas as pd
from collections import OrderedDict


class WideDeepModel(tf.keras.Model):

    def __init__(self, liner_features, deep_features, **kwargs):
        super(WideDeepModel, self).__init__(**kwargs)
        self.liner_features = liner_features
        self.deep_features = deep_features

    def call(self, input):
        liner_layer_input = tf.keras.layers.DenseFeatures(self.liner_features)(input)
        deep_layer_input = tf.keras.layers.DenseFeatures(self.liner_features)(input)

        linear_layer = tf.keras.layers.Dense(units=32,activation='relu')(liner_layer_input)
        deep_layer = tf.keras.layers.Dense(units=32,activation='relu')(deep_layer_input)
        concate = tf.keras.layers.concatenate([linear_layer, deep_layer], axis=1)
        output = tf.keras.layers.Dense(1)(concate)
        return output


if __name__ == '__main__':
    CSV_COLUMN_NAMES = ['SepalLength', 'SepalWidth', 'PetalLength', 'PetalWidth', 'Species']
    SPECIES = ['Setosa', 'Versicolor', 'Virginica']
    train_path = tf.keras.utils.get_file(
        "iris_training.csv", "https://storage.googleapis.com/download.tensorflow.org/data/iris_training.csv")
    test_path = tf.keras.utils.get_file(
        "iris_test.csv", "https://storage.googleapis.com/download.tensorflow.org/data/iris_test.csv")

    train = pd.read_csv(train_path, names=CSV_COLUMN_NAMES, header=0)
    test = pd.read_csv(test_path, names=CSV_COLUMN_NAMES, header=0)
    train_y = train.pop('Species')
    test_y = test.pop('Species')
    train_dict = OrderedDict(train)
    input_data = tf.data.Dataset.from_tensor_slices((train_dict,train_y))

    print(input_data.as_numpy_iterator().next())

    my_feature_columns = []
    inputs = []
    for key in train_dict.keys():
        # my_feature_columns.append(tf.feature_column.numeric_column(key=key))
        inputs.append(tf.keras.Input(shape=(1, ), name=key))

    model = WideDeepModel(liner_features=inputs, deep_features=inputs)


    optimizer = tf.keras.optimizers.Adam(learning_rate=0.0
    model.compile(optimizer, loss=tf.keras.losses.cosine_similarity)
    model.fit(input_data, epochs=2, batch_size=64)



