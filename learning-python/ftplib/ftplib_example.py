from ftplib import FTP


def main():
    host = "192.168.7.186"
    username = "admin"
    password = "admin@123"
    port = 21
    ftp = FTP()
    ftp.connect(host, port)
    ftp.login(username, password)
    ftp.retrlines('LIST')
    with open("README.md" , "wb") as fp:
        ftp.retrbinary('RETR README.md', fp.write, 1024)
    ftp.quit()


if __name__ == "__main__":
    main()
