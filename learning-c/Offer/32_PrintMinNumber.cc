// 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    static bool cmp(int a, int b) {
        string A = to_string(a) + to_string(b);
        string B = to_string(b) + to_string(a);
        return A < B;
    }
    string PrintMinNumber(vector<int> numbers) {
        int l = numbers.size();
        if (l == 0) {
            return "";
        }
        sort(numbers.begin(), numbers.end(), cmp);
        string res;
        for (int i = 0; i < l; ++i) {
            res += to_string(numbers[i]);
        }
        return res;
    }
};

int main() {
    int a[] = {3, 32, 321};
    vector<int> v(a, a + 3);
    Solution s;
    cout << s.PrintMinNumber(v) << endl;
    return 0;
}
