#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""calculate item similarity"""


import os
import numpy as np
from collections import defaultdict
import operator
from preprocessing import *


def load_item_vector(path):
    """
    Args:
        path: file path
    Return:
        {itemid: np.ndarray([v1, v2, v3])}
    """
    if not os.path.exists(path):
        return {}
    records = {}
    with open(path, "rb") as fp:
        for line in fp:
            items = line.strip().split()
            if len(items) < 129:
                continue
            if items[0] == "</s>":
                continue
            records[items[0]] = np.array([float(_) for _ in items[1:]])
    return records


def calculate_item_similarity(item_vector):
    """
    calculate item similarity
    Args:
        item_vector: {itemid: np.ndarray([v1, v2, v3])}
    Return:
        {itemid: [(itemid1, similarity), (itemid2, similarity)]}
    """
    item_similarity_dict = defaultdict(lambda: {})
    for itemid_i, v1 in item_vector.items():
        for itemid_j, v2 in item_vector.items():
            if itemid_i == itemid_j:
                continue
            similarity = np.dot(v1, v2) / (np.linalg.norm(v1)*np.linalg.norm(v2))
            item_similarity_dict[itemid_i][itemid_j] = similarity
        item_similarity_dict[itemid_i] = sorted(item_similarity_dict[itemid_i].iteritems(), key=operator.itemgetter(1), reverse=True)
    return item_similarity_dict


def calculate_recommend_info(item_similarity_dict, user_action, recent_action_number=3, k=5):
    """
    calculate recommend info
    Args:
        item_similarity_dict: {itemid1: [(itemid2, similarity), (itemid3, similarity)]}
        user_action: {userid1: [(itemid1, timestamp)], userid2: [(timeid2, timestamp)]}
        recent_action_number: recent action number default 3
        k: similarity topk default 5
    Return:
        recommend_info: {userid1: [itemid1, itemid2], userid2: [itemid3, itemid4]}
    """
    recommend_info = defaultdict(lambda: [])
    for userid, items in user_action.items():
        itemids = [itemid for itemid, timestamp in items]
        for itemid in itemids[:recent_action_number]:
            if itemid in item_similarity_dict:
                recommend_items = item_similarity_dict[itemid][:k]
                for recommend_itemid, similarity in recommend_items:
                    if recommend_itemid in recommend_info[userid] or recommend_itemid in itemids:
                        continue
                    recommend_info[userid].append(recommend_itemid)
    return recommend_info


def item2vec_recommend(recommend_info, item_info, user, k=5):
    """
    item2vec recommend
    Args:
        recommend_info: recommend info
        user: userid
        k: topk recommend
    """
    item2vec_recommend_info = ""
    for itemid in recommend_info[user][:k]:
        title, genre = item_info[itemid]
        item2vec_recommend_info += "%s  &  %s  &  %s\n" % (itemid, title, genre)
    return item2vec_recommend_info


def main():
    if not os.path.exists("train.data"):
        input_file = "../dataset/ml-100k/u.data"
        output_file = "train.data"
        get_train_data(input_file, output_file)
    user_action_path = "../dataset/ml-100k/u.data"
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    item_vector_path = "item_vector.data"
    if not os.path.exists(item_vector_path):
        os.system("./train.sh")
    user_action = get_user_action(user_action_path)
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    item_vector = load_item_vector(item_vector_path)
    item_similarity = calculate_item_similarity(item_vector)
    recommend_info = calculate_recommend_info(item_similarity, user_action)
    print item2vec_recommend(recommend_info, item_info, "1")


if __name__ == "__main__":
    main()
