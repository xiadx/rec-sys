#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""lr model train"""


from __future__ import division
import math
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


def train(path, coef, model):
    """
    lr model train
    Args:
        path: file path
        coef: model coef file
        model: model file
    """
    t = load(path, np.int32, ",")
    y_train = t[:, 0]
    X_train = t[:, 1:]

    lr = LRCV(Cs=[1], penalty="l2", tol=0.0001, max_iter=500, cv=5, scoring="roc_auc").fit(X_train, y_train)

    with open(coef, "wb") as fp:
        fp.write(str(lr.intercept_[0])+",")
        fp.write(",".join([str(_) for _ in lr.coef_[0]]))

    joblib.dump(lr, model)


def test(path, coef, model):
    """
    lr model test
    Args:
        path: file path
        coef: model coef file
        model: model file
    """
    t = load(path, np.int32, ",")
    y_test = t[:, 0]
    X_test = t[:, 1:]

    lr = joblib.load(model)
    model_predict = [x[1] for x in lr.predict_proba(X_test)]
    c = load(coef, np.float64, ",")
    intercept = c[0]
    coefs = c[1:]
    coef_predict = np.frompyfunc(sigmoid, 1, 1)(np.dot(X_test, coefs)+intercept)

    print auc(y_test, model_predict)
    print auc(y_test, coef_predict)
    print acc(y_test, model_predict)
    print acc(y_test, coef_predict)


def main():
    train("train.data", "coef.data", "model.data")
    test("test.data", "coef.data", "model.data")


if __name__ == "__main__":
    main()
