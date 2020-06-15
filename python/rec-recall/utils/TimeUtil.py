import time
import datetime
import functools
import logging


class TimeUtil(object):

    @staticmethod
    def get_today_str(fmt='%Y%m%d'):
        return datetime.date.today().strftime(fmt)

    @staticmethod
    def get_yesterday_str(fmt='%Y%m%d'):
        return (datetime.date.today() + datetime.timedelta(days=-1)).strftime(fmt)

    @staticmethod
    def get_few_days_ago(dt, cnt, fmt='%Y%m%d'):
        return (datetime.datetime.strptime(dt, fmt) + datetime.timedelta(days=-cnt)).strftime(fmt)

    @staticmethod
    def get_days_list(dt, cnt=0, fmt='%Y%m%d'):
        start = TimeUtil.get_few_days_ago(dt, cnt, fmt)
        r = []
        cur = dt
        while datetime.datetime.strptime(cur, fmt) >= datetime.datetime.strptime(start, fmt):
            r.append(cur)
            cur = (datetime.datetime.strptime(cur, fmt) + datetime.timedelta(days=-1)).strftime(fmt)
        return r


def timeit(fn):
    # create logger
    logger = logging.getLogger('timeit')
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

    @functools.wraps(fn)
    def wrapper(*args, **kwargs):
        begin = time.time()
        result = fn(*args, **kwargs)
        logger.info("cost %.2fs for %s" % (time.time() - begin, fn.__name__))
        return result

    return wrapper


@timeit
def main():
    print(TimeUtil.get_today_str())
    print(TimeUtil.get_yesterday_str())
    print(TimeUtil.get_few_days_ago("20200605", 3))
    print(TimeUtil.get_days_list("20200609", 1))


if __name__ == "__main__":
    main()
