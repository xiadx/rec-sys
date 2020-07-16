import os
import pickle
import logging


class PickleUtil(object):

    # create logger
    logger = logging.getLogger('PickleUtil')
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

    @staticmethod
    def save(obj, path, mode="wb"):
        with open(path, mode) as f:
            pickle.dump(obj, f, protocol=pickle.HIGHEST_PROTOCOL)

    @staticmethod
    def load(path, mode="rb"):
        with open(path, mode) as f:
            obj = pickle.load(f)
        return obj

    @classmethod
    def cache(cls, cache_path, fn, *args, **kwargs):
        """
        Cache-wrapper for a function or class. If the cache-file exists
        then the data is reloaded and returned, otherwise the function
        is called and the result is saved to cache. The fn-argument can
        also be a class instead, in which case an object-instance is
        created and saved to the cache-file.
        Args:
            cache_path: File-path for the cache-file.
            fn: Function or class to be called.
            args: Arguments to the function or class-init.
            kwargs: Keyword arguments to the function or class-init.
        Return:
            The result of calling the function or creating the object-instance.
        """
        # If the cache-file exists.
        if os.path.exists(cache_path):
            # Load the cached data from the file.
            with open(cache_path, mode="rb") as file:
                obj = pickle.load(file)

            cls.logger.info("- Data saved to cache-file: " + cache_path)
        else:
            # The cache-file does not exist.

            # Call the function / class-init with the supplied arguments.
            obj = fn(*args, **kwargs)

            # Save the data to a cache-file.
            with open(cache_path, mode="wb") as file:
                pickle.dump(obj, file)

            cls.logger.info("- Data saved to cache-file: " + cache_path)

        return obj


def main():
    pass


if __name__ == "__main__":
    main()
