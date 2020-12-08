import os


def main():
    job_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "../../../"))
    sample_path = os.path.join(job_path, "data/rec-rank/sample/index")
    train_path = os.path.join(sample_path, "xgb.v1.train.libsvm.shuf")
    test_path = os.path.join(sample_path, "xgb.v1.test.libsvm.shuf")
    i = 1
    with open(train_path, "rb") as fp:
        for line in fp:
            if i < 10:
                print(line)
            else:
                break
            i += 1


if __name__ == "__main__":
    main()
