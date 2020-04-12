// 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。n<=39

#include <iostream>
using namespace std;

class Solution {
public:
    int Fibonacci(int n) {
        int x = 0, y = 1;
        for (int i = 0; i < n; ++i) {
            int t = x;
            x = y;
            y = t + y;
        }
        return x;
    }
};

int main() {
    Solution s;
    cout << s.Fibonacci(5) << endl;
    return 0;
}
