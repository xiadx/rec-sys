import os
from pyhocon import ConfigFactory


class ConfigUtil(object):

    conf_path = os.path.abspath(os.path.join(os.getcwd(), "../conf"))

    resources_path = os.path.abspath(os.path.join(os.getcwd(), "../../../rec-recall/src/main/resources"))

    hnsw_conf = ConfigFactory.parse_file(os.path.join(conf_path, "hnsw.conf"))

    word2vec_conf = ConfigFactory.parse_file(os.path.join(resources_path, "word2vec.conf"))


def main():
    pass


if __name__ == "__main__":
    main()
