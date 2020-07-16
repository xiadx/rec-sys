import os
import json
import subprocess


class Submitter(object):

    def __init__(self):
        self.hive = "/usr/local/datacenter/hive/bin/hive"
        self.presto = "/usr/local/datacenter/presto/bin/presto-cli.pl"
        self.hadoop = "/usr/local/datacenter/hadoop/bin/hadoop"
        self.spark_submit = "/usr/local/datacenter/spark2/bin/spark-submit"
        self.conf = {
            "spark.default.parallelism": "400",
            "spark.sql.shuffle.partitions": "200",
            "spark.python.worker.memory": "10000",
            # "spark.yarn.appMasterEnv.JAVA_HOME": "/usr/java/jdk1.8.0_71",
            # "spark.executorEnv.JAVA_HOME": "/usr/java/jdk1.8.0_71",
            # "spark.yarn.appMasterEnv.PYSPARK_PYTHON": "./ANACONDA/python3.7/bin/python",
            # "spark.executorEnv.PYSPARK_PYTHON": "./ANACONDA/python3.7/bin/python",
        }
        self.param = {
            "queue": "root.spark",
            "master": "yarn",
            "driver-memory": "8G",
            "executor-memory": "8G",
            "executor-cores": "1",
            "num-executors": "50",
        }

    def submit(self, app, arg=None, param=None, conf=None):
        cur_param = self.param.copy()
        cur_conf = self.conf.copy()
        if param:
            cur_param.update(param)
        if conf:
            cur_conf.update(conf)
        cmd = self.spark_submit
        for k, v in cur_param.items():
            cmd += ' --%s "%s"' % (k, v)
        for k, v in cur_conf.items():
            cmd += ' --conf "%s=%s"' % (k, v)
        cmd += ' %s' % app
        if arg:
            cmd += ' "%s"' % json.dumps(arg).replace('"', '\\"')
        return subprocess.getstatusoutput(cmd)


class JobManager(object):

    def __init__(self, conf_mgr):
        self.conf_mgr = conf_mgr
        self.pipeline = dict()
        self.submitter = Submitter()


def main():
    print(Submitter())
    print(Submitter())


if __name__ == "__main__":
    main()
