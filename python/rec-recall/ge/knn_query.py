import os
import sys
import datetime

sys.path.append(os.path.abspath("../utils"))

from ConfigUtil import ConfigUtil
from HnswUtil import HnswUtil


def main():
    if len(sys.argv) == 1:
        dt = (datetime.date.today() + datetime.timedelta(days=-1)).strftime("%Y%m%d")
    elif len(sys.argv) == 2:
        dt = sys.argv[1]
    else:
        print("parameter error")
        sys.exit(1)

    vector_path = ConfigUtil.word2vec_conf.get_string("vector-path")
    vector_dat = os.path.abspath(os.path.join(os.getcwd(), "../../../data/rec-recall" + vector_path.split("rec-recall")[1]))

    labels, datas, label2ids = HnswUtil.load_vector(vector_dat.replace("${dt}", dt))
    p = HnswUtil.init_index(labels, datas)
    ql, qd = HnswUtil.knn_query(p, datas)

    print(ql)
    print(qd)


if __name__ == "__main__":
    main()
