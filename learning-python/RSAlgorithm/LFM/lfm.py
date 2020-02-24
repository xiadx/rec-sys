#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""lfm model train"""


import numpy as np
from preprocessing import *


def init(F):
    """
    Args:
        F: vector size
    Return:
        np.ndarray
    """
    return np.random.randn(F)


def predict(user_vector, item_vector):
    """
    user vector and item vector distance
    Args:
        user_vector: user vector
        item_vector: item vector
    Return:
        user vector and item vector similarity
    """
    return np.dot(user_vector, item_vector) / (np.linalg.norm(user_vector)*np.linalg.norm(item_vector))


def train(train_data, F, alpha, beta, step):
    """
    Args:
        train_data: train data for lfm
        F: user vector size, item vector size
        alpha: regularization factor
        beta: learning rate
        step: iteration
    Return:
        {itemid: np.ndarray([v1, v2, v3])}
        {userid: np.ndarray([v1, v2, v3])}
    """
    user_vector, item_vector = {}, {}
    for s in range(step):
        for instance in train_data:
            userid, itemid, label = instance
            if userid not in user_vector:
                user_vector[userid] = init(F)
            if itemid not in item_vector:
                item_vector[itemid] = init(F)
            delta = label - predict(user_vector[userid], item_vector[itemid])
            for i in range(F):
                user_vector[userid][i] += beta * (delta*item_vector[itemid][i] - alpha * user_vector[userid][i])
                item_vector[itemid][i] += beta * (delta*user_vector[userid][i] - alpha * item_vector[itemid][i])
                beta *= 0.9
    return user_vector, item_vector


def lfm_recommend(user_vector, item_vector, item_info, user, k):
    """
    lfm recommend
    Args:
        user_vector: user vector
        item_vector: item vector
        item_info: item info
        user: userid
        k: topk
    Return:
        [(itemid1, score), (itemid2, score)]
    """
    if user not in user_vector:
        return []
    records = {}
    v1 = user_vector[user]
    for itemid in item_vector:
        v2 = item_vector[itemid]
        records[itemid] = np.dot(v1, v2) / (np.linalg.norm(v1)*np.linalg.norm(v2))
    lfm_recommend_info = ""
    for itemid, score in sorted(records.iteritems(), key=operator.itemgetter(1), reverse=True)[:k]:
        title, genre = item_info[itemid]
        lfm_recommend_info += "%s  &  %s  &  %s  &  %f\n" % (itemid, title, genre, score)
    return lfm_recommend_info


def main():
    train_data_path = "../dataset/ml-100k/u.data"
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    train_data = get_train_data(train_data_path)
    F, alpha, beta, step = 50, 0.01, 0.1, 10
    user_vector, item_vector = train(train_data, F, alpha, beta, step)
    print lfm_recommend(user_vector, item_vector, item_info, "24", 10)


if __name__ == "__main__":
    main()
