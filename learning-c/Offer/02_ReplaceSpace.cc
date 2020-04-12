// 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。

#include <iostream>
using namespace std;

class Solution {
public:
    void replaceSpace(char *str, int length) {
        if (str == NULL) {
            return;
        }
        int blankCount = 0;
        int originalLength = 0;
        for (int i = 0; str[i] != '\0'; ++i) {
            ++originalLength;
            if (str[i] == ' ') {
                ++blankCount;
            }
        }
        int l = originalLength + 2 * blankCount;
        if (l + 1 > length) {
            return;
        }
        char *p = str + originalLength;
        char *q = str + l;
        while (p < q) {
            if (*p == ' ') {
                *q-- = '0';
                *q-- = '2';
                *q-- = '%';
            } else {
                *q-- = *p;
            }
            --p;
        }
    }
};

int main() {
    char str[100] = "We Are Happy";
    Solution s;
    s.replaceSpace(str, 100);
    cout << str << endl;
    return 0;
}
