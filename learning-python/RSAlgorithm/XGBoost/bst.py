#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""xgboost model train"""


from __future__ import division
import xgboost as xgb
from preprocessing import *


def auc(y_true, y_pred):
    """
    calculate auc
    Args:
        y_true: label
        y_pred: predict
    Return:
        auc
    """
    l = [(i, t[0], t[1]) for i, t in enumerate(sorted(zip(y_true, y_pred), key=lambda x: x[1]))]
    # p = sum([x[1] for x in l])
    # n = len(l) - p
    # t = sum([x[0] for x in l if x[1] == 1])
    n, p, t = 0, 0, 0
    for i, y, r in l:
        if y == 0:
            n += 1
        else:
            p += 1
            t += i
    return (t-(p*(p+1)/2)) / (n*p)


def acc(y_true, y_pred):
    """
    calculate acc
    Args:
        y_true: label
        y_pred: predict
    Return:
        acc
    """
    return sum(1 for i in range(len(y_pred)) if int(y_pred[i] >= 0.5) == y_true[i]) / len(y_pred)


def train(path, param, model):
    """
    xgboost model train
    Args:
        path: file path
        param: model parameter
        model: model file
    """
    t = load(path, np.float64, ",")
    y_train = t[:, 0]
    X_train = t[:, 1:]
    dtrain = xgb.DMatrix(X_train, y_train)
    bst = xgb.train(param, dtrain)
    bst.save_model(model)


def test(path, model):
    """
    xgboost model test
    Args:
        path: file path
        model: model file
    """
    t = load(path, np.float64, ",")
    y_test = t[:, 0]
    X_test = t[:, 1:]
    dtest = xgb.DMatrix(X_test, y_test)
    bst = xgb.Booster(model_file=model)
    y_pred = bst.predict(dtest)

    print "+" * 50
    print acc(y_test, y_pred)
    print auc(y_test, y_pred)
    print "+" * 50


def main():
    train("train.data", {"num_round": 100, "max_depth": 7, "eta": 0.3}, "xgb.model")
    test("test.data", "xgb.model")


if __name__ == "__main__":
    main()
