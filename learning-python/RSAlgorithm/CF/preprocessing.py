#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""preprocessing ml-100k dataset"""


import os


def get_user_action(path):
    """
    get user action
    Args:
        path: file path
    Return:
        user_action: {userid1: [(itemid1, timestamp)], userid2: [(timeid2, timestamp)]}
    """
    if not os.path.exists(path):
        return {}
    user_action = {}
    with open(path, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            if userid not in user_action:
                user_action[userid] = []
            if float(rating) >= 3.0:
                user_action[userid].append((itemid, timestamp))
    return user_action


def get_item_action(path):
    """
    get item action
    Args:
        path: file path
    Return:
        item_action: {itemid1: [(userid1, timestamp)], itemid2: [(userid2, timestamp)]}
    """
    if not os.path.exists(path):
        return {}
    item_action = {}
    with open(path, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            if itemid not in item_action:
                item_action[itemid] = []
            if float(rating) >= 3.0:
                item_action[itemid].append((userid, timestamp))
    return item_action


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


def main():
    user_action_path = "../dataset/ml-100k/u.data"
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    user_action = get_user_action(user_action_path)
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    print user_action
    print genre_dict
    print item_info


if __name__ == "__main__":
    main()
