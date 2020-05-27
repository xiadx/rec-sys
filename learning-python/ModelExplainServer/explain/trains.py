from sklearn.datasets import load_svmlight_file
import pandas as pd
import xgboost as xgb

from .datasets import *


param = {
    "objective": "binary:logistic",
    "eta": 0.1,
    "max_depth": 7,
    "min_child_weight": 1,
    "gamma": 0,
    "subsample": 0.8,
    "colsample_bytree": 0.8,
    "scale_pos_weight": 1,
    "silent": True
}
num_boost_round = 300


def xgboost_train(train_libsvm_file, model_file, featmap_file):
    feature_columns = get_feature_columns(featmap_file)

    X, y = load_svmlight_file(train_libsvm_file, n_features=len(feature_columns), zero_based=True)

    df = pd.DataFrame(X.toarray())
    df.columns = feature_columns
    df["label"] = y
    df = df[feature_columns+["label"]]

    dtrain = xgb.DMatrix(df[feature_columns], label=df["label"])
    bst = xgb.train(param, dtrain, num_boost_round=num_boost_round)

    bst.save_model(model_file)


def main():
    pass


if __name__ == "__main__":
    main()
