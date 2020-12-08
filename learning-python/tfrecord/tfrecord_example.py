import os
import tensorflow as tf


def main():
    # Create a description of the features.
    feature_description = {
        'id': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'IntegerCol': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'LongCol': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'FloatCol': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'DoubleCol': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'VectorCol': tf.io.FixedLenFeature([2], tf.float32, default_value=[0.0, 0.0]),
        'StringCol': tf.io.FixedLenFeature([], tf.string, default_value=''),
    }

    def _parse_function(example_proto):
        # Parse the input `tf.Example` proto using the dictionary above.
        return tf.io.parse_single_example(example_proto, feature_description)

    job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../"))
    path = os.path.join(job_path, "data/learning-python/pyspark/tfrecord/tfrecord-example/test-output.tfrecord")
    filenames = [os.path.join(path, f) for f in os.listdir(path) if f.endswith(".tfrecord")]

    ds = tf.data.TFRecordDataset(filenames)

    for raw_record in ds.take(1):
        example = tf.train.Example()
        example.ParseFromString(raw_record.numpy())
        print(example)

    ds = tf.data.TFRecordDataset(filenames).map(_parse_function)

    for parsed_record in ds.take(10):
        print(parsed_record)


if __name__ == "__main__":
    main()
