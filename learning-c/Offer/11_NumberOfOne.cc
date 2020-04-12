// 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。

#include <iostream>
using namespace std;

class Solution {
public:
    int NumberOf1(int n) {
        int sum = 0;
        for (int i = 0; i < 32; ++i) {
            sum += ((n >> i) & 1);
        }
        return sum;
    }
};

int main() {
    Solution s;
    cout << s.NumberOf1(3) << endl;
    return 0;
}
