#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""preprocessing ml-100k dataset"""


from __future__ import division
import os
from collections import defaultdict
import numpy as np
from scipy.sparse import coo_matrix


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
                item_info["item_"+itemid] = [title, genres]
    return item_info


def get_graph_data(path):
    """
    Args:
        path: file path
    Return:
        {userA: {itemb: 1, itemc: 1}, itemb: {UserA: 1}}
    """
    if not os.path.exists(path):
        return {}
    graph_data = defaultdict(lambda: {})
    with open(path, "rb") as fp:
        for line in fp:
            userid, itemid, rating, timestamp = line.strip().split()
            if float(rating) >= 3.0:
                graph_data["user_"+userid]["item_"+itemid] = 1
                graph_data["item_"+itemid]["user_"+userid] = 1
    return graph_data


def get_sparse_matrix(graph):
    """
    Args:
        graph: user item graph
    Return:
        coo_matrix sparse matrix
    """
    v = graph.keys()
    d = {e: i for i, e in enumerate(v)}
    rows, cols, data = [], [], []
    for ei in graph:
        w = 1 / len(graph[ei])
        r = d[ei]
        for ej in graph[ei]:
            c = d[ej]
            rows.append(r)
            cols.append(c)
            data.append(w)
    rows, cols, data = np.array(rows), np.array(cols), np.array(data)
    sparse_matrix = coo_matrix((data, (rows, cols)), shape=(len(v), len(v)))
    return sparse_matrix, v, d


def get_transform_matrix(sparse_matrix, v, alpha):
    """
    get E - alpha * sparse_matrix.T
    Args:
        sparse_matrix: sparse matrix
        v: total item and user vertex
        alpha: the probability for random walk
    Return:
        coo_matrix sparse matrix
    """
    rows, cols, data = range(len(v)), range(len(v)), [1] * len(v)
    rows, cols, data = np.array(rows), np.array(cols), np.array(data)
    E = coo_matrix((data, (rows, cols)), shape=(len(v), len(v)))
    return E.tocsr() - alpha*sparse_matrix.tocsr().transpose()


def main():
    graph_data_path = "../dataset/ml-100k/u.data"
    graph_data = get_graph_data(graph_data_path)
    sparse_matrix, v, d = get_sparse_matrix(graph_data)
    print get_transform_matrix(sparse_matrix, v, 0.8)


if __name__ == "__main__":
    main()
