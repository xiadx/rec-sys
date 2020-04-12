// 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。

#include <iostream>
using namespace std;

class Solution {
public:
    int Sum(int n) {
        return (1 + n) * n / 2;
    }
};

int main() {
    Solution s;
    cout << s.Sum(5) << endl;
    return 0;
}
