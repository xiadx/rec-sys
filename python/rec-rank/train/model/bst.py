import os
import xgboost as xgb

job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
sample_path = os.path.join(job_path, "data/rec-rank/sample")
train_file = os.path.join(sample_path, "sample.rec.lr.v1.train.shuf")
test_file = os.path.join(sample_path, "sample.rec.lr.v1.test.shuf")


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
    p = sum([x[1] for x in l])
    n = len(l) - p
    t = sum([x[0] for x in l if x[1] == 1])
    # n, p, t = 0, 0, 0
    # for i, y, r in l:
    #     if y == 0:
    #         n += 1
    #     else:
    #         p += 1
    #         t += i
    return (t-(p*(p+1)/2)) / (n*p)

param = {
    "objective": "binary:logistic",
    "eta": 0.3,
    "max_depth": 4,
    "min_child_weight": 1,
    "gamma": 0,
    "subsample": 0.8,
    "colsample_bytree": 0.8,
    "scale_pos_weight": 1,
    "silent": True
}
num_boost_round = 5
dtrain = xgb.DMatrix(train_file)
bst = xgb.train(param, dtrain, num_boost_round=num_boost_round)

dtest = xgb.DMatrix(test_file)
print(dtest.num_row())
print(dtest.num_col())
print(dtest.feature_names)

y_pred = bst.predict(dtest)
y_true = []
with open(test_file, "r") as fp:
    for line in fp:
        y_true.append(float(line.split(" ")[0]))

print(y_pred)
print(y_true)
print(auc(y_true, y_pred))

from sklearn.metrics import roc_auc_score
print(roc_auc_score(y_true, y_pred))
