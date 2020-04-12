// 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。

#include <iostream>
using namespace std;

class Solution {
public:
    double Power(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        } else if (exponent > 0) {
            if (exponent & 1) {
                return base * Power(base, (int)(exponent / 2)) * Power(base, (int)(exponent / 2));
            } else {
                return Power(base, (int)(exponent / 2)) * Power(base, (int)(exponent / 2));
            }
        } else {
            return 1.0 / Power(base, -exponent);
        }
    }
};

int main() {
    Solution s;
    cout << s.Power(2, -1) << endl;
    cout << s.Power(2, 3) << endl;
    return 0;
}
