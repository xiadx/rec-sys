import sys
import json
import importlib


def main():
    # if len(sys.argv) != 2:
    #     print("parameter error")
    #     sys.exit(1)
    #
    # arg = json.loads(sys.argv[1])

    sample_method = "lr_sample"
    sample_module = importlib.import_module(sample_method)
    sample_module.main()


if __name__ == "__main__":
    main()
