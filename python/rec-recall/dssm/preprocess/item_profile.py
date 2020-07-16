import os
import sys
import math
import logging
import pandas as pd
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences

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
    id_columns = ["itemid", "uid"]
    category_columns = []
    numerical_columns = ["itemtype", "month", "season"]
    continuous_columns = ["diff_time"]
    varlen_columns = ["tags", "poi", "mdd"]

    # missing value
    for c in id_columns:
        df[c] = df[c].apply(lambda x: "-1" if x == "" or x == "None" or x is None else x)
    for c in category_columns:
        df[c] = df[c].apply(lambda x: "-1" if x == "" or x == "None" or x is None else x)
    for c in numerical_columns:
        df[c] = df[c].apply(lambda x: 0 if x == "" or x == "None" or x is None else int(x))
    for c in continuous_columns:
        df[c] = df[c].apply(lambda x: 0 if x is None else float(x))
    for c in varlen_columns:
        df[c] = df[c].apply(lambda x: "-1" if x == "" or x == "None" or x is None else x)

    # df[id_columns] = df[id_columns].fillna("-1")
    # df[category_columns] = df[category_columns].fillna("-1")
    # df[continuous_columns] = df[continuous_columns].fillna(0)

    # unique id
    df["itemid_type"] = df[["itemid", "itemtype"]].apply(lambda x: str(x[0]) + "_" + str(x[1]), axis=1)

    # varlen columns
    for c in varlen_columns:
        df[c] = df[c].apply(lambda x: x.split(","))

    id_columns = ["itemid_type", "uid"]

    feature_columns = id_columns + category_columns + numerical_columns + continuous_columns + varlen_columns
    df = df[feature_columns]

    # tokenizer
    tokenizer_dict = {}
    for c in id_columns:
        tokenizer = Tokenizer(filters=None, lower=False, oov_token="<OOV_TOKEN>")
        tokenizer.fit_on_texts([df[c].values.tolist()])
        df[c + "_index"] = tokenizer.texts_to_sequences([df[c].values.tolist()])[0]
        tokenizer_dict[c] = tokenizer
    for c in category_columns:
        tokenizer = Tokenizer(filters=None, lower=False, oov_token="<OOV_TOKEN>")
        tokenizer.fit_on_texts([df[c].values.tolist()])
        df[c + "_index"] = tokenizer.texts_to_sequences([df[c].values.tolist()])[0]
        tokenizer_dict[c] = tokenizer
    for c in varlen_columns:
        tokenizer = Tokenizer(filters=None, lower=False, oov_token="<OOV_TOKEN>")
        tokenizer.fit_on_texts(df[c].values)
        df[c + "_index"] = tokenizer.texts_to_sequences(df[c].values)
        q = math.ceil(df[c].apply(lambda x: len(x)).quantile(0.9))
        df[c + "_index"] = df[c + "_index"].apply(lambda x: pad_sequences([x], maxlen=q)[0])
        tokenizer_dict[c] = tokenizer

    return df, tokenizer_dict


def main():
    item_profile_path = DSSMConfigUtil.item_conf.get_string("item-profile-path")
    if item_profile_path == "":
        job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
        item_profile_path = os.path.join(job_path, "data/rec-recall/dssm/item-profile")

    os.makedirs(item_profile_path, exist_ok=True)

    df = PickleUtil.cache(os.path.join(item_profile_path, "cache.pkl"), item_profile)
    df, tokenizer_dict = deal_item_profile(df)

    PickleUtil.save(df, os.path.join(item_profile_path, "df.pkl"))
    PickleUtil.save(tokenizer_dict, os.path.join(item_profile_path, "tokenizer.pkl"))

    logger.info("df_file:{}".format(os.path.join(item_profile_path, "df.pkl")))
    logger.info("tokenizer_file:{}".format(os.path.join(item_profile_path, "tokenizer.pkl")))


if __name__ == "__main__":
    main()
