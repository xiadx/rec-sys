#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""user cf algorithm"""


from __future__ import division
import math
import operator
from collections import defaultdict
from preprocessing import *


def base_contribute_score():
    """
    user cf base contribution score
    """
    return 1


def improve_one_contribute_score(n):
    """
    user cf improve contribute score version 1.0
    Args:
        n: item action numbers
    """
    return 1 / math.log10(1 + n)


def improve_two_contribute_score(delta_time):
    """
    user cf improve contribute score version 2.0
    Args:
        delta_time: userid1 timestamp - userid2 timestamp
    """
    return 1 / (1 + abs(delta_time) / (60 * 60 * 24))


def calculate_user_similarity(item_action):
    """
    Args:
        item_action: {itemid1: [(userid1, timestamp)], itemid2: [(userid2, timestamp)]}
    Return:
        user_similarity_dict: {userid1: [(userid2, similarity), (userid3, similarity)]}
    """
    common_appear = defaultdict(lambda: defaultdict(lambda: 0))
    user_appear = defaultdict(lambda: 0)
    for itemid, users in item_action.items():
        for i in range(0, len(users)):
            userid_i = users[i][0]
            user_appear[userid_i] += 1
            for j in range(i + 1, len(users)):
                userid_j = users[j][0]
                common_appear[userid_i][userid_j] += base_contribute_score()
                common_appear[userid_j][userid_i] += base_contribute_score()

                # common_appear[userid_i][userid_j] += improve_one_contribute_score(len(users))
                # common_appear[userid_j][userid_i] += improve_one_contribute_score(len(users))

                # delta_time = float(users[i][1]) - float(users[j][1])
                # common_appear[userid_i][userid_j] += improve_two_contribute_score(delta_time)
                # common_appear[userid_j][userid_i] += improve_two_contribute_score(delta_time)

    user_similarity_dict = defaultdict(lambda: {})
    for userid_i, common_users in common_appear.items():
        for itemid_j, common_time in common_users.items():
            similarity = common_time / math.sqrt(user_appear[userid_i] * user_appear[userid_j])
            user_similarity_dict[userid_i][userid_j] = similarity

    for userid, common_users in user_similarity_dict.items():
        user_similarity_dict[userid] = sorted(common_users.iteritems(), key=operator.itemgetter(1), reverse=True)

    return user_similarity_dict


def calculate_recommend_info(user_similarity_dict, user_action, recent_action_number=3, k=5):
    """
    calculate recommend info
    Args:
        user_similarity_dict: {userid1: [(userid2, similarity), (userid3, similarity)]}
        user_action: {userid1: [(itemid1, timestamp)], userid2: [(timeid2, timestamp)]}
        recent_action_number: recent action number default 3
        k: similarity topk default 5
    """
    recommend_info = defaultdict(lambda: [])
    for userid, items in user_action.items():
        itemids = [itemid for itemid, timestamp in items]
        for similarity_userid, similarity in user_similarity_dict[userid][:k]:
            recommend_items = user_action[similarity_userid]
            recommend_itemids = [itemid for itemid, timestamp in recommend_items]
            filter_itemids = []
            for itemid in recommend_itemids:
                if itemid in itemids or itemid in recommend_info[userid]:
                    continue
                filter_itemids.append(itemid)
            for itemid in filter_itemids[:recent_action_number]:
                recommend_info[userid].append(itemid)
    return recommend_info


def usercf_recommend(recommend_info, item_info, user, k=5):
    """
    usercf recommend
    Args:
        recommend_info: recommend info
        user: userid
        k: topk recommend
    """
    usercf_recommend_info = ""
    for itemid in recommend_info[user][:k]:
        title, genre = item_info[itemid]
        usercf_recommend_info += "%s  &  %s  &  %s\n" % (itemid, title, genre)
    return usercf_recommend_info


def main():
    user_action_path = "../dataset/ml-100k/u.data"
    item_action_path = "../dataset/ml-100k/u.data"
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    user_action = get_user_action(user_action_path)
    item_action = get_item_action(item_action_path)
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    user_similarity = calculate_user_similarity(item_action)
    recommend_info = calculate_recommend_info(user_similarity, user_action)
    print usercf_recommend(recommend_info, item_info, "1")


if __name__ == "__main__":
    main()
