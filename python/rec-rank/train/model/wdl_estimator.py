import os
import tensorflow as tf


def get_tfrecord_files(dir):
    files = tf.io.gfile.listdir(dir)
    return [os.path.join(dir, f)for f in files if f.endswith(".tfrecord.gz")]


def feature_column():
    al_static_score = tf.feature_column.numeric_column("al_static_score")
    als_ui_cosine = tf.feature_column.numeric_column("als_ui_cosine")
    doubleFlow_article_click_1 = tf.feature_column.numeric_column("doubleFlow_article_click_1")
    doubleFlow_article_click_3 = tf.feature_column.numeric_column("doubleFlow_article_click_3")
    doubleFlow_article_click_30 = tf.feature_column.numeric_column("doubleFlow_article_click_30")
    doubleFlow_article_click_7 = tf.feature_column.numeric_column("doubleFlow_article_click_7")
    doubleFlow_article_ctr_1_v2 = tf.feature_column.numeric_column("doubleFlow_article_ctr_1_v2")
    doubleFlow_article_ctr_30_v2 = tf.feature_column.numeric_column("doubleFlow_article_ctr_30_v2")
    doubleFlow_article_ctr_3_v2 = tf.feature_column.numeric_column("doubleFlow_article_ctr_3_v2")
    doubleFlow_article_ctr_7_v2 = tf.feature_column.numeric_column("doubleFlow_article_ctr_7_v2")
    doubleFlow_article_view_1 = tf.feature_column.numeric_column("doubleFlow_article_view_1")
    doubleFlow_article_view_3 = tf.feature_column.numeric_column("doubleFlow_article_view_3")
    doubleFlow_article_view_30 = tf.feature_column.numeric_column("doubleFlow_article_view_30")
    doubleFlow_article_view_7 = tf.feature_column.numeric_column("doubleFlow_article_view_7")
    doubleFlow_user_click_1 = tf.feature_column.numeric_column("doubleFlow_user_click_1")
    doubleFlow_user_click_3 = tf.feature_column.numeric_column("doubleFlow_user_click_3")
    doubleFlow_user_click_30 = tf.feature_column.numeric_column("doubleFlow_user_click_30")
    doubleFlow_user_click_7 = tf.feature_column.numeric_column("doubleFlow_user_click_7")
    doubleFlow_user_view_1 = tf.feature_column.numeric_column("doubleFlow_user_view_1")
    doubleFlow_user_view_3 = tf.feature_column.numeric_column("doubleFlow_user_view_3")
    doubleFlow_user_view_30 = tf.feature_column.numeric_column("doubleFlow_user_view_30")
    doubleFlow_user_view_7 = tf.feature_column.numeric_column("doubleFlow_user_view_7")
    favorite = tf.feature_column.numeric_column("favorite")
    item_duration = tf.feature_column.numeric_column("item_duration")
    item_type = tf.feature_column.numeric_column("item_type")
    lat = tf.feature_column.numeric_column("lat")
    lng = tf.feature_column.numeric_column("lng")
    music = tf.feature_column.numeric_column("music")
    pv = tf.feature_column.numeric_column("pv")
    reply = tf.feature_column.numeric_column("reply")
    tags_ui_cosine_30_v1 = tf.feature_column.numeric_column("tags_ui_cosine_30_v1")
    timeFlag = tf.feature_column.numeric_column("timeFlag")
    ui_cosine_90 = tf.feature_column.numeric_column("ui_cosine_90")
    vote = tf.feature_column.numeric_column("vote")

    wide_columns = [al_static_score,
                    als_ui_cosine,
                    doubleFlow_article_click_1,
                    doubleFlow_article_click_3,
                    doubleFlow_article_click_30,
                    doubleFlow_article_click_7,
                    doubleFlow_article_ctr_1_v2,
                    doubleFlow_article_ctr_30_v2,
                    doubleFlow_article_ctr_3_v2,
                    doubleFlow_article_ctr_7_v2,
                    doubleFlow_article_view_1,
                    doubleFlow_article_view_3,
                    doubleFlow_article_view_30,
                    doubleFlow_article_view_7,
                    doubleFlow_user_click_1,
                    doubleFlow_user_click_3,
                    doubleFlow_user_click_30,
                    doubleFlow_user_click_7,
                    doubleFlow_user_view_1,
                    doubleFlow_user_view_3,
                    doubleFlow_user_view_30,
                    doubleFlow_user_view_7,
                    favorite,
                    item_duration,
                    item_type,
                    lat,
                    lng,
                    music,
                    pv,
                    reply,
                    tags_ui_cosine_30_v1,
                    timeFlag,
                    ui_cosine_90,
                    vote]
    deep_columns = [al_static_score,
                    als_ui_cosine,
                    doubleFlow_article_click_1,
                    doubleFlow_article_click_3,
                    doubleFlow_article_click_30,
                    doubleFlow_article_click_7,
                    doubleFlow_article_ctr_1_v2,
                    doubleFlow_article_ctr_30_v2,
                    doubleFlow_article_ctr_3_v2,
                    doubleFlow_article_ctr_7_v2,
                    doubleFlow_article_view_1,
                    doubleFlow_article_view_3,
                    doubleFlow_article_view_30,
                    doubleFlow_article_view_7,
                    doubleFlow_user_click_1,
                    doubleFlow_user_click_3,
                    doubleFlow_user_click_30,
                    doubleFlow_user_click_7,
                    doubleFlow_user_view_1,
                    doubleFlow_user_view_3,
                    doubleFlow_user_view_30,
                    doubleFlow_user_view_7,
                    favorite,
                    item_duration,
                    item_type,
                    lat,
                    lng,
                    music,
                    pv,
                    reply,
                    tags_ui_cosine_30_v1,
                    timeFlag,
                    ui_cosine_90,
                    vote]
    return wide_columns, deep_columns


