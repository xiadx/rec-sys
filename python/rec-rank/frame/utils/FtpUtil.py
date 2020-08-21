from ftplib import FTP
from ConfigUtil import ConfigUtil


class FtpUtil(object):

    ftp_conf = ConfigUtil.db_conf.get_config("ftp")

    @classmethod
    def get_connection(cls):
        ftp = FTP()
        ftp.connect(cls.ftp_conf.get_string("host"), cls.ftp_conf.get_int("port"))
        ftp.login(cls.ftp_conf.get_string("username"), cls.ftp_conf.get_string("password"))
        return ftp

    @classmethod
    def retrbinary(cls, src_file, dest_file, blocksize=8192):
        ftp = cls.get_connection()
        with open(dest_file, "wb") as fp:
            ftp.retrbinary("RETR %s" % src_file, fp.write, blocksize)
        ftp.quit()


def main():
    src_file = "README.md"
    dest_file = "README.md"
    FtpUtil.retrbinary(src_file, dest_file)


if __name__ == "__main__":
    main()
