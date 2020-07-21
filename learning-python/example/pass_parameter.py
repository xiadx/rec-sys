def abc(a=0, b=0, c=0, d=0, **kwargs):
    print(a, b, c, d)


def main():
    d = {"a":1, "b": 2, "c": 3, "d": 4}
    abc(**d)


if __name__ == "__main__":
    main()
