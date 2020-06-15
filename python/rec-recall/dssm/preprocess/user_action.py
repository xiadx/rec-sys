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
logger = logging.getLogger('user_action')
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


def full_station_action(dt):
    """
    root
     |-- open_udid: string (nullable = true)
     |-- item_id: string (nullable = true)
     |-- item_type: string (nullable = true)
     |-- ctime: long (nullable = true)
     |-- event_code: string (nullable = true)
     |-- dt: string (nullable = true)
    Args:
        dt: dt
    Return:
        df: pd.DataFrame
    """
    conn = HiveUtil.get_connection()
    sql = """
    select
        *
    from
        recommend.zbb_full_station_click
    where
        dt='%s' and item_type in ('0','1','8') and event_code in ('show_index','page')
    """ % dt
    df = pd.read_sql(sql, conn)
    df.columns = [c.split(".")[-1] for c in df.columns]
    selected_col = "id_type_time"
    df[selected_col] = df.apply(lambda r: r[1] + "_" + r[2] + "_" + str(r[3]), axis=1)
    HiveUtil.close(conn)
    return df


def get_user_action(df, grouped_col, selected_col):
    """
    Args:
        df: pd.DataFrame
        grouped_col: grouped col
        selected_col: select col
        sorted_col: sorted col
    Return:
        user_action_dict: {open_uid:[item_id_item_type_ctime, ...]}
    """
    user_action_dict = {}
    for open_udid, grouped in df.groupby(grouped_col):
        items = sorted(grouped[selected_col].values.tolist(), key=lambda x: float(x.split("_")[2]), reverse=True)
        user_action_dict[open_udid] = items
    return user_action_dict


def get_user_click(df):
    """
    Args:
        df: full station action
    Return:
        user click action
    """
    grouped_col = "open_udid"
    selected_col = "id_type_time"
    click_df = df[df.event_code == "page"]
    user_click = get_user_action(click_df, grouped_col, selected_col)
    return user_click


def get_user_show(df):
    """
    Args:
        df: full station action
    Return:
        user show action
    """
    grouped_col = "open_udid"
    selected_col = "id_type_time"
    show_df = df[df.event_code == "show_index"]
    user_show = get_user_action(show_df, grouped_col, selected_col)
    return user_show


def save_user_click(user_click, path):
    PickleUtil.save(user_click, path)


def save_user_show(user_show, path):
    PickleUtil.save(user_show, path)


def main():
    if len(sys.argv) == 1:
        dt = TimeUtil.get_yesterday_str()
    elif len(sys.argv) == 2:
        dt = sys.argv[1]
    else:
        logger.error("parameter error")
        sys.exit(1)

    action_path = DSSMConfigUtil.action_conf.get_string("action-path")
    if action_path == "":
        job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../../"))
        action_path = os.path.join(job_path, "data/rec-recall/dssm/action")

    click_path = os.path.join(action_path, "click")
    show_path = os.path.join(action_path, "show")

    os.makedirs(click_path, exist_ok=True)
    os.makedirs(show_path, exist_ok=True)

    logger.info("click_path:{}".format(click_path))
    logger.info("show_path:{}".format(show_path))

    df = full_station_action(dt)
    user_click = get_user_click(df)
    user_show = get_user_show(df)
    save_user_click(user_click, os.path.join(click_path, "click_{}.pkl".format(dt)))
    save_user_show(user_show, os.path.join(show_path, "show_{}.pkl".format(dt)))


if __name__ == "__main__":
    main()
