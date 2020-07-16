import pymysql
from ConfigUtil import ConfigUtil


class JdbcUtil(object):

    mysql_conf = ConfigUtil.db_conf.get_config("mysql")

    @classmethod
    def get_connection(cls):
        host = cls.mysql_conf.get_string("host")
        user = cls.mysql_conf.get_string("user")
        password = cls.mysql_conf.get_string("password")
        database = cls.mysql_conf.get_string("database")
        port = cls.mysql_conf.get_int("port")
        charset = 'utf8'
        use_unicode = True

        conn = pymysql.connect(host=host,
                               user=user,
                               password=password,
                               database=database,
                               port=port,
                               charset=charset,
                               use_unicode=use_unicode)

        return conn

    @classmethod
    def close(cls, conn):
        if conn:
            conn.close()


def main():
    print(JdbcUtil.get_connection())


if __name__ == "__main__":
    main()
