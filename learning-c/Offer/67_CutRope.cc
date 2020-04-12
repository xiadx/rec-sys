// 给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1），每段绳子的长度记为k[0],k[1],...,k[m]。请问k[0]xk[1]x...xk[m]可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。

#include <iostream>
#include <cmath>
using namespace std;

class Solution {
public:
    int cutRope(int number) {
        if (number == 2) {
            return 1;
        }
        if (number == 3) {
            return 2;
        }
        int x = number % 3;
        int y = number / 3;
        if (x == 0) {
            return pow(3, y);
        } else if (x == 1) {
            return 2 * 2 * pow(3, y - 1);
        } else {
            return 2 * pow(3, y);
        }
    }
};

int main() {
    Solution s;
    cout << s.cutRope(8) << endl;
    return 0;
}
