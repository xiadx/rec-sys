import sys


def sirf_config():
    if len(sys.argv) != 3:
        print("parameter error")
    else:
        file = sys.argv[1]
        window = sys.argv[2]
        with open(file, "r") as fp:
            i = 82
            for line in fp:
                id = i
                mark = "sirf"
                if line.strip() == "itemId" or line.strip() == "itemType" or line.strip() == "itemUniqueId" \
                        or line.strip() == "deadlineTime":
                    name = line.strip()
                else:
                    name = line.strip() + window
                detail = ""
                category = ""
                expression = ""
                if name.find("Ctr") != -1:
                    default = "0.0"
                    type = "double"
                else:
                    default = "0"
                    type = "long"
                status = "1"
                print("{\n\tid=%d\n\tmark=\"%s\"\n\tname=\"%s\"\n\tdetail=\"%s\"\n\t"
                      "category=\"%s\"\n\texpression=\"%s\"\n\tdefault=\"%s\"\n\t"
                      "type=\"%s\"\n\tstatus=\"%s\"\n}" %
                      (id, mark, name, detail, category, expression, default, type, status))
                i += 1


def main():
    if len(sys.argv) != 2:
        print("parameter error")
    else:
        file = sys.argv[1]
        with open(file, "r") as fp:
            for line in fp:
                if line.strip() == "itemId" or line.strip() == "itemType" or line.strip() == "itemUniqueId":
                    expression = "val " + line.strip() + ": String = \"abc\""
                elif line.strip().find("Ctr") != -1:
                    expression = "var " + line.strip() + ": Double = 0.0d"
                else:
                    expression = "var " + line.strip() + ": Long = 0L"
                print(expression)


if __name__ == "__main__":
    main()
