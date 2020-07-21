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
    conf_mgr.load_model_conf()
    conf_mgr.load_cmd_conf()
    conf_mgr.load_pipe_conf()
    conf_mgr.load_submit_conf()

    job_mgr = job_manager.JobManager(conf_mgr)

    if gflags.FLAGS.single:
        if len(sys.argv) != 2:
            print('Usage: %s <job_name>\nARGS:\n%s' % (sys.argv[0], gflags.FLAGS))
            sys.exit(1)

        job_name = sys.argv[1]

        job_mgr.submitter.submit(job_name)


if __name__ == "__main__":
    main()
