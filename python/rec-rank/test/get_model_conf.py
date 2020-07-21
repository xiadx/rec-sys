def main():
    features = [
        "al_static_score",
        "als_ui_cosine",
        "doubleFlow_article_click_1",
        "doubleFlow_article_click_3",
        "doubleFlow_article_click_30",
        "doubleFlow_article_click_7",
        "doubleFlow_article_ctr_1_v2",
        "doubleFlow_article_ctr_30_v2",
        "doubleFlow_article_ctr_3_v2",
        "doubleFlow_article_ctr_7_v2",
        "doubleFlow_article_view_1",
        "doubleFlow_article_view_3",
        "doubleFlow_article_view_30",
        "doubleFlow_article_view_7",
        "doubleFlow_user_click_1",
        "doubleFlow_user_click_3",
        "doubleFlow_user_click_30",
        "doubleFlow_user_click_7",
        "doubleFlow_user_view_1",
        "doubleFlow_user_view_3",
        "doubleFlow_user_view_30",
        "doubleFlow_user_view_7",
        "favorite",
        "item_duration",
        "item_type",
        "lat",
        "lng",
        "music",
        "pv",
        "reply",
        "tags_ui_cosine_30_v1",
        "timeFlag",
        "ui_cosine_90",
        "vote"
    ]
    for f in features:
        name = f
        alias = ""
        column = "numeric_column"
        dtype = "tf.int64"
        shape = 1
        default_value = 0
        scope = "wide"
        print("{\n\tname=\"%s\"\n\talias=\"%s\"\n\tcolumn=\"%s\"\n\tdtype=\"%s\"\n\t"
              "shape=%d\n\tdefault_value=%d\n\tscope=\"%s\"\n}" %
              (name, alias, column, dtype, shape, default_value, scope))


if __name__ == "__main__":
    # main()
    import tensorflow as tf
    import inspect
    import re
    # pattern = re.compile(r"\w{0,2}[int|float]\d{1,2}")
    # pattern = re.compile(r"\w{0,2}int\d{1,2}|\w{0,2}float\d{1,2}")
    # for k, v in inspect.getmembers(tf):
    #     if pattern.match(k):
    #         print(k)
    #         print(v)
    print(id(getattr(tf.feature_column, "numeric_column")("a")))
    print(id(getattr(tf.feature_column, "numeric_column")("a")))
    print(id(tf.feature_column.numeric_column("a")))
    print(id(tf.feature_column.numeric_column("a")))
