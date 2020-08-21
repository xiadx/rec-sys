import os
import numpy as np
import tensorflow as tf

import IPython.display as display


# The following functions can be used to convert a value to a type compatible
# with tf.Example.

def _bytes_feature(value):
    """Returns a bytes_list from a string / byte."""
    if isinstance(value, type(tf.constant(0))):
        value = value.numpy() # BytesList won't unpack a string from an EagerTensor.
    return tf.train.Feature(bytes_list=tf.train.BytesList(value=[value]))

def _float_feature(value):
    """Returns a float_list from a float / double."""
    return tf.train.Feature(float_list=tf.train.FloatList(value=[value]))

def _int64_feature(value):
    """Returns an int64_list from a bool / enum / int / uint."""
    return tf.train.Feature(int64_list=tf.train.Int64List(value=[value]))


def serialize_example(feature0, feature1, feature2, feature3):
    """
    Creates a tf.Example message ready to be written to a file.
    """
    # Create a dictionary mapping the feature name to the tf.Example-compatible
    # data type.
    feature = {
        'feature0': _int64_feature(feature0),
        'feature1': _int64_feature(feature1),
        'feature2': _bytes_feature(feature2),
        'feature3': _float_feature(feature3),
    }

    # Create a Features message using tf.train.Example.

    example_proto = tf.train.Example(features=tf.train.Features(feature=feature))
    return example_proto.SerializeToString()


def tf_serialize_example(f0, f1, f2, f3):
    tf_string = tf.py_function(
        serialize_example,
        (f0, f1, f2, f3),  # pass these args to the above function.
        tf.string)  # the return type is `tf.string`.
    return tf.reshape(tf_string, ())  # The result is a scalar


