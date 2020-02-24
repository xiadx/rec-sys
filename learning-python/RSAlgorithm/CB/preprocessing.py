#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""preprocessing ml-100k dataset"""


from __future__ import division
import os
from collections import defaultdict
import operator


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


def get_item_category(average_score, item_info):
    """
    Args:
        average_score: {itemid: average_score}
        item_info: {itemid: [title, genre]}
    Return:
        {itemid: {category: ratio}}
        {category: [(itemid1, score), (itemid2, score), (itemid3, score)]}
    """
    item_category = defaultdict(lambda: {})
    for itemid, info in item_info.items():
        categories = info[1].split("|")
        ratio = 1 / len(categories)
        for category in categories:
            item_category[itemid][category] = ratio

    category_item = defaultdict(lambda: [])
    for itemid, categories in item_category.items():
        score = average_score[itemid]
        for category, ratio in categories.items():
            category_item[category].append((itemid, score))
    for category, items in category_item.items():
        category_item[category] = sorted(items, key=operator.itemgetter(1), reverse=True)

    return item_category, category_item


def main():
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    average_score_path = "../dataset/ml-100k/u.data"
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    average_score = get_average_score(average_score_path)
    records = get_item_category(average_score, item_info)
    print records[0]
    print records[1]


if __name__ == "__main__":
    main()
