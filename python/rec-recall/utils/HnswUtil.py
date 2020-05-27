import hnswlib
import numpy as np
from ConfigUtil import ConfigUtil


class HnswUtil(object):

    dim = ConfigUtil.hnsw_conf.get_int("dim")

    k = ConfigUtil.hnsw_conf.get_int("k")

    ef_construction = ConfigUtil.hnsw_conf.get_int("ef_construction")

    M = ConfigUtil.hnsw_conf.get_int("M")

    ef = ConfigUtil.hnsw_conf.get_int("ef")

    space = ConfigUtil.hnsw_conf.get_string("space")

    num_threads = ConfigUtil.hnsw_conf.get_int("num_threads")

    @staticmethod
    def load_vector(vector_path):
        """
        :param vector_path: vector path
        :return labels, datas, label2ids
        """
        labels, datas, label2ids = [], [], {}
        with open(vector_path, "r") as fp:
            for i, line in enumerate(fp):
                if i < 1000:
                    s = line.strip().replace("[", "").replace("]", "")
                    id = s.split(",")[0]
                    labels.append(i)
                    datas.append([float(_) for _ in s.split(",")[1:]])
                    label2ids[i] = id
                else:
                    break
        return np.array(labels), np.array(datas), label2ids

    @classmethod
    def init_index(cls, label, data):
        """
        :param label: label
        :param data: data
        :return p
        """
        num_elements = len(label)

        p = hnswlib.Index(space=cls.space, dim=cls.dim)  # possible options are l2, cosine or ip
        # Initing index - the maximum number of elements should be known beforehand
        p.init_index(max_elements=num_elements, ef_construction=cls.ef_construction, M=cls.M)
        # Element insertion (can be called several times):
        p.add_items(data, label, num_threads=cls.num_threads)
        # Controlling the recall by setting ef:
        p.set_ef(cls.ef)  # ef should always be > k

        return p

    @classmethod
    def knn_query(cls, p, data):
        """
        :param p:
        :param data:
        :return:
        """
        labels, distances = p.knn_query(data, k=cls.k, num_threads=cls.num_threads)

        return labels, distances

    @staticmethod
    def save_index(p, index_path):
        """
        :param p: p
        :param index_path: index path
        """
        p.save_index(index_path)


def main():
    pass


if __name__ == "__main__":
    main()
