import os
import gc
import sys
import math
import random
import asyncio
from collections import defaultdict
from multiprocessing import Pool
import logging

sys.path.append(os.path.abspath("../utils"))
sys.path.append(os.path.abspath("../../utils"))

from TimeUtil import TimeUtil
from FileUtil import FileUtil
from PickleUtil import PickleUtil
from CpuUtil import CpuUtil
from DSSMConfigUtil import DSSMConfigUtil


# create logger
logger = logging.getLogger('sample')
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


def click_action_files(path, dt, cnt=0):
    days_list = TimeUtil.get_days_list(dt, cnt)
    files = []
    for dt in days_list:
        files.append(os.path.join(path, "click_{}.pkl".format(dt)))
    return files


def merge_action(files):
    merge_action_dict = defaultdict(lambda: [])
    for file in files:
        action_dict = PickleUtil.load(file)
        for k, v in action_dict.items():
            merge_action_dict[k].extend(v)
    return merge_action_dict


def filter_action(action, mi, ma):
    return {k: ["_".join(_.split("_")[:2][:ma]) for _ in v] for k, v in action.items() if mi <= len(v)}


def split_merge_action(merge, block_size):
    blocks = []
    block = {}
    for i, (k, v) in enumerate(merge.items()):
        if i % block_size != 0:
            block[k] = v
        else:
            blocks.append(block)
            block = {}
    if block:
        blocks.append(block)
    return blocks


def deal_block(block, items, window, J, path):
    for k, v in block.items():
        negatives = items.difference(set(v))
        tasks = []

        for i in range(window, len(v)):
            tasks.append(deal_line(k, v[i-window:i], v[i], random.sample(negatives, J), path))

        if tasks:
            loop = asyncio.get_event_loop()
            loop.run_until_complete(asyncio.wait(tasks))

        del negatives, k, v
        gc.collect()


async def write(s, path):
    with open(path, "a+") as f:
        f.write(s)


async def deal_line(open_udid, items, positives, negatives, path):
    s = "{0},{1},{2},{3}\n".format(open_udid,
                                   " ".join([str(_) for _ in items]),
                                   str(positives),
                                   ",".join([str(_) for _ in negatives]))
    await write(s, path)


def set_items(merge):
    s = set()
    for k, v in merge.items():
        s.update(["_".join(_.split("_")[:2]) for _ in v])
    return s


def save_items(s, path):
    PickleUtil.save(s, path)


def main():
    if len(sys.argv) == 1:
        dt = TimeUtil.get_yesterday_str()
    elif len(sys.argv) == 2:
        dt = sys.argv[1]
    else:
        logger.error("parameter error")
        sys.exit(1)

    recent_items = DSSMConfigUtil.sample_conf.get_int("recent-items")
    cpu_threshold = DSSMConfigUtil.sample_conf.get_float("cpu-threshold")
    window = DSSMConfigUtil.sample_conf.get_int("window")
    J = DSSMConfigUtil.sample_conf.get_int("J")

    action_path = DSSMConfigUtil.action_conf.get_string("action-path")
    if action_path == "":
        job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
        action_path = os.path.join(job_path, "data/rec-recall/dssm/action")
    click_path = os.path.join(action_path, "click")

    click_files = click_action_files(click_path, dt, 1)
    logger.info("click_files:{}".format(click_files))

    sample_path = DSSMConfigUtil.sample_conf.get_string("sample-path")
    if sample_path == "":
        job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
        sample_path = os.path.join(job_path, "data/rec-recall/dssm/sample")

    os.makedirs(sample_path, exist_ok=True)
    logger.info("sample_path:{}".format(sample_path))

    if not FileUtil.files_exists(click_files):
        logger.info("there are click files that do not exist")
        sys.exit(1)

    ma = merge_action(click_files)
    fa = filter_action(ma, window+1, recent_items)
    logger.info("len(ma):{}".format(len(ma)))
    logger.info("len(fa):{}".format(len(fa)))

    items = set_items(ma)
    logger.info("len(items):{}".format(len(items)))

    total_number = len(fa)
    block_number = math.ceil(CpuUtil.cpu_count() * cpu_threshold)
    block_size = FileUtil.block_size(total_number, block_number)

    logger.info("total_number:{}".format(total_number))
    logger.info("block_number:{}".format(block_number))
    logger.info("block_size:{}".format(block_size))

    blocks = split_merge_action(fa, block_size)

    logger.info("len(blocks):{}".format(len(blocks)))

    FileUtil.remove(os.path.join(sample_path, "sample_{}.data".format(dt)))

    del ma, fa
    gc.collect()

    pool = Pool(block_number)
    for block in blocks:
        pool.apply_async(deal_block, args=(block, items, window, J, os.path.join(sample_path, "sample_{}.data".format(dt))))
    pool.close()
    pool.join()

    logger.info("sample_file:{}".format(os.path.join(sample_path, "sample_{}.data".format(dt))))


if __name__ == "__main__":
    main()
