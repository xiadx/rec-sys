// 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    int MoreThanHalfNum(vector<int> numbers) {
        if (numbers.empty()) {
            return 0;
        }
        int result = numbers[0];
        int times = 1;
        for (int i = 1; i < numbers.size(); ++i) {
            if (times == 0) {
                result = numbers[i];
                times = 1;
            } else if (numbers[i] == result) {
                ++times;
            } else {
                --times;
            }
        }
        times = 0;
        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers[i] == result) {
                ++times;
            }
        }
        return (times > numbers.size() / 2) ? result : 0;
    }
};

int main() {
    int a[] = {1};
    int b[] = {1, 2};
    int c[] = {1, 2, 3, 2, 2, 2, 5, 4, 2};
    vector<int> x(a, a + 1);
    vector<int> y(b, b + 2);
    vector<int> z(c, c + 9);
    Solution s;
    cout << s.MoreThanHalfNum(x) << endl;
    cout << s.MoreThanHalfNum(y) << endl;
    cout << s.MoreThanHalfNum(z) << endl;
    return 0;
}
