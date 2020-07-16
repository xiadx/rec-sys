import os
from pyhocon import ConfigFactory


class DSSMConfigUtil(object):

    conf_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../conf"))

    action_conf = ConfigFactory.parse_file(os.path.join(conf_path, "action.conf"))

    sample_conf = ConfigFactory.parse_file(os.path.join(conf_path, "sample.conf"))

    item_conf = ConfigFactory.parse_file(os.path.join(conf_path, "item-profile.conf"))


def main():
    print(os.getcwd())
    print(os.path.dirname(__file__))


if __name__ == "__main__":
    main()
