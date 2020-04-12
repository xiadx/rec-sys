// 把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    int GetUglyNumber(int index) {
        if (index < 7) {
            return index;
        }
        vector<int> res(index);
        res[0] = 1;
        int t2 = 0, t3 = 0, t5 = 0, i;
        for (int i = 1; i < index; ++i) {
            res[i] = min(res[t2] * 2, min(res[t3] * 3, res[t5] * 5));
            if (res[i] == res[t2] * 2) ++t2;
            if (res[i] == res[t3] * 3) ++t3;
            if (res[i] == res[t5] * 5) ++t5;
        }
        return res[index - 1];
    }
};

int main() {
    Solution s;
    cout << s.GetUglyNumber(7) << endl;
    cout << s.GetUglyNumber(8) << endl;
    cout << s.GetUglyNumber(9) << endl;
    cout << s.GetUglyNumber(10) << endl;
    cout << s.GetUglyNumber(11) << endl;
    return 0;
}