def main():
    print(_bytes_feature(b'test_string'))
    print(_bytes_feature(u'test_bytes'.encode('utf-8')))

    print(_float_feature(np.exp(1)))

    print(_int64_feature(True))
    print(_int64_feature(1))

    feature = _float_feature(np.exp(1))
    print(feature.SerializeToString())

    # The number of observations in the dataset.
    n_observations = int(1e4)

    # Boolean feature, encoded as False or True.
    feature0 = np.random.choice([False, True], n_observations)

    # Integer feature, random from 0 to 4.
    feature1 = np.random.randint(0, 5, n_observations)

    # String feature
    strings = np.array([b'cat', b'dog', b'chicken', b'horse', b'goat'])
    feature2 = strings[feature1]

    # Float feature, from a standard normal distribution
    feature3 = np.random.randn(n_observations)

    # This is an example observation from the dataset.

    example_observation = []

    serialized_example = serialize_example(False, 4, b'goat', 0.9876)
    print(serialized_example)

    example_proto = tf.train.Example.FromString(serialized_example)
    print(example_proto)

    print(tf.data.Dataset.from_tensor_slices(feature1))

    features_dataset = tf.data.Dataset.from_tensor_slices((feature0, feature1, feature2, feature3))
    print(features_dataset)

    # Use `take(1)` to only pull one example from the dataset.
    for f0, f1, f2, f3 in features_dataset.take(1):
        print(f0)
        print(f1)
        print(f2)
        print(f3)

        print(tf_serialize_example(f0, f1, f2, f3))

    serialized_features_dataset = features_dataset.map(tf_serialize_example)
    print(serialized_features_dataset)

    def generator():
        for features in features_dataset:
            yield serialize_example(*features)

    serialized_features_dataset = tf.data.Dataset.from_generator(
        generator, output_types=tf.string, output_shapes=())
    print(serialized_features_dataset)

    job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../"))
    filename = os.path.join(job_path, "data/learning-python/tfrecord/tf-example/test.tfrecord")
    writer = tf.data.experimental.TFRecordWriter(filename)
    writer.write(serialized_features_dataset)

    filenames = [filename]
    raw_dataset = tf.data.TFRecordDataset(filenames)
    print(raw_dataset)

    for raw_record in raw_dataset.take(10):
        print(repr(raw_record))

    # Create a description of the features.
    feature_description = {
        'feature0': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'feature1': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'feature2': tf.io.FixedLenFeature([], tf.string, default_value=''),
        'feature3': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
    }

    def _parse_function(example_proto):
        # Parse the input `tf.Example` proto using the dictionary above.
        return tf.io.parse_single_example(example_proto, feature_description)

    parsed_dataset = raw_dataset.map(_parse_function)
    print(parsed_dataset)

    for parsed_record in parsed_dataset.take(10):
        print(repr(parsed_record))

    # Write the `tf.Example` observations to the file.
    with tf.io.TFRecordWriter(filename) as writer:
        for i in range(n_observations):
            example = serialize_example(feature0[i], feature1[i], feature2[i], feature3[i])
            writer.write(example)

    filenames = [filename]
    raw_dataset = tf.data.TFRecordDataset(filenames)
    print(raw_dataset)

    for raw_record in raw_dataset.take(1):
        example = tf.train.Example()
        example.ParseFromString(raw_record.numpy())
        print(example)

    cat_in_snow = tf.keras.utils.get_file('320px-Felis_catus-cat_on_snow.jpg', 'https://storage.googleapis.com/download.tensorflow.org/example_images/320px-Felis_catus-cat_on_snow.jpg')
    williamsburg_bridge = tf.keras.utils.get_file('194px-New_East_River_Bridge_from_Brooklyn_det.4a09796u.jpg','https://storage.googleapis.com/download.tensorflow.org/example_images/194px-New_East_River_Bridge_from_Brooklyn_det.4a09796u.jpg')

    image_labels = {
        cat_in_snow: 0,
        williamsburg_bridge: 1,
    }

    # This is an example, just using the cat image.
    image_string = open(cat_in_snow, 'rb').read()

    label = image_labels[cat_in_snow]

    # Create a dictionary with features that may be relevant.
    def image_example(image_string, label):
        image_shape = tf.image.decode_jpeg(image_string).shape

        feature = {
            'height': _int64_feature(image_shape[0]),
            'width': _int64_feature(image_shape[1]),
            'depth': _int64_feature(image_shape[2]),
            'label': _int64_feature(label),
            'image_raw': _bytes_feature(image_string),
        }

        return tf.train.Example(features=tf.train.Features(feature=feature))

    for line in str(image_example(image_string, label)).split('\n')[:15]:
        print(line)
    print('...')

    # Write the raw image files to `images.tfrecords`.
    # First, process the two images into `tf.Example` messages.
    # Then, write to a `.tfrecords` file.
    record_file = os.path.join(job_path, "data/learning-python/tfrecord/tf-example/images.tfrecords")
    with tf.io.TFRecordWriter(record_file) as writer:
        for filename, label in image_labels.items():
            image_string = open(filename, 'rb').read()
            tf_example = image_example(image_string, label)
            writer.write(tf_example.SerializeToString())

    raw_image_dataset = tf.data.TFRecordDataset(record_file)

    # Create a dictionary describing the features.
    image_feature_description = {
        'height': tf.io.FixedLenFeature([], tf.int64),
        'width': tf.io.FixedLenFeature([], tf.int64),
        'depth': tf.io.FixedLenFeature([], tf.int64),
        'label': tf.io.FixedLenFeature([], tf.int64),
        'image_raw': tf.io.FixedLenFeature([], tf.string),
    }

    def _parse_image_function(example_proto):
        # Parse the input tf.Example proto using the dictionary above.
        return tf.io.parse_single_example(example_proto, image_feature_description)

    parsed_image_dataset = raw_image_dataset.map(_parse_image_function)
    print(parsed_image_dataset)

    for image_features in parsed_image_dataset:
        image_raw = image_features['image_raw'].numpy()
        display.display(display.Image(data=image_raw))


if __name__ == "__main__":
    main()
