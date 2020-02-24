from functools import cmp_to_key


def cmp(u1, u2):
    if u1["name"] > u2["name"]:
        return 1
    if u1["name"] < u2["name"]:
        return -1
    if u1["name"] == u2["name"]:
        if u1["age"] < u2["age"]:
            return 1
        if u1["age"] > u2["age"]:
            return -1
        if u1["age"] == u2["age"]:
            return 0


def main():
    us = [
        {"name": "xiadingxing", "age": 18},
        {"name": "xiadingxing", "age": 19},
        {"name": "pantingsheng", "age": 19}
    ]
    print(sorted(us, key=cmp_to_key(cmp)))


if __name__ == "__main__":
    main()
