#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""item cf algorithm"""


from __future__ import division
import math
import operator
from collections import defaultdict
from preprocessing import *


def base_contribute_score():
    """
    item cf base contribution score
    """
    return 1


def improve_one_contribute_score(n):
    """
    item cf improve contribute score version 1.0
    Args:
        n: user action numbers
    """
    return 1 / math.log10(1 + n)


def improve_two_contribute_score(delta_time):
    """
    item cf improve contribute score version 2.0
    Args:
        delta_time: itemid1 timestamp - timeid2 timestamp
    """
    return 1 / (1 + abs(delta_time) / (60 * 60 * 24))


def calculate_item_similarity(user_action):
    """
    Args:
        user_action: {userid1: [(itemid1, timestamp)], userid2: [(timeid2, timestamp)]}
    Return:
        item_similarity_dict: {itemid1: [(itemid2, similarity), (itemid3, similarity)]}
    """
    common_appear = defaultdict(lambda: defaultdict(lambda: 0))
    item_appear = defaultdict(lambda: 0)
    for userid, items in user_action.items():
        for i in range(0, len(items)):
            itemid_i = items[i][0]
            item_appear[itemid_i] += 1
            for j in range(i + 1, len(items)):
                itemid_j = items[j][0]
                common_appear[itemid_i][itemid_j] += base_contribute_score()
                common_appear[itemid_j][itemid_i] += base_contribute_score()

                # common_appear[itemid_i][itemid_j] += improve_one_contribute_score(len(items))
                # common_appear[itemid_j][itemid_i] += improve_one_contribute_score(len(items))

                # delta_time = float(items[i][1]) - float(items[j][1])
                # common_appear[itemid_i][itemid_j] += improve_two_contribute_score(delta_time)
                # common_appear[itemid_j][itemid_i] += improve_two_contribute_score(delta_time)

    item_similarity_dict = defaultdict(lambda: {})
    for itemid_i, common_items in common_appear.items():
        for itemid_j, common_time in common_items.items():
            similarity = common_time / math.sqrt(item_appear[itemid_i] * item_appear[itemid_j])
            item_similarity_dict[itemid_i][itemid_j] = similarity

    for itemid, common_items in item_similarity_dict.items():
        item_similarity_dict[itemid] = sorted(common_items.iteritems(), key=operator.itemgetter(1), reverse=True)

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
            recommend_items = item_similarity_dict[itemid][:k]
            for recommend_itemid, similarity in recommend_items:
                if recommend_itemid in recommend_info[userid] or recommend_itemid in itemids:
                    continue
                recommend_info[userid].append(recommend_itemid)
    return recommend_info


def itemcf_recommend(recommend_info, item_info, user, k=5):
    """
    itemcf recommend
    Args:
        recommend_info: recommend info
        user: userid
        k: topk recommend
    """
    itemcf_recommend_info = ""
    for itemid in recommend_info[user][:k]:
        title, genre = item_info[itemid]
        itemcf_recommend_info += "%s  &  %s  &  %s\n" % (itemid, title, genre)
    return itemcf_recommend_info


def main():
    user_action_path = "../dataset/ml-100k/u.data"
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    user_action = get_user_action(user_action_path)
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    item_similarity = calculate_item_similarity(user_action)
    recommend_info = calculate_recommend_info(item_similarity, user_action)
    print itemcf_recommend(recommend_info, item_info, "1")


if __name__ == "__main__":
    main()
