import multiprocessing


class CpuUtil(object):

    @staticmethod
    def cpu_count():
        return multiprocessing.cpu_count()


def main():
    print(CpuUtil.cpu_count())


if __name__ == "__main__":
    main()
