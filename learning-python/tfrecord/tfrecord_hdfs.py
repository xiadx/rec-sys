import tensorflow as tf

dataset = tf.data.TFRecordDataset([
    "hdfs:///user/xiadinxin/rec-sys/python/rec-rank/sample/sample.rec.wdl.v1.tfrecord.train/20200708/part-00195-9dade2bc-53e4-4c33-ad4b-1adf018b8bcb-c000.tfrecord.gz"
])

for raw_record in dataset.take(1):
    print(raw_record)
