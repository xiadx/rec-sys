import os
import math


class FileUtil(object):

    @staticmethod
    def exists(path):
        os.path.exists(path)

    @staticmethod
    def remove(path):
        if FileUtil.exists(path):
            os.remove(path)

    @staticmethod
    def files_exists(files):
        for file in files:
            if not os.path.exists(file):
                return False
        return True

    @staticmethod
    def block_size(total_number, block_number):
        return math.ceil(total_number / block_number)


def main():
    print(FileUtil.block_size(10, 2))


if __name__ == "__main__":
    main()
