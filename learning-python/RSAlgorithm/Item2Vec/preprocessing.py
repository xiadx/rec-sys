#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""preprocessing ml-100k dataset"""


import os
from collections import defaultdict


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


def get_train_data(input_file, output_file):
    """
    get train data for item2vec model
    Args:
        input_file: input file path
        output_file: output file path
    """
    if not os.path.exists(input_file):
        return
    records = defaultdict(lambda: [])
    with open(input_file, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            if float(rating) >= 3.0:
                records[userid].append(itemid)
    with open(output_file, "w") as fp:
        for userid, items in records.items():
            fp.write(" ".join(items) + "\n")


def main():
    input_file = "../dataset/ml-100k/u.data"
    output_file = "train.data"
    get_train_data(input_file, output_file)


if __name__ == "__main__":
    main()
