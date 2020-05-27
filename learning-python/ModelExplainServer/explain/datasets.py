import re
import os
import base64
import io
from io import BytesIO

from sklearn.datasets import load_svmlight_file
import pandas as pd
import numpy as np
import xgboost as xgb

from .trains import xgboost_train


MODEL_PATH = "/home/xiadinxin/Workspace/MFWProjects/model"
FEATMAP_PATH = "/home/xiadinxin/Workspace/MFWProjects/feature-map"
DATA_PATH = "/home/xiadinxin/Workspace/MFWProjects/train-data"


def get_feature_columns(featmap_file):
    """get feature columns"""
    with open(featmap_file) as fp:
        cols = []
        for line in fp:
            cols.append(re.search(r"\t(.*)\t", line.strip()).group(1))
    return cols


def balance_sample_libsvm(input_file_path, output_file_path, n):
    """balance sample for the libsvm file"""
    input_file_dirname = os.path.dirname(input_file_path)
    input_file_name = os.path.basename(input_file_path)
    shuf_input_file = input_file_dirname + "/shuf_" + input_file_name

    cmd = "shuf " + input_file_path + " -o " + shuf_input_file
    flag = os.system(cmd)
    if flag != 0:
        return False

    fr = open(shuf_input_file, "r")
    fw = open(output_file_path, "w")
    i, j, m = 0, 0, int(n / 2)
    for line in fr:
        label = float(line.strip().split()[0])
        if label == 0.0:
            if i < m:
                fw.write(line)
                i += 1
        else:
            if j < m:
                fw.write(line)
                j += 1
        if i >= m and j >= m:
            break
    fw.close()
    fr.close()

    return True


def imbalance_sample_libsvm(input_file_path, output_file_path, n):
    """imbalance sample for the libsvm file"""
    cmd = "shuf -n " + str(n) + " " + input_file_path + " -o " + output_file_path
    flag = os.system(cmd)
    if flag != 0:
        return False
    return True


def libsvm_to_dataframe(libsvm_file, feature_columns):
    """libsvm to dataframe"""
    X, y = load_svmlight_file(libsvm_file,
                              n_features=len(feature_columns),
                              zero_based=True)
    df = pd.DataFrame(X.toarray())
    df.columns = feature_columns
    df["label"] = y
    df = df[feature_columns + ["label"]]
    return df


def select_features_and_shap_values(df, shap_values, feature_columns, select_columns):
    """select features and shap values"""
    if not select_columns:
        return df[feature_columns], shap_values
    else:
        feature_columns_dict = {col: i for i, col in enumerate(df[feature_columns].columns)}
        columns_indexs = [feature_columns_dict[c] for c in select_columns]
        select_df = df.iloc[:, columns_indexs]
        select_shap_values = shap_values[:, columns_indexs]
        return select_df, select_shap_values


def select_features_and_shap_values_single(df, shap_values, feature_columns, select_columns, i):
    if not select_columns:
        return df[feature_columns].iloc[i], shap_values[i]
    else:
        feature_columns_dict = {col: i for i, col in enumerate(df[feature_columns].columns)}
        columns_indexs = [feature_columns_dict[c] for c in select_columns]
        select_df = df.iloc[i, columns_indexs]
        select_shap_values = shap_values[i, columns_indexs]
        return select_df, select_shap_values


def select_features_and_shap_values_single_only(df, shap_values, feature_columns, select_columns):
    if not select_columns:
        return df[feature_columns].iloc[0], shap_values[0]
    else:
        feature_columns_dict = {col: i for i, col in enumerate(df[feature_columns].columns)}
        columns_indexs = [feature_columns_dict[c] for c in select_columns]
        select_df = df.iloc[0, columns_indexs]
        select_shap_values = shap_values[0, columns_indexs]
        return select_df, select_shap_values


def select_features_and_shap_values_margin_negative_single(model,
                                                           df,
                                                           shap_values,
                                                           shap_expected_value,
                                                           feature_columns,
                                                           select_columns):

    df["pred"] = model.predict(xgb.DMatrix(df[feature_columns], label=df["label"]), output_margin=True)
    i = np.random.choice(df[df["pred"] < shap_expected_value].index.tolist())
    return select_features_and_shap_values_single(df, shap_values, feature_columns, select_columns, i)


def select_features_and_shap_values_probability_negative_single(model,
                                                                df,
                                                                shap_values,
                                                                shap_expected_value,
                                                                feature_columns,
                                                                select_columns):

    df["pred"] = model.predict(xgb.DMatrix(df[feature_columns], label=df["label"]), output_margin=False)
    i = np.random.choice(df[df["pred"] < shap_expected_value].index.tolist())
    return select_features_and_shap_values_single(df, shap_values, feature_columns, select_columns, i)


def select_features_and_shap_values_margin_positive_single(model,
                                                           df,
                                                           shap_values,
                                                           shap_expected_value,
                                                           feature_columns,
                                                           select_columns):

    df["pred"] = model.predict(xgb.DMatrix(df[feature_columns], label=df["label"]), output_margin=True)
    j = np.random.choice(df[df["pred"] > shap_expected_value].index.tolist())
    return select_features_and_shap_values_single(df, shap_values, feature_columns, select_columns, j)


def select_features_and_shap_values_probability_positive_single(model,
                                                                df,
                                                                shap_values,
                                                                shap_expected_value,
                                                                feature_columns,
                                                                select_columns):

    df["pred"] = model.predict(xgb.DMatrix(df[feature_columns], label=df["label"]), output_margin=False)
    j = np.random.choice(df[df["pred"] > shap_expected_value].index.tolist())
    return select_features_and_shap_values_single(df, shap_values, feature_columns, select_columns, j)


