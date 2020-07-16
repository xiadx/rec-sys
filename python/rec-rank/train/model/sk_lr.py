import os
import numpy as np


job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
sample_path = os.path.join(job_path, "data/rec-rank/sample")
train_file = os.path.join(sample_path, "sample.rec.lr.v1.train.shuf")
test_file = os.path.join(sample_path, "sample.rec.lr.v1.test.shuf")
model_file = os.path.join(job_path, "data/rec-rank/model/rec.lr.v1")
num_features = 474


def create_dataset(path):
    labels, features = [], []
    with open(path, "r") as fp:
        for l in fp:
            label = float(l.split(" ")[0])
            feature_dict = {int(f.split(":")[0]): float(f.split(":")[1]) for f in l.split(" ")[1:]}
            feature_list = [feature_dict[i] if i in feature_dict else 0 for i in range(num_features)]
            labels.append(label)
            features.append(np.array(feature_list))
    return np.array(features), np.array(labels)


X_train, y_train = create_dataset(train_file)
X_test, y_test = create_dataset(train_file)


from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import normalize
from sklearn.metrics import roc_auc_score

X_train = normalize(X_train)
X_test = normalize(X_test)

lr = LogisticRegression(C=0.001)

lr.fit(X_train, y_train)

y_pred = lr.predict(X_test)
y_true = y_test

print(roc_auc_score(y_true, y_pred))

