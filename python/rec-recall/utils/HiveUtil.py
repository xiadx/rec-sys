from pyhive import hive
from ConfigUtil import ConfigUtil


class HiveUtil(object):

    host = ConfigUtil.hive_conf.get_string("host")

    port = ConfigUtil.hive_conf.get_int("port")

    username = ConfigUtil.hive_conf.get_string("username")

    database = ConfigUtil.hive_conf.get_string("database")

    auth = ConfigUtil.hive_conf.get_string("auth")

    @classmethod
    def get_connection(cls):
        conn = hive.Connection(host=cls.host,
                               port=cls.port,
                               username=cls.username,
                               database=cls.database,
                               auth=cls.auth)
        return conn

    @classmethod
    def close(cls, conn):
        if conn:
            conn.close()


def main():
    print(HiveUtil.get_connection())


if __name__ == "__main__":
    main()
