#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""preprocessing adult dataset"""


import numpy as np
import pandas as pd


def train_test_data_df(train_data_file, test_data_file):
    """
    get train test data dataframe
    Args:
        train_data_file: train data file
        test_data_file: test data file
    Return:
        train_data: pd.DataFrame
        test_data: pd.DataFrame
    """
    dtype_dict = {
        "age": np.int32,
        "education-num": np.int32,
        "capital-gain": np.int32,
        "capital-loss": np.int32,
        "hours-per-week": np.int32
    }
    cols = [i for i in range(15) if i != 2]
    train_data = pd.read_csv(train_data_file, sep=", ", header=0, dtype=dtype_dict, na_values="?", usecols=cols)
    train_data = train_data.dropna(axis=0, how="any")
    test_data = pd.read_csv(test_data_file, sep=", ", header=0, dtype=dtype_dict, na_values="?", usecols=cols)
    test_data = test_data.dropna(axis=0, how="any")
    return train_data, test_data


def deal_cross_feature(fields, df):
    """
    deal cross feature
    Args:
        fields: [[field1, field2], [field3, field4, field5]]
        df: pd.DataFrame
    Return:
        df: pd.DafaFrame
    """
    for f in fields:
        df["_".join(f)] = df.apply(lambda r: "_".join([r[x] for x in f]), axis=1)
    return df


def deal_label(field, df):
    """
    deal label
    Args:
        field: label field name
        df: pd.DataFrame
    Return:
        df: pd.DataFrame
    """
    labels = []

    def deal_raw(raw_data):
        """
        deal one raw data of pd.DataFrame
        Args:
            raw_data: pd.Series one raw data of pd.DataFrame
        """
        if raw_data[field].find("<=50K") != -1:
            labels.append(0)
        else:
            labels.append(1)

    df.apply(deal_raw, axis=1)
    df["label"] = labels
    df = df.drop(field, axis=1)
    return df


def deal_discrete_feature(fields, df):
    """
    deal discrete feature
    Args:
        fields: discrete feature field list
        df: pd.DataFrame
    Return:
        discrete_feature_df: pd.DataFrame
    """
    discrete_feature_df = pd.DataFrame()
    for field in fields:
        categorys = df[field].value_counts().to_dict().keys()
        category_dict = {category: i for i, category in enumerate(categorys)}
        category_length = len(categorys)
        discrete_feature_dict = {field+"_"+str(_): [] for _ in range(category_length)}
        for category in df[field]:
            c = category_dict[category]
            one_hot = [1 if c == i else 0 for i in range(category_length)]
            for i, e in enumerate(one_hot):
                discrete_feature_dict[field+"_"+str(i)].append(e)
        discrete_feature_df = pd.concat([discrete_feature_df, pd.DataFrame(discrete_feature_dict)], axis=1)
    df = pd.concat([df, discrete_feature_df], axis=1).drop(fields, axis=1)
    return df


def deal_continuous_feature(fields, df):
    """
    deal continuous feature
    Args:
        fields: continuous feature field list
        df: pd.DataFrame
    Return:
        df: pd.DataFrame
    """
    continuous_feature_df = pd.DataFrame()
    for field in fields:
        describe_dict = df[field].describe().to_dict()
        category_range = [
            describe_dict["min"],
            describe_dict["25%"],
            describe_dict["50%"],
            describe_dict["75%"],
            describe_dict["max"]
        ]
        continuous_feature_dict = {field + "_" + str(_): [] for _ in range(4)}
        for value in df[field]:
            c = -1
            if category_range[0] <= value < category_range[1]:
                c = 0
            elif category_range[1] <= value < category_range[2]:
                c = 1
            elif category_range[2] <= value < category_range[3]:
                c = 2
            else:
                c = 3
            one_hot = [1 if c == i else 0 for i in range(4)]
            for i, e in enumerate(one_hot):
                continuous_feature_dict[field+"_"+str(i)].append(e)
        continuous_feature_df = pd.concat([continuous_feature_df, pd.DataFrame(continuous_feature_dict)], axis=1)
    df = pd.concat([df, continuous_feature_df], axis=1).drop(fields, axis=1)
    return df


def split_train_test_data(total_data_df, frac):
    """
    split train test data
    Args:
        total_data_df: total data dataframe
        frac: test data frac
    Return:
        train_data_df: train data dataframe
        test_data_df: test data dataframe
    """
    test_data_df = total_data_df.sample(frac=frac, random_state=1)
    train_data_df = total_data_df.loc[total_data_df.index.difference(test_data_df.index)]
    return train_data_df, test_data_df


def save(df, path, label, sep):
    """
    save
    Args
        df: pd.DataFrame
        path: file path
        label: label column
        sep: separator
    """
    cols = [label] + [c for c in df.columns if c != label]
    df[cols].to_csv(path, sep=sep, header=False, index=False)


def load(path, dtype, sep):
    """
    load
    Args:
        path: file path
        dtype: dtype
        sep: separator
        cols: user columns
    Return:
        np.ndarray
    """
    return np.genfromtxt(path, dtype=dtype, delimiter=sep)


def main():
    train_data_file = "../dataset/adult/adult.data"
    test_data_file = "../dataset/adult/adult.test"
    train_data, test_data = train_test_data_df(train_data_file, test_data_file)
    total_data = pd.concat([train_data, test_data]).reset_index(drop=True)
    total_data = deal_label("class", total_data)
    fields = [["occupation", "race"], ["occupation", "sex"]]
    total_data = deal_cross_feature(fields, total_data)
    fields = ["workclass", "education", "marital-status", "occupation",
              "relationship", "race", "sex", "native-country",
              "occupation_race", "occupation_sex"]
    total_data = deal_discrete_feature(fields, total_data)
    fields = ["age", "education-num", "capital-gain", "capital-loss", "hours-per-week"]
    total_data = deal_continuous_feature(fields, total_data)
    train_data, test_data = split_train_test_data(total_data, 0.3)
    save(train_data, "train.data", "label", ",")
    save(test_data, "test.data", "label", ",")


if __name__ == "__main__":
    main()
