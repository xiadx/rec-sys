#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""gbdt&lr model train"""


from __future__ import division
import math
import xgboost as xgb
from sklearn.linear_model import LogisticRegressionCV as LRCV
from sklearn.externals import joblib
from preprocessing import *


def sigmoid(x):
    """
    sigmoid function
    Args:
        x: a value
    Return:
        1. / (1.+math.exp(-x))
    """
    return 1. / (1.+math.exp(-x))


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
    y = [1 if x >= 0.5 else 0 for x in y_pred]
    l = [1 if t[0] == t[1] else 0 for t in zip(y_true, y)]
    return sum(l) / len(l)


def train(path, param, gbdt_model, coef, lr_model):
    """
    gbdt&lr model train
    Args:
        path: file path
        param: model parameter
        gbdt_model: gbdt model file
        coef: lr coef file
        lr_model: lr model file
    """
    t = load(path, np.float64, ",")
    y_train = t[:, 0]
    X_train = t[:, 1:]

    dtrain = xgb.DMatrix(X_train, y_train)
    bst = xgb.train(param, dtrain)
    bst.save_model(gbdt_model)

    num_leaf = 2 ** (param["max_depth"]+1)
    trans = bst.predict(dtrain, pred_leaf=True)

    X_lr_train = []
    for t in trans:
        f = []
        for x in t:
            f += [1 if x == i else 0 for i in range(num_leaf)]
        X_lr_train.append(f)

    X_lr_train = np.array(X_lr_train)
    y_lr_train = y_train

    lr = LRCV(Cs=[1], penalty="l2", tol=0.0001, max_iter=500, cv=5, scoring="roc_auc").fit(X_lr_train, y_lr_train)

    with open(coef, "wb") as fp:
        fp.write(str(lr.intercept_[0]) + ",")
        fp.write(",".join([str(_) for _ in lr.coef_[0]]))

    joblib.dump(lr, lr_model)


def test(path, gbdt_model, coef, lr_model, max_depth):
    """
    gbdt&lr model test
    Args:
        path: file path
        gbdt_model: gbdt model file
        coef: lr coef file
        lr_model: lr model file
        max_depth: max depth
    """
    t = load(path, np.float64, ",")
    y_test = t[:, 0]
    X_test = t[:, 1:]
    dtest = xgb.DMatrix(X_test, y_test)
    bst = xgb.Booster(model_file=gbdt_model)

    num_leaf = 2 ** (max_depth + 1)
    trans = bst.predict(dtest, pred_leaf=True)

    X_lr_test = []
    for t in trans:
        f = []
        for x in t:
            f += [1 if x == i else 0 for i in range(num_leaf)]
        X_lr_test.append(f)
    y_lr_test = y_test

    lr = joblib.load(lr_model)
    model_predict = [x[1] for x in lr.predict_proba(X_lr_test)]
    c = load(coef, np.float64, ",")
    intercept = c[0]
    coefs = c[1:]
    coef_predict = np.frompyfunc(sigmoid, 1, 1)(np.dot(X_lr_test, coefs) + intercept)

    print auc(y_lr_test, model_predict)
    print auc(y_lr_test, coef_predict)
    print acc(y_lr_test, model_predict)
    print acc(y_lr_test, coef_predict)


def main():
    param = {"num_round": 20, "max_depth": 6, "eta": 0.3}
    train("train.data", param, "xgb.model", "coef.data", "lr.model")
    test("test.data", "xgb.model", "coef.data", "lr.model", param["max_depth"])


if __name__ == "__main__":
    main()
