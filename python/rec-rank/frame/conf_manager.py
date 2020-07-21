import os
import sys
import json

sys.path.append(os.path.abspath("./utils"))
sys.path.append(os.path.abspath("./entity"))

from ConfigUtil import ConfigUtil
from JdbcUtil import JdbcUtil
from BaseFeature import BaseFeature
from OfflineFeature import OfflineFeature


class FeatureConf(object):

    def __init__(self, name):
        self.name = name
        self.feature_dict = dict()


class SparkConf(object):

    def __init__(self, name, conf):
        self.type = conf.get_string("type")
        self.package = conf.get_string("package")
        self.app = conf.get_string("app")
        self.param = conf.get_config("param").as_plain_ordered_dict()
        self.conf = conf.get_config("conf").as_plain_ordered_dict()
        self.arg = conf.get_config("arg").as_plain_ordered_dict()


class ModelConf(object):

    def __init__(self, name, conf):
        self.type = conf.get_string("type")
        self.package = conf.get_string("package")
        self.app = conf.get_string("app")
        self.arg = conf.get_config("arg").as_plain_ordered_dict()


class CmdConf(object):

    def __init__(self, name, conf):
        self.typ = conf.get_string("type")
        self.cmd = conf.get_string("cmd")
        self.arg = conf.get_config("arg").as_plain_ordered_dict()


class PipeConf(object):

    def __init__(self, name, conf):
        self.pipelines = conf.as_plain_ordered_dict()


class SubmitConf(object):

    def __init__(self, name, conf):
        self.hive = conf.get_string("hive")
        self.presto = conf.get_string("presto")
        self.hadoop = conf.get_string("hadoop")
        self.python = conf.get_string("python")
        self.spark = conf.get_string("spark")
        self.conf = conf.get_config("conf").as_plain_ordered_dict()
        self.param = conf.get_config("param").as_plain_ordered_dict()


class ConfManager(object):

    def __init__(self):
        self.mysql_conn = None

        self.feature_conf = {
            "basic": FeatureConf("basic"),
            "offline": FeatureConf("offline")
        }

        self.spark_conf = dict()

        self.model_conf = dict()

        self.cmd_conf = dict()

        self.pipe_conf = dict()

        self.submit_conf = dict()

    def load_spark_conf(self):
        for name in ConfigUtil.spark_conf.keys():
            conf = ConfigUtil.spark_conf.get_config(name)
            self.spark_conf[name] = SparkConf(name, conf)

    def load_model_conf(self):
        for name in ConfigUtil.model_conf.keys():
            conf = ConfigUtil.model_conf.get_config(name)
            self.model_conf[name] = ModelConf(name, conf)

    def load_cmd_conf(self):
        for name in ConfigUtil.cmd_conf.keys():
            conf = ConfigUtil.cmd_conf.get_config(name)
            self.cmd_conf[name] = CmdConf(name, conf)

    def load_pipe_conf(self):
        for name in ConfigUtil.pipe_conf.keys():
            conf = ConfigUtil.pipe_conf.get_config(name)
            self.pipe_conf[name] = PipeConf(name, conf)

    def load_submit_conf(self):
        for name in ConfigUtil.submit_conf.keys():
            conf = ConfigUtil.submit_conf.get_config(name)
            self.submit_conf[name] = SubmitConf(name, conf)

    def load_basic_feature(self):
        self.mysql_conn = JdbcUtil.get_connection()

        basic_feature_table = ConfigUtil.db_conf.get_string("feature.basic-feature-table")
        cursor = self.mysql_conn.cursor()
        cursor.execute("select * from %s" % basic_feature_table)
        basic_feature_info = cursor.fetchall()

        for feature in basic_feature_info:
            id = feature[0]
            feature_mark = feature[1]
            status = feature[2]
            feature_name = feature[3]
            feature_source = feature[4]
            expression = feature[5]
            feature_details = feature[6]
            ctime = feature[7]
            mtime = feature[8]
            online_status = feature[9]
            feature_type = feature[10]
            feature_value_type = feature[11]
            d_value_str = feature[12]
            d_value_type = feature[13]
            feature_scene = feature[14]

            self.feature_conf["basic"].feature_dict[feature_name] = BaseFeature(id,
                                                                                feature_mark,
                                                                                status,
                                                                                feature_name,
                                                                                feature_source,
                                                                                expression,
                                                                                feature_details,
                                                                                ctime,
                                                                                mtime,
                                                                                online_status,
                                                                                feature_type,
                                                                                feature_value_type,
                                                                                d_value_str,
                                                                                d_value_type,
                                                                                feature_scene)

        JdbcUtil.close(self.mysql_conn)

    def load_offline_feature(self):
        self.mysql_conn = JdbcUtil.get_connection()

        offline_feature_table = ConfigUtil.db_conf.get_string("feature.offline-feature-table")
        cursor = self.mysql_conn.cursor()
        cursor.execute("select * from %s" % offline_feature_table)
        offline_feature_info = cursor.fetchall()

        for feature in offline_feature_info:
            id = feature[0]
            feature_name = feature[1]
            default_value = feature[2]
            value_type = feature[3]
            feature_define = feature[4]
            expression = feature[5]
            details = feature[6]
            ctime = feature[7]
            mtime = feature[8]
            feature_scene = feature[9]
            feature_type = feature[10]
            feature_status = feature[11]

            self.feature_conf["offline"].feature_dict[feature_name] = OfflineFeature(id,
                                                                                     feature_name,
                                                                                     default_value,
                                                                                     value_type,
                                                                                     feature_define,
                                                                                     expression,
                                                                                     details,
                                                                                     ctime,
                                                                                     mtime,
                                                                                     feature_scene,
                                                                                     feature_type,
                                                                                     feature_status)

        JdbcUtil.close(self.mysql_conn)

    def check_basic_feature(self):
        feature_name_list = self.feature_conf["basic"].feature_dict.keys()
        feature_name_set = set(feature_name_list)
        return len(feature_name_list) == len(feature_name_set)

    def check_offline_feature(self):
        feature_name_list = self.feature_conf["offline"].feature_dict.keys()
        feature_name_set = set(feature_name_list)
        return len(feature_name_list) == len(feature_name_set)


def main():
    conf_mgr = ConfManager()
    # conf_mgr.load_basic_feature()
    # conf_mgr.load_offline_feature()

    conf_mgr.load_spark_conf()
    conf_mgr.load_cmd_conf()
    conf_mgr.load_pipe_conf()
    conf_mgr.load_submit_conf()

    # print(json.dumps(conf_mgr.spark_conf["dump-sample"].__dict__))
    # print(conf_mgr.cmd_conf["load-train-sample"].__dict__)
    print(conf_mgr.submit_conf["submit"].__dict__)


if __name__ == "__main__":
    main()
