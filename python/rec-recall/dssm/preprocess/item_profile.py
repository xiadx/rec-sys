import os
import sys
import logging
import pandas as pd

sys.path.append(os.path.abspath("../utils"))
sys.path.append(os.path.abspath("../../utils"))

from HiveUtil import HiveUtil
from TimeUtil import TimeUtil
from PickleUtil import PickleUtil
from DSSMConfigUtil import DSSMConfigUtil


# create logger
logger = logging.getLogger('item_profile')
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


def item_profile():
    """
    root
     |-- itemId: long (nullable = true)
     |-- itemType: integer (nullable = true)
     |-- uid: string (nullable = true)
     |-- quality_score: double (nullable = true)
     |-- ctime: string (nullable = true)
     |-- duration: double (nullable = true)
     |-- default_bucket: string (nullable = true)
     |-- test_bucket: string (nullable = true)
     |-- tags: string (nullable = true)
     |-- poi: string (nullable = true)
     |-- mdd: string (nullable = true)
     |-- item_vec: string (nullable = true)
     |-- poi_vec_1: string (nullable = true)
     |-- als_item_vec: string (nullable = true)
     |-- mdd_vec: string (nullable = true)
     |-- poi_vec_2: string (nullable = true)
     |-- tag_vec: string (nullable = true)
     |-- diff_time: double (nullable = true)
     |-- month: string (nullable = true)
     |-- season: string (nullable = true)
    Return:
        df: pd.DataFrame
    """
    conn = HiveUtil.get_connection()
    sql = """
    select
        *
    from
        recommend.zbb_item_profile_v3
    """
    df = pd.read_sql(sql, conn)
    df.columns = [c.split(".")[-1] for c in df.columns]
    HiveUtil.close(conn)
    return df


def deal_item_profile(df):
    """
    deal item profile
    Args:
        df: pd.DataFrame item profile
    Return:
        item_profile: pd.DataFrame
    """


def main():
    pass


if __name__ == "__main__":
    main()