def input(tfrecord_files, re_time, shuffle, batch_num, predict):
    feature_description = {
        'al_static_score': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'als_ui_cosine': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'doubleFlow_article_click_1': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_article_click_3': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_article_click_30': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_article_click_7': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_article_ctr_1_v2': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'doubleFlow_article_ctr_30_v2': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'doubleFlow_article_ctr_3_v2': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'doubleFlow_article_ctr_7_v2': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'doubleFlow_article_view_1': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_article_view_3': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_article_view_30': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_article_view_7': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_click_1': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_click_3': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_click_30': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_click_7': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_view_1': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_view_3': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_view_30': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'doubleFlow_user_view_7': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'favorite': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'item_duration': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'item_type': tf.io.FixedLenFeature([], tf.int64, default_value=-1),
        'label': tf.io.FixedLenFeature([], tf.int64, default_value=-1),
        'lat': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'lng': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'mdd_hot': tf.io.FixedLenFeature([], tf.string, default_value='0'),
        'music': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'pv': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'reply': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'tags_ui_cosine_30_v1': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'timeFlag': tf.io.FixedLenFeature([], tf.int64, default_value=0),
        'ui_cosine_90': tf.io.FixedLenFeature([], tf.float32, default_value=0.0),
        'vote': tf.io.FixedLenFeature([], tf.int64, default_value=0),
    }

    def _parse_function(example_proto):
        # Parse the input `tf.Example` proto using the dictionary above.
        example = tf.io.parse_single_example(example_proto, feature_description)
        label = example.pop("label")
        features = example
        return features, label

    def _parse_function_predict(example_proto):
        # Parse the input `tf.Example` proto using the dictionary above.
        example = tf.io.parse_single_example(example_proto, feature_description)
        example.pop("label")
        features = example
        return features

    data_set = tf.data.TFRecordDataset(tfrecord_files, compression_type="GZIP").take(20480)

    if shuffle:
        data_set = data_set.shuffle(buffer_size=300000)
    if predict:
        data_set = data_set.map(_parse_function_predict, num_parallel_calls=200)
    else:
        data_set = data_set.map(_parse_function, num_parallel_calls=200)

    data_set = data_set.repeat(re_time)
    data_set = data_set.batch(batch_num)

    return data_set.prefetch(buffer_size=5)


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
        linear_optimizer=tf.optimizers.Ftrl(0.1, l2_regularization_strength=1.0),
        dnn_feature_columns=deep_columns,
        dnn_optimizer=tf.optimizers.Adagrad(learning_rate=0.1),
        dnn_hidden_units=[128, 64, 32, 16]
    )
    feature_columns = wide_columns + deep_columns
    feature_spec = tf.feature_column.make_parse_example_spec(feature_columns)
    serving_input_fn = (tf.estimator.export.build_parsing_serving_input_receiver_fn(feature_spec))
    return model_es, serving_input_fn


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
    model_es.train(input_fn=lambda: input(train_file, 5, True, 1024, False))
    print(model_es.evaluate(input_fn=lambda: input(test_file, 1, False, 1024, False)))
    model_es.export_saved_model(model_export_folder, serving_input_fn)


def test(model_es, test_file):
    """
    model test
    Args:
        model_es: wide&deep estimator
        test_file: test file
    """
    result = model_es.predict(input_fn=lambda: input(test_file, 1, False, 1024, True))
    print(result)


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
    job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
    sample_path = os.path.join(job_path, "data/rec-rank/sample")
    train_path = os.path.join(sample_path, "sample.rec.wdl.v1.tfrecord.train/20200627")
    test_path = os.path.join(sample_path, "sample.rec.wdl.v1.tfrecord.test/20200628")
    train_tfrecord_files = get_tfrecord_files(train_path)
    test_tfrecord_files = get_tfrecord_files(test_path)
    run(train_tfrecord_files, test_tfrecord_files, "wide&deep", "wide&deep_export")


if __name__ == "__main__":
    main()
