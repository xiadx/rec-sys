// 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。

#include <iostream>
using namespace std;

class Solution {
public:
    int jumpFloorII(int number) {
        return 1 << (number - 1);
    }
};

int main() {
    Solution s;
    cout << s.jumpFloorII(2) << endl;
    return 0;
}
