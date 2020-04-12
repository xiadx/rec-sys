// 请实现一个函数用来找出字符流中第一个只出现一次的字符。例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
// 如果当前字符流没有存在出现一次的字符，返回#字符。

#include <iostream>
#include <deque>
using namespace std;

class Solution {
public:
    void Insert(char ch) {
        data.push_back(ch);
        appearCount[ch]++;
    }
    char FirstAppearingOnce() {
        deque<char>::iterator it;
        for (it = data.begin(); it != data.end(); ++it) {
            if (appearCount[*it] == 1) {
                return *it;
            }
        }
        return '#';
    }
private:
    unsigned int appearCount[256];
    deque<char> data;
};

int main() {
    Solution s;
    s.Insert('a');
    s.Insert('b');
    s.Insert('a');
    cout << s.FirstAppearingOnce() << endl;
    return 0;
}
