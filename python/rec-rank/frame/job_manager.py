import os
import sys
import re
import json
import subprocess


class Submitter(object):

    def __init__(self, conf_mgr):
        self.conf_mgr = conf_mgr
        self.hive = conf_mgr.submit_conf["submit"].hive
        self.presto = conf_mgr.submit_conf["submit"].presto
        self.hadoop = conf_mgr.submit_conf["submit"].hadoop
        self.python = conf_mgr.submit_conf["submit"].python
        self.spark = conf_mgr.submit_conf["submit"].spark
        self.job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../"))
        self.conf = conf_mgr.submit_conf["submit"].conf
        self.param = conf_mgr.submit_conf["submit"].param

    def spark_submit(self, app, arg=None, param=None, conf=None):
        cur_param = self.param.copy()
        cur_conf = self.conf.copy()
        if param:
            cur_param.update(param)
        if conf:
            cur_conf.update(conf)
        cmd = self.spark
        if "jars" in cur_param:
            cur_param["jars"] = os.path.join(self.job_path, cur_param["jars"])
        for k, v in cur_param.items():
            cmd += ' --%s "%s"' % (k, v)
        for k, v in cur_conf.items():
            cmd += ' --conf "%s=%s"' % (k, v)
        if app.startswith('/'):
            cmd += ' %s' % app
        else:
            cmd += ' %s' % os.path.join(self.job_path, app)
        if arg:
            cmd += ' "%s"' % json.dumps(arg).replace('"', '\\"')
        print("Command: %s" % cmd)
        return subprocess.getstatusoutput(cmd)

    def cmd_submit(self, cmd, typ=None, arg=None):
        if typ:
            cmd = cmd.replace("{%s}" % typ, getattr(self, typ))
        if arg:
            pattern = re.compile(r"{.+?}")
            cur_arg = {k: v for k, v in arg.items() if not pattern.findall(v)}
            conf_arg = {k: v for k, v in arg.items() if pattern.findall(v)}
            for k, v in conf_arg.items():
                cur_v = v
                for p in pattern.findall(v):
                    ca = re.sub(r"{|}", "", p).replace("_", "-")
                    if ca in cur_arg:
                        cur_v = cur_v.replace(p, cur_arg[ca])
                conf_arg[k] = cur_v
            cur_arg.update(conf_arg)
            param = pattern.findall(cmd)
            for p in param:
                k = re.sub(r"{|}", "", p).replace("_", "-")
                if k in cur_arg:
                    cmd = cmd.replace(p, cur_arg[k])
                else:
                    print('Error: %s param not in arg' % k)
                    sys.exit(1)
        print("Command: %s" % cmd)
        return subprocess.getstatusoutput(cmd)

    def app_submit(self, app, typ=None, arg=None):
        if typ:
            cmd = getattr(self, typ)
        else:
            cmd = self.python
        if app.startswith('/'):
            cmd += ' %s' % app
        else:
            cmd += ' %s' % os.path.join(self.job_path, app)
        if arg:
            cmd += ' "%s"' % json.dumps(arg).replace('"', '\\"')
        print("Command: %s" % cmd)
        return subprocess.getstatusoutput(cmd)

    def submit(self, job_name):
        if job_name in self.conf_mgr.spark_conf:
            app = self.conf_mgr.spark_conf[job_name].app
            param = self.conf_mgr.spark_conf[job_name].param
            conf = self.conf_mgr.spark_conf[job_name].conf
            arg = self.conf_mgr.spark_conf[job_name].arg

            code, ret = self.spark_submit(app,
                                          arg,
                                          param,
                                          conf)
            print('code:', code)
            print('msg', ret)

        if job_name in self.conf_mgr.model_conf:
            app = self.conf_mgr.model_conf[job_name].app
            typ = self.conf_mgr.model_conf[job_name].type
            arg = self.conf_mgr.model_conf[job_name].arg

            code, ret = self.app_submit(app,
                                        typ,
                                        arg)
            print('code:', code)
            print('msg', ret)

        if job_name in self.conf_mgr.cmd_conf:
            cmd = self.conf_mgr.cmd_conf[job_name].cmd
            typ = self.conf_mgr.cmd_conf[job_name].typ
            arg = self.conf_mgr.cmd_conf[job_name].arg

            code, ret = self.cmd_submit(cmd,
                                        typ,
                                        arg)

            print('code:', code)
            print('msg', ret)

        if job_name in self.conf_mgr.pipe_conf:
            pipelines = self.conf_mgr.pipe_conf[job_name].pipelines

            self.pipe_submit(pipelines)

    def pipe_submit(self, pipelines):
        for stage, tasks in pipelines.items():
            print("%s: %s" % (stage, ",".join(tasks)))
            for task in tasks:
                self.submit(task)


class JobManager(object):

    def __init__(self, conf_mgr):
        self.conf_mgr = conf_mgr
        self.submitter = Submitter(conf_mgr)


def main():
    pass


if __name__ == "__main__":
    main()
