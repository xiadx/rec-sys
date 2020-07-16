import os
import sys
import json
import datetime
from pyspark.sql import *


def get_days_list(start, end, fmt="%Y%m%d"):
    r = []
    cur = end
    while datetime.datetime.strptime(cur, fmt) >= datetime.datetime.strptime(start, fmt):
        r.append(cur)
        cur = (datetime.datetime.strptime(cur, fmt) + datetime.timedelta(days=-1)).strftime(fmt)
    return r


def main():
    if len(sys.argv) != 2:
        print("parameter error")
        sys.exit(1)

    arg = json.loads(sys.argv[1])

    spark = SparkSession.builder \
        .appName("dump_sample") \
        .enableHiveSupport() \
        .getOrCreate()

    input_path = arg["input-path"]
    output_path = arg["output-path"]
    start = arg["start"]
    end = arg["end"]
    sels = arg["select"]

    days_list = get_days_list(start, end)
    df_merge = spark.read.parquet(*[os.path.join(input_path, day) for day in days_list]).select(*sels)

    def deal_sample(r):
        s = ""
        for i, c in enumerate(r):
            f = 0
            try:
                f = float(r[i])
            except Exception as e:
                print(e)
            s += "%d:%f " % (i, f)
        s = s[:-1]
        return s

    df_merge.rdd.map(lambda r: deal_sample(r)).saveAsTextFile(output_path)


if __name__ == "__main__":
    main()
