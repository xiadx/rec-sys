import os
from pyhocon import ConfigFactory


class ConfigUtil(object):

    conf_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../conf"))

    resources_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../rec-recall/src/main/resources"))

    hnsw_conf = ConfigFactory.parse_file(os.path.join(conf_path, "hnsw.conf"))

    word2vec_conf = ConfigFactory.parse_file(os.path.join(resources_path, "word2vec.conf"))

    hive_conf = ConfigFactory.parse_file(os.path.join(conf_path, "hive.conf"))


def main():
    print(os.getcwd())
    print(os.path.dirname(__file__))


if __name__ == "__main__":
    main()
