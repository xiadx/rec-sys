// 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）

#include <iostream>
using namespace std;

class Solution {
public:
    int jumpFloor(int number) {
        int x = 1, y = 1;
        for (int i = 0; i < number; ++i) {
            int t = x;
            x = y;
            y = t + y;
        }
        return x;
    }
};

int main() {
    Solution s;
    cout << s.jumpFloor(5) << endl;
    return 0;
}
