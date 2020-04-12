// 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.

#include <iostream>
using namespace std;

class Solution {
public:
    int FirstNotRepeatingChar(string str) {
        if (str.size() == 0) {
            return -1;
        }
        char ch[256] = {0};
        for (int i = 0; i < str.size(); ++i) {
            ch[str[i]]++;
        }
        for (int i = 0; i < str.size(); ++i) {
            if (ch[str[i]] == 1) {
                return i;
            }
        }
        return -1;
    }
};

int main() {
    string str = "abc";
    Solution s;
    cout << s.FirstNotRepeatingChar(str) << endl;
    return 0;
}
