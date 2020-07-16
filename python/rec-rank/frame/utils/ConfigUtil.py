import os
from pyhocon import ConfigFactory


class ConfigUtil(object):

    conf_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../conf"))

    db_conf = ConfigFactory.parse_file(os.path.join(conf_path, "db.conf"))

    spark_conf = ConfigFactory.parse_file(os.path.join(conf_path, "spark.conf"))


def main():
    print(os.getcwd())
    print(os.path.dirname(__file__))


if __name__ == "__main__":
    main()
