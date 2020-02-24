#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""preprocessing ml-100k dataset"""


import os
import operator
from collections import defaultdict


def get_genre_dict(path):
    """
    get genre dict
    Args:
        path: file path
    Return:
        genre_dict: {0: "unknown", 1: "Action"}
    """
    genre_dict = {}
    with open(path, "rb") as fp:
        for line in fp:
            if line.strip():
                genres = line.strip().split("|")
                genre_dict[int(genres[1])] = genres[0]
    return genre_dict


def get_item_info(path, genre_dict):
    """
    get item info
    Args:
        path: file path
        genre_dict: genre dict
    Return:
        item_info: {itemid: [title, genre]}
    """
    item_info = {}
    with open(path, "rb") as fp:
        for line in fp:
            if line.strip():
                items = line.strip().split("|")
                itemid = items[0]
                title = items[1]
                genres = []
                for i, c in enumerate(items[-19:]):
                    if c == "1":
                        genres.append(genre_dict[i])
                genres = "|".join(genres)
                item_info[itemid] = [title, genres]
    return item_info


def get_average_score(path):
    """
    get average score
    Args:
        path: file path
    Return:
        average_scoe: {itemid: average_score}
    """
    if not os.path.exists(path):
        return {}
    record = defaultdict(lambda: [0, 0])
    with open(path, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            record[itemid][0] += 1
            record[itemid][1] += float(rating)
    average_score = {}
    for k, v in record.items():
        average_score[k] = round(v[1]/v[0], 3)
    return average_score


def get_train_data(path):
    """
    get train data for LFM model
    Args:
        path: file path
    Return:
        train_data: [(userid1, itemid1, label), (userid2, itemid2, label)]
    """
    if not os.path.exists(path):
        return []
    average_score = get_average_score(path)
    positive, negtive = defaultdict(lambda: []), defaultdict(lambda: [])
    with open(path, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            if float(rating) >= 3.0:
                positive[userid].append((itemid, 1))
            else:
                negtive[userid].append((itemid, average_score[userid]))

    train_data = []
    for userid, items in positive.items():
        positive_numbers = len(items)
        negtive_numbers = len(negtive[userid])
        if positive_numbers == 0 or negtive_numbers == 0:
            continue
        if positive_numbers > negtive_numbers:
            for itemid, label in items[:negtive_numbers]:
                train_data.append((userid, itemid, label))
            for itemid, score in negtive[userid]:
                train_data.append((userid, itemid, 0))
        else:
            for itemid, label in items:
                train_data.append((userid, itemid, label))
            sample_items = sorted(negtive[userid], key=operator.itemgetter(1))[:positive_numbers]
            for itemid, score in sample_items:
                train_data.append((userid, itemid, 0))

    return train_data


def main():
    path = "../dataset/ml-100k/u.data"
    print get_train_data(path)


if __name__ == "__main__":
    main()
