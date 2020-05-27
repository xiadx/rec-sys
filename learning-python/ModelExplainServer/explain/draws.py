import matplotlib.pyplot as plt
import seaborn as sns
import xgboost as xgb
import shap
import shutil

from .datasets import *


plt.style.use("seaborn")


def feature_importance_plot(model_file, featmap_file, importance_type, k):
    model = xgb.Booster(model_file=model_file)

    plt.figure(figsize=(10, 8))
    plt.xticks(fontsize=10)
    plt.yticks(fontsize=10)
    plt.xlabel("importance", fontsize=10)
    plt.ylabel("feature", fontsize=10)

    feature_importance = pd.DataFrame(
        [(x[0], x[1]) for x in model.get_score(importance_type=importance_type, fmap=featmap_file).items()],
        columns=["feature", "importance"])
    feature_importance.sort_values(by=["importance"], ascending=False, inplace=True)
    sns.barplot(x="importance", y="feature", data=feature_importance[:k], color="dodgerblue")

    html = plt_image_to_html(plt)

    plt.clf()

    return html


def trees_graphviz_plot(model_file, featmap_file):
    model = xgb.Booster(model_file=model_file)
    model_name = model_file.split("/")[-1]

    dot = xgb.to_graphviz(model, fmap=featmap_file)

    dot.render(filename=model_name, directory="dot", format="png")

    with open("dot/" + model_name + ".png", "rb") as fp:
        tmpfile = fp.read()

    encoded = base64.b64encode(tmpfile).decode(encoding="utf-8")
    html = '<div style="text-align: center;"><img src="data:image/png;base64,{}"></div>'.format(encoded)

    shutil.rmtree("dot")

    return html


