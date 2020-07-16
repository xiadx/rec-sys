from ftplib import FTP


def main():
    ftp = FTP('ftp.debian.org')
    print(ftp.login())
    # ftp.cwd('debian')
    # print(ftp.retrlines('LIST'))


if __name__ == "__main__":
    main()
