// 将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
// 输入一个字符串,包括数字字母符号,可以为空
// 如果是合法的数值表达则返回该数字，否则返回0

#include <iostream>
using namespace std;

class Solution {
public:
    int StrToInt(string str) {
        int n = str.size(), f = 1;
        long long r = 0;
        if (!n) return 0;
        if (str[0] == '-') f = -1;
        for (int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i < n; ++i) {
            if (!('0' <= str[i] && str[i] <= '9')) return 0;
            r = (r << 1) + (r << 3) + (str[i] & 0xf);
        }
        if ((f == 1 && r > 0x7fffffff) || (f == -1 && r > 0x80000000)) {
            return 0;
        }
        return f * r;
    }
};

int main() {
    Solution s;
    cout << s.StrToInt("+123") << endl;
    return 0;
}
