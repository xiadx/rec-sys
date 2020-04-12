// 我们可以用21的小矩形横着或者竖着去覆盖更大的矩形。请问用n个21的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？

#include <iostream>
using namespace std;

class Solution {
public:
    int rectCover(int number) {
        if (number == 0) {
            return 0;
        }
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
    cout << s.rectCover(1) << endl;
    cout << s.rectCover(2) << endl;
    return 0;
}
