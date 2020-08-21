def main():
    features = [
        "doubleFlow_article_ctr_30_v2",
        "doubleFlow_article_ctr_7_v2",
        "doubleFlow_weng_user_view_30",
        "u_tr",
        "recall=basicitemcf",
        "doubleFlow_user_click_30",
        "action_client_sign30d",
        "doubleFlow_weng_user_click_30",
        "doubleFlow_article_ctr_3_v2",
        "doubleFlow_user_view_30"
    ]
    for f in features:
        name = f
        alias = ""
        column = "numeric_column"
        if name.find("ctr") != -1 or name.find("cosine") != -1 or name.find("score") != -1:
            dtype = "tf.float32"
            default_value = 0.0
        else:
            dtype = "tf.int64"
            default_value = 0
        shape = 1
        scope = "wide"
        print("{\n\tname=\"%s\"\n\talias=\"%s\"\n\tcolumn=\"%s\"\n\tdtype=\"%s\"\n\t"
              "shape=%d\n\tdefault_value=%d\n\tscope=\"%s\"\n}" %
              (name, alias, column, dtype, shape, default_value, scope))


if __name__ == "__main__":
    main()