def select_features_and_shap_values_margin_single_feature_bar(df,
                                                              shap_values,
                                                              feature_columns,
                                                              select_columns):

    return select_features_and_shap_values_single_only(df, shap_values, feature_columns, select_columns)


def select_features_and_shap_values_probability_single_feature_bar(df,
                                                                   shap_values,
                                                                   feature_columns,
                                                                   select_columns):

    return select_features_and_shap_values_single_only(df, shap_values, feature_columns, select_columns)


def select_features_and_shap_values_margin_single_feature(df,
                                                          shap_values,
                                                          feature_columns,
                                                          select_columns):

    return select_features_and_shap_values_single_only(df, shap_values, feature_columns, select_columns)


def select_features_and_shap_values_probability_single_feature(df,
                                                               shap_values,
                                                               feature_columns,
                                                               select_columns):

    return select_features_and_shap_values_single_only(df, shap_values, feature_columns, select_columns)


def plt_image_to_html(plt, f="png"):
    tmpfile = BytesIO()
    plt.savefig(tmpfile, format=f, bbox_inches="tight")
    encoded = base64.b64encode(tmpfile.getvalue()).decode(encoding="utf-8")
    html = '<div style="text-align: center;"><img src="data:image/' + f + ';base64,{}"></div>'.format(encoded)
    return html


def plot_to_html(plot_html):
    html = ""
    html += "<html><head><script>\n"
    # dump the js code
    bundle_path = os.path.join(os.path.split(__file__)[0], "resources", "bundle.js")
    with io.open(bundle_path, encoding="utf-8") as f:
        bundle_data = f.read()
    html += bundle_data
    html += "</script></head><body>\n"
    html += plot_html.data
    html += "</body></html>\n"
    return html


def plots_to_html(plots):
    html = ""
    html += "<html><head><script>\n"
    # dump the js code
    bundle_path = os.path.join(os.path.split(__file__)[0], "resources", "bundle.js")
    with io.open(bundle_path, encoding="utf-8") as f:
        bundle_data = f.read()
    html += bundle_data
    html += "</script></head><body>\n"
    for plot in plots:
        html += plot.data
    html += "</body></html>\n"
    return html


def shap_to_html(plt, sns, select_df, select_shap_values, shap_expected_value, predict_value):
    shap_df = pd.DataFrame([(select_df.index[i], round(select_shap_values[i], 6)) for i in range(len(select_df))],
                           columns=["Feature", "SHAP Value"])

    table_df = pd.DataFrame([(select_df.index[i],
                              round(select_df.values[i], 6),
                              round(select_shap_values[i], 6)) for i in range(len(select_df))],
                            columns=["Feature", "Value", "SHAP Value"])

    plt.figure(figsize=(20, 8))
    plt.xticks(fontsize=8, rotation=90)
    plt.yticks(fontsize=8)
    plt.xlabel("SHAP Value", fontsize=8)
    plt.ylabel("Feature", fontsize=8)

    title = "SHAP Expected Value: %s, Predict Value: %s." % (round(shap_expected_value, 6), round(predict_value, 6))

    sns.barplot(x="Feature", y="SHAP Value", data=shap_df)

    plt.table(cellText=np.hstack((table_df["Feature"].values.reshape(-1, 1),
                                  np.round(table_df[["Value", "SHAP Value"]].values, 6))), loc="top")

    html = '<div style="text-align: center;">{}</div>'.format(title) + plt_image_to_html(plt)

    return html


def prepare(scene,
            model_name,
            sample_method,
            test_sample_number,
            train_sample_number,
            retrain):

    featmap_file = FEATMAP_PATH + "/" + scene + "/" + model_name
    test_data_file = DATA_PATH + "/" + scene + "/test_" + model_name + ".txt"
    train_data_file = DATA_PATH + "/" + scene + "/train_" + model_name + ".txt"
    sample_test_data_file = DATA_PATH + "/" + scene + "/" + sample_method + "_sample_test_" + str(test_sample_number) + "_" + model_name + ".txt"
    sample_train_data_file = DATA_PATH + "/" + scene + "/" + sample_method + "_sample_train_" + str(train_sample_number) + "_" + model_name + ".txt"

    if sample_method == "imbalance":
        if not os.path.exists(sample_test_data_file):
            imbalance_sample_libsvm(test_data_file, sample_test_data_file, test_sample_number)
    if sample_method == "balance":
        if not os.path.exists(sample_test_data_file):
            balance_sample_libsvm(test_data_file, sample_test_data_file, test_sample_number)

    model_file = None
    if retrain == "false":
        model_file = MODEL_PATH + "/" + scene + "/" + model_name
    if retrain == "true":
        if sample_method == "imbalance":
            if not os.path.exists(sample_train_data_file):
                imbalance_sample_libsvm(train_data_file, sample_train_data_file, train_sample_number)
        if sample_method == "balance":
            if not os.path.exists(sample_train_data_file):
                balance_sample_libsvm(train_data_file, sample_train_data_file, train_sample_number)

        model_file = MODEL_PATH + "/" + scene + "/" + sample_method + "_retrain_" + train_sample_number + "_" + model_name
        if not os.path.exists(model_file):
            xgboost_train(sample_train_data_file, model_file, featmap_file)

    return (featmap_file,
            test_data_file,
            train_data_file,
            sample_test_data_file,
            sample_train_data_file,
            model_file)


def main():
    pass


if __name__ == "__main__":
    main()
