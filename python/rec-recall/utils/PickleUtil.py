import pickle


class PickleUtil(object):

    @staticmethod
    def save(obj, path, mode="wb"):
        with open(path, mode) as f:
            pickle.dump(obj, f, protocol=pickle.HIGHEST_PROTOCOL)

    @staticmethod
    def load(path, mode="rb"):
        with open(path, mode) as f:
            obj = pickle.load(f)
        return obj


def main():
    pass


if __name__ == "__main__":
    main()
