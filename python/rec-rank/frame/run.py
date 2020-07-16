import os
import sys
import conf_manager
import job_manager
import gflags


def main():
    gflags.DEFINE_boolean('single', True, 'run single spark job')
    try:
        gflags.FLAGS(sys.argv)
    except gflags.FlagsError as e:
        print('%s\nUsage: %s ARGS:\n%s' % (e, sys.argv[0], gflags.FLAGS))
        sys.exit(1)

    conf_mgr = conf_manager.ConfManager()
    # conf_mgr.load_basic_feature()
    # conf_mgr.load_offline_feature()
    conf_mgr.load_spark_conf()

    job_mgr = job_manager.JobManager(conf_mgr)

    if gflags.FLAGS.single:
        if len(sys.argv) != 2:
            print('Usage: %s <job_name>\nARGS:\n%s' % (sys.argv[0], gflags.FLAGS))
            sys.exit(1)

        job_name = sys.argv[1]
        app = conf_mgr.spark_conf[job_name].app
        param = conf_mgr.spark_conf[job_name].param
        conf = conf_mgr.spark_conf[job_name].conf
        arg = conf_mgr.spark_conf[job_name].arg

        code, ret = job_mgr.submitter.submit(app,
                                             arg,
                                             param,
                                             conf)
        print('code:', code)
        print('msg', ret)


if __name__ == "__main__":
    main()
