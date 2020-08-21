import tensorflow as tf
import pandas as pd
from collections import OrderedDict


class WideDeepModel(tf.keras.models.Model):

    def __init__(self, liner_features, deep_features, input_layer, **kwargs):
        super(WideDeepModel, self).__init__(**kwargs)
        self.liner_features = liner_features
        self.deep_features = deep_features
        self.liner_layer = tf.keras.layers.DenseFeatures(liner_features)
        self.deep_layer = tf.keras.layers.DenseFeatures(deep_features)
        self.input_layer = input_layer

    def call(self, inputs, training=None, mask=None):
        print(inputs)
        input_dict = {}
        for k in range(len(self.liner_features)):
            input_dict[self.liner_features[k].name] = inputs[k]
        print(input_dict)
        print("_____")
        print(self.liner_features)
        liner_layer_input = self.liner_layer(input_dict)
        deep_layer_input = self.deep_layer(input_dict)
        deep_layer = tf.keras.layers.Dense(units=32,activation='relu')(deep_layer_input)
        deep_layer = tf.keras.layers.Dense(units=32,activation='relu')(deep_layer)
        concate = tf.keras.layers.concatenate([liner_layer_input, deep_layer], axis=-1)
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
    train_dict = dict(train)
    input_data = tf.data.Dataset.from_tensor_slices((train_dict, train_y))

    print(input_data.as_numpy_iterator().next())

    my_feature_columns = []
    inputs = []
    input_layer = []
    for key in train_dict.keys():
        my_feature_columns.append(tf.feature_column.numeric_column(key=key))
        inputs.append(tf.TensorShape(1,))
        input_layer.append(tf.keras.layers.Input(shape=(1,)))
    print(input_layer)
    # model = WideDeepModel(liner_features=my_feature_columns, deep_features=my_feature_columns,input_layer= input_layer)
    # model.build(input_shape=inputs)
    # optimizer = tf.keras.optimizers.Adam(learning_rate=0.01)
    # model.compile(optimizer, loss=tf.keras.losses.binary_crossentropy)

    input_dict = {}
    for k in range(len(my_feature_columns)):
        input_dict[my_feature_columns[k].name] = input_layer[k]
    print("------------------")
    print(input_dict)
    deep_layer = tf.keras.layers.DenseFeatures(my_feature_columns)(input_dict)
    x = tf.keras.layers.Dense(128, activation='relu')(deep_layer)
    x = tf.keras.layers.Dense(64, activation='relu')(x)
    baggage_pred = tf.keras.layers.Dense(1, activation='sigmoid')(x)

    model = tf.keras.Model(inputs=[k for k in input_dict.values()], outputs=baggage_pred)
    model.compile(optimizer='adam',
                  loss='binary_crossentropy',
                  metrics=['accuracy'])
    model.fit(input_data, epochs=2, batch_size=32)
