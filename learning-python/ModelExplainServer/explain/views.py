from django.http import HttpResponse

import json

from .draws import *
from .datasets import *


def index(request):
    return HttpResponse("model explain.")


def feature_importance(request):
    scene = request.GET.get("scene", default="onTravel")
    model_name = request.GET.get("model", default="onTravelV6Z")
    importance_type = request.GET.get("type", default="total_gain")
    k = int(request.GET.get("k", default="20"))

    model_file = MODEL_PATH + "/" + scene + "/" + model_name
    featmap_file = FEATMAP_PATH + "/" + scene + "/" + model_name

    html = feature_importance_plot(model_file, featmap_file, importance_type, k)

    return HttpResponse(html)


def trees_graphviz(request):
    scene = request.GET.get("scene", default="onTravel")
    model_name = request.GET.get("model", default="onTravelV6Z")

    model_file = MODEL_PATH + "/" + scene + "/" + model_name
    featmap_file = FEATMAP_PATH + "/" + scene + "/" + model_name

    html = trees_graphviz_plot(model_file, featmap_file)

    return HttpResponse(html)


def shap_explain_outline(request):
    scene = request.GET.get("scene", default="onTravel")
    model_name = request.GET.get("model", default="onTravelV6Z")
    sample_method = request.GET.get("sample", default="imbalance")
    test_sample_number = int(request.GET.get("n", default="1000"))
    train_sample_number= int(request.GET.get("m", default="5000"))
    explain_method = request.GET.get("method", default="margin")
    retrain = request.GET.get("retrain", default="false")
    link = request.GET.get("link", default="identity")
    columns = json.loads(request.GET.get("columns", default="[]"))

    (featmap_file,
     test_data_file,
     train_data_file,
     sample_test_data_file,
     sample_train_data_file,
     model_file) = prepare(scene,
                           model_name,
                           sample_method,
                           test_sample_number,
                           train_sample_number,
                           retrain)

    html = None
    if explain_method == "margin":
        html = shap_explain_plot_margin_outline(model_file, sample_test_data_file, featmap_file, link, columns)
    if explain_method == "probability":
        html = shap_explain_plot_probability_outline(model_file, sample_test_data_file, featmap_file, link, columns)

    return HttpResponse(html)


def shap_explain_summary(request):
    scene = request.GET.get("scene", default="onTravel")
    model_name = request.GET.get("model", default="onTravelV6Z")
    sample_method = request.GET.get("sample", default="imbalance")
    test_sample_number = int(request.GET.get("n", default="1000"))
    train_sample_number = int(request.GET.get("m", default="5000"))
    explain_method = request.GET.get("method", default="margin")
    retrain = request.GET.get("retrain", default="false")
    k = int(request.GET.get("k", default="20"))
    plot = request.GET.get("plot", default="dot")
    columns = json.loads(request.GET.get("columns", default="[]"))

    (featmap_file,
     test_data_file,
     train_data_file,
     sample_test_data_file,
     sample_train_data_file,
     model_file) = prepare(scene,
                           model_name,
                           sample_method,
                           test_sample_number,
                           train_sample_number,
                           retrain)

    html = None
    if explain_method == "margin":
        html = shap_explain_plot_margin_summary(model_file, sample_test_data_file, featmap_file, k, plot, columns)
    if explain_method == "probability":
        html = shap_explain_plot_probability_summary(model_file, sample_test_data_file, featmap_file, k, plot, columns)

    return HttpResponse(html)


def shap_explain_single(request):
    scene = request.GET.get("scene", default="onTravel")
    model_name = request.GET.get("model", default="onTravelV6Z")
    sample_method = request.GET.get("sample", default="imbalance")
    test_sample_number = int(request.GET.get("n", default="1000"))
    train_sample_number = int(request.GET.get("m", default="5000"))
    explain_method = request.GET.get("method", default="margin")
    retrain = request.GET.get("retrain", default="false")
    link = request.GET.get("link", default="identity")
    type = request.GET.get("type", default="sample")
    plot = request.GET.get("plot", default="line")
    columns = json.loads(request.GET.get("columns", default="[]"))
    features = json.loads(request.GET.get("features", default="{}"))

    (featmap_file,
     test_data_file,
     train_data_file,
     sample_test_data_file,
     sample_train_data_file,
     model_file) = prepare(scene,
                           model_name,
                           sample_method,
                           test_sample_number,
                           train_sample_number,
                           retrain)

    html = None
    if explain_method == "margin":

        if plot == "line":

            if type == "negative":
                html = shap_explain_plot_margin_negative_single(model_file,
                                                                sample_test_data_file,
                                                                featmap_file,
                                                                link,
                                                                columns)

            if type == "positive":
                html = shap_explain_plot_margin_positive_single(model_file,
                                                                sample_test_data_file,
                                                                featmap_file,
                                                                link,
                                                                columns)

            if type == "sample" and not features:
                html = shap_explain_plot_margin_sample_single(model_file,
                                                              sample_test_data_file,
                                                              featmap_file,
                                                              link,
                                                              columns)

            if type == "sample" and features:
                html = shap_explain_plot_margin_single_feature(model_file,
                                                               features,
                                                               featmap_file,
                                                               link,
                                                               columns)

        if plot == "bar":

            if type == "negative":
                html = shap_explain_plot_margin_negative_single_bar(model_file,
                                                                    sample_test_data_file,
                                                                    featmap_file,
                                                                    columns)

            if type == "positive":
                html = shap_explain_plot_margin_positive_single_bar(model_file,
                                                                    sample_test_data_file,
                                                                    featmap_file,
                                                                    columns)

            if type == "sample" and not features:
                pass

            if type == "sample" and features:
                html = shap_explain_plot_margin_single_feature_bar(model_file,
                                                                   features,
                                                                   featmap_file,
                                                                   columns)

    if explain_method == "probability":

        if plot == "line":

            if type == "negative":
                html = shap_explain_plot_probability_negative_single(model_file,
                                                                     sample_test_data_file,
                                                                     featmap_file,
                                                                     link,
                                                                     columns)

            if type == "positive":
                html = shap_explain_plot_probability_positive_single(model_file,
                                                                     sample_test_data_file,
                                                                     featmap_file,
                                                                     link,
                                                                     columns)

            if type == "sample" and not features:
                html = shap_explain_plot_probability_sample_single(model_file,
                                                                   sample_test_data_file,
                                                                   featmap_file,
                                                                   link,
                                                                   columns)

            if type == "sample" and features:
                html = shap_explain_plot_probability_single_feature(model_file,
                                                                    features,
                                                                    featmap_file,
                                                                    link,
                                                                    columns)

        if plot == "bar":

            if type == "negative":
                html = shap_explain_plot_probability_negative_single_bar(model_file,
                                                                         sample_test_data_file,
                                                                         featmap_file,
                                                                         columns)

            if type == "positive":
                html = shap_explain_plot_probability_positive_single_bar(model_file,
                                                                         sample_test_data_file,
                                                                         featmap_file,
                                                                         columns)

            if type == "sample" and not features:
                pass

            if type == "sample" and features:
                html = shap_explain_plot_probability_single_feature_bar(model_file,
                                                                        features,
                                                                        featmap_file,
                                                                        columns)

    return HttpResponse(html)


def lime_explain(request):
    return HttpResponse("lime explain")
