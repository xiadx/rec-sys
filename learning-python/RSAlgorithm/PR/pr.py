#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""personal rank algorithm"""


from __future__ import division
import operator
from preprocessing import *
from scipy.sparse.linalg import gmres


def calculate_recommend_info(graph, root, alpha, iters, k):
    """
    Args:
        graph: user item graph
        root: the fixed user for which to recommend
        alpha: the probability to go to random walk
        iters: iteration
        k: topk
    Return:
        {itemid: pr}
    """
    rank = {key: 0 for key in graph}
    rank[root] = 1
    for i in range(iters):
        temporary_rank = {key: 0 for key in graph}
        for out_key, out_value in graph.items():
            for inner_key in graph[out_key]:
                temporary_rank[inner_key] += alpha * rank[out_key] / len(out_value)
                if inner_key == root:
                    temporary_rank[inner_key] += 1 - alpha
        if temporary_rank == rank:
            break
        rank = temporary_rank
    rank = {key: value for key, value in rank.items() if key.find("item") != -1 and key not in graph[root]}
    recommend_info = sorted(rank.iteritems(), key=operator.itemgetter(1), reverse=True)[:k]
    return recommend_info


def pr_recommend(recommend_info, item_info):
    pr_recommend_info = ""
    for itemid, pr_value in recommend_info:
        title, genre = item_info[itemid]
        pr_recommend_info += "%s  &  %s  &  %s  &  %f\n" % (itemid, title, genre, pr_value)
    return pr_recommend_info


def calculate_matrix_recommend_info(graph, root, alpha, k):
    """
    Args:
        graph: user item graph
        root: the fixed user for which to recommend
        alpha: the probability to random walk
        k: topk
    Return:
        {itemid: pr}
    """
    sparse_matrix, v, d = get_sparse_matrix(graph)
    if root not in d:
        return {}
    transform_matrix = get_transform_matrix(sparse_matrix, v, 0.8)
    i = d[root]
    r = [[0] if i != j else [1] for j in range(len(v))]
    r = np.array(r)
    prs = gmres(transform_matrix, r, tol=1e-8)[0]
    records = {v[i]: pr for i, pr in enumerate(prs) if v[i].find("item") != -1 and v[i] not in graph[root]}
    recommend_info = sorted(records.iteritems(), key=operator.itemgetter(1), reverse=True)[:k]
    return recommend_info


def main():
    genre_dict_path = "../dataset/ml-100k/u.genre"
    item_info_path = "../dataset/ml-100k/u.item"
    graph_data_path = "../dataset/ml-100k/u.data"
    genre_dict = get_genre_dict(genre_dict_path)
    item_info = get_item_info(item_info_path, genre_dict)
    graph_data = get_graph_data(graph_data_path)
    root, alpha, iters, k = "user_24", 0.8, 10, 10
    # recommend_info = calculate_recommend_info(graph_data, root, alpha, iters, k)
    recommend_info = calculate_matrix_recommend_info(graph_data, root, alpha, k)
    print pr_recommend(recommend_info, item_info)


if __name__ == "__main__":
    main()
