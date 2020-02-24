#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""content based algorithm"""


from __future__ import division
import os
from collections import defaultdict
from preprocessing import *


def get_max_timestamp(path):
    """
    get max timestamp
    Args:
        path: file path
    Return:
        max timestamp
    """
    if not os.path.exists(path):
        return
    max_timestamp = 0
    with open(path, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            if float(timestamp) > max_timestamp:
                max_timestamp = float(timestamp)
    return max_timestamp


def get_time_score(max_timestamp, timestamp):
    """
    get time score
    Args:
        max_timestamp: max timestamp
        timestamp: timestamp
    Return:
        time score
    """
    return 1 / (1+((max_timestamp-timestamp)/24*60*60))


def get_user_profile(item_category, path):
    """
    get user profile
    Args:
        path: file path
        item_category: {itemid: {category: ratio}}
    Return:
        user_profile: {userid: [(category: score)]}
    """
    if not os.path.exists(path):
        return {}
    user_profile = defaultdict(lambda: defaultdict(lambda: 0))
    max_timestamp = get_max_timestamp("../dataset/ml-100k/u.data")
    with open(path, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            if float(rating) < 3.0:
                continue
            if itemid not in item_category:
                continue
            time_score = get_time_score(max_timestamp, float(timestamp))
            for category, ratio in item_category[itemid].items():
                score = float(rating) * time_score * item_category[itemid][category]
                user_profile[userid][category] += score

    for userid, categories in user_profile.items():
        total_score = 0
        for k, v in categories.items():
            total_score += v
        categories = {k: round(v/total_score, 3) for k, v in categories.items()}
        user_profile[userid] = sorted(categories.iteritems(), key=operator.itemgetter(1), reverse=True)

    return user_profile


def calculate_recommend_info(category_item, user_profile, user, k=10):
    """
    calculate content based recommend info
    Args:
        category_item: category item
        user_profile: user profile
        user: userid
        k: topk
    Return:
        recommend_info: [itemid1, itemid2]
    """
    recommend_info = []
    if user not in user_profile:
        return {}
    for category, score in user_profile[user]:
        numbers = int(k*score) + 1
        for itemid, score in category_item[category][:numbers]:
            recommend_info.append(itemid)
    return recommend_info


def cb_recommend(recommend_info, item_info, k=5):
    """
    content based recommend
    Args:
        recommend_info: recommend info
        k: topk
    Return:
         cb_recommend_info
    """
    cb_recommend_info = ""
    for itemid in recommend_info[:k]:
        title, genre = item_info[itemid]
        cb_recommend_info += "%s  &  %s  &  %s\n" % (itemid, title, genre)
    return cb_recommend_info


def main():
    path = "../dataset/ml-100k/u.data"
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    average_score_path = "../dataset/ml-100k/u.data"
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    average_score = get_average_score(average_score_path)
    item_category, category_item = get_item_category(average_score, item_info)
    user_profile = get_user_profile(item_category, path)
    recommend_info = calculate_recommend_info(category_item, user_profile, "27")
    print cb_recommend(recommend_info, item_info)


if __name__ == "__main__":
    main()
