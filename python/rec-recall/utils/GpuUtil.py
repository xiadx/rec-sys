import logging
import tensorflow as tf


class GpuUtil(object):

    # create logger
    logger = logging.getLogger('GpuUtil')
    logger.setLevel(logging.INFO)

    # create console handler and set level to info
    ch = logging.StreamHandler()
    ch.setLevel(logging.INFO)

    # create formatter
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')

    # add formatter to ch
    ch.setFormatter(formatter)

    # add ch to logger
    logger.addHandler(ch)

    @classmethod
    def gpu_info(cls):
        gpus = tf.config.experimental.list_physical_devices('GPU')
        for gpu in gpus:
            cls.logger.info(gpu)

    @classmethod
    def cpu_info(cls):
        cpus = tf.config.experimental.list_physical_devices('CPU')
        for cpu in cpus:
            cls.logger.info(cpu)

    @classmethod
    def set_memory_growth(cls):
        gpus = tf.config.experimental.list_physical_devices('GPU')
        if gpus:
            try:
                for gpu in gpus:
                    tf.config.experimental.set_memory_growth(gpu, True)
                logical_gpus = tf.config.experimental.list_logical_devices('GPU')
                # logger.info("%d Physical GPUs, %d Logical GPUs" % (len(gpus), len(logical_gpus)))
                cls.logger.info("Set Memory Growth, {0} Physical GPUs, {1} Logical GPUs".format(len(gpus), len(logical_gpus)))
            except RuntimeError as e:
                cls.logger.error(e)

    @classmethod
    def choose_gpu(cls, n=0):
        gpus = tf.config.experimental.list_physical_devices('GPU')
        if gpus:
            try:
                tf.config.experimental.set_visible_devices(gpus[n], 'GPU')
                cls.logger.info(gpus[n])
            except RuntimeError as e:
                cls.logger.error(e)

    @staticmethod
    def mirrored_strategy():
        return tf.distribute.MirroredStrategy()

    @staticmethod
    def num_replicas_in_sync():
        mirrored_strategy = tf.distribute.MirroredStrategy()
        return mirrored_strategy.num_replicas_in_sync

    @staticmethod
    def batch_size(batch_size_replica):
        return batch_size_replica * GpuUtil.num_replicas_in_sync()


def main():
    # print(GpuUtil.num_replicas_in_sync())
    GpuUtil.cpu_info()


if __name__ == "__main__":
    main()