def shap_explain_plot_margin_outline(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values(df, shap_values, feature_columns, select_columns)

    plot_html = shap.force_plot(shap_explainer.expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def shap_explain_plot_probability_outline(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values(df, shap_values, feature_columns, select_columns)

    plot_html = shap.force_plot(shap_explainer.expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def shap_explain_plot_margin_summary(model_file, libsvm_file, featmap_file, k, plot_type, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values(df, shap_values, feature_columns, select_columns)

    shap.summary_plot(select_shap_values, features=select_df, plot_type=plot_type, max_display=k)

    html = plt_image_to_html(plt)

    plt.clf()

    return html


def shap_explain_plot_probability_summary(model_file, libsvm_file, featmap_file, k, plot_type, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values(df, shap_values, feature_columns, select_columns)

    shap.summary_plot(select_shap_values, features=select_df, plot_type=plot_type, max_display=k)

    html = plt_image_to_html(plt)

    plt.clf()

    return html


def shap_explain_plot_margin_negative_single(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_margin_negative_single(model,
                                                                                           df,
                                                                                           shap_values,
                                                                                           shap_expected_value,
                                                                                           feature_columns,
                                                                                           select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def shap_explain_plot_probability_negative_single(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_probability_negative_single(model,
                                                                                                df,
                                                                                                shap_values,
                                                                                                shap_expected_value,
                                                                                                feature_columns,
                                                                                                select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def shap_explain_plot_margin_positive_single(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_margin_positive_single(model,
                                                                                           df,
                                                                                           shap_values,
                                                                                           shap_expected_value,
                                                                                           feature_columns,
                                                                                           select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def shap_explain_plot_probability_positive_single(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_probability_negative_single(model,
                                                                                                df,
                                                                                                shap_values,
                                                                                                shap_expected_value,
                                                                                                feature_columns,
                                                                                                select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def shap_explain_plot_margin_sample_single(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    plots = []

    select_df, select_shap_values = select_features_and_shap_values_margin_negative_single(model,
                                                                                           df,
                                                                                           shap_values,
                                                                                           shap_expected_value,
                                                                                           feature_columns,
                                                                                           select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    plots.append(plot_html)

    select_df, select_shap_values = select_features_and_shap_values_margin_positive_single(model,
                                                                                           df,
                                                                                           shap_values,
                                                                                           shap_expected_value,
                                                                                           feature_columns,
                                                                                           select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    plots.append(plot_html)

    html = plots_to_html(plots)

    return html


def shap_explain_plot_probability_sample_single(model_file, libsvm_file, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    plots = []

    select_df, select_shap_values = select_features_and_shap_values_probability_negative_single(model,
                                                                                                df,
                                                                                                shap_values,
                                                                                                shap_expected_value,
                                                                                                feature_columns,
                                                                                                select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    plots.append(plot_html)

    select_df, select_shap_values = select_features_and_shap_values_probability_positive_single(model,
                                                                                                df,
                                                                                                shap_values,
                                                                                                shap_expected_value,
                                                                                                feature_columns,
                                                                                                select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    plots.append(plot_html)

    html = plots_to_html(plots)

    return html


def shap_explain_plot_margin_negative_single_bar(model_file, libsvm_file, featmap_file, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_margin_negative_single(model,
                                                                                           df,
                                                                                           shap_values,
                                                                                           shap_expected_value,
                                                                                           feature_columns,
                                                                                           select_columns)

    predict_value = model.predict(xgb.DMatrix(select_df.values.reshape(1, -1), feature_names=feature_columns),
                                  output_margin=True)

    html = shap_to_html(plt, sns, select_df, select_shap_values, shap_expected_value, predict_value[0])

    plt.clf()

    return html


def shap_explain_plot_probability_negative_single_bar(model_file, libsvm_file, featmap_file, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_probability_negative_single(model,
                                                                                                df,
                                                                                                shap_values,
                                                                                                shap_expected_value,
                                                                                                feature_columns,
                                                                                                select_columns)

    predict_value = model.predict(xgb.DMatrix(select_df.values.reshape(1, -1), feature_names=feature_columns),
                                  output_margin=False)

    html = shap_to_html(plt, sns, select_df, select_shap_values, shap_expected_value, predict_value[0])

    plt.clf()

    return html


def shap_explain_plot_margin_positive_single_bar(model_file, libsvm_file, featmap_file, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_margin_positive_single(model,
                                                                                           df,
                                                                                           shap_values,
                                                                                           shap_expected_value,
                                                                                           feature_columns,
                                                                                           select_columns)

    predict_value = model.predict(xgb.DMatrix(select_df.values.reshape(1, -1), feature_names=feature_columns),
                                  output_margin=True)

    html = shap_to_html(plt, sns, select_df, select_shap_values, shap_expected_value, predict_value[0])

    plt.clf()

    return html


def shap_explain_plot_probability_positive_single_bar(model_file, libsvm_file, featmap_file, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = libsvm_to_dataframe(libsvm_file, feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_probability_positive_single(model,
                                                                                                df,
                                                                                                shap_values,
                                                                                                shap_expected_value,
                                                                                                feature_columns,
                                                                                                select_columns)

    predict_value = model.predict(xgb.DMatrix(select_df.values.reshape(1, -1), feature_names=feature_columns),
                                  output_margin=False)

    html = shap_to_html(plt, sns, select_df, select_shap_values, shap_expected_value, predict_value[0])

    plt.clf()

    return html


def shap_explain_plot_margin_single_feature_bar(model_file, features, featmap_file, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = pd.DataFrame([[features[c] for c in feature_columns]], columns=feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_margin_single_feature_bar(df,
                                                                                              shap_values,
                                                                                              feature_columns,
                                                                                              select_columns)

    predict_value = model.predict(xgb.DMatrix(df.values, feature_names=feature_columns),
                                  output_margin=True)

    html = shap_to_html(plt, sns, select_df, select_shap_values, shap_expected_value, predict_value[0])

    plt.clf()

    return html


def shap_explain_plot_probability_single_feature_bar(model_file, features, featmap_file, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = pd.DataFrame([[features[c] for c in feature_columns]], columns=feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_probability_single_feature_bar(df,
                                                                                                   shap_values,
                                                                                                   feature_columns,
                                                                                                   select_columns)

    predict_value = model.predict(xgb.DMatrix(df.values, feature_names=feature_columns),
                                  output_margin=False)

    html = shap_to_html(plt, sns, select_df, select_shap_values, shap_expected_value, predict_value[0])

    plt.clf()

    return html


def shap_explain_plot_margin_single_feature(model_file, features, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = pd.DataFrame([[features[c] for c in feature_columns]], columns=feature_columns)

    shap_explainer = shap.TreeExplainer(model)
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_margin_single_feature(df,
                                                                                          shap_values,
                                                                                          feature_columns,
                                                                                          select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def shap_explain_plot_probability_single_feature(model_file, features, featmap_file, link, select_columns):
    model = xgb.Booster(model_file=model_file)
    feature_columns = get_feature_columns(featmap_file)

    df = pd.DataFrame([[features[c] for c in feature_columns]], columns=feature_columns)

    shap_explainer = shap.TreeExplainer(model, df[feature_columns],
                                        model_output="probability",
                                        feature_dependence="independent")
    shap_expected_value = shap_explainer.expected_value
    shap_values = shap_explainer.shap_values(df[feature_columns])

    select_df, select_shap_values = select_features_and_shap_values_probability_single_feature(df,
                                                                                               shap_values,
                                                                                               feature_columns,
                                                                                               select_columns)

    plot_html = shap.force_plot(shap_expected_value,
                                select_shap_values,
                                features=select_df,
                                link=link)

    html = plot_to_html(plot_html)

    return html


def main():
    pass


if __name__ == "__main__":
    main()
