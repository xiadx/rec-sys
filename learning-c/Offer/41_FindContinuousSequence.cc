// 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
// 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    vector<vector<int> > FindContinuousSequence(int sum) {
        int l = 1, r = 1, sumx = 1;
        vector<vector<int> > v;
        while (l <= r) {
            r++;
            sumx += r;
            while (sumx > sum) {
                sumx -= l;
                l++;
            }
            if (sumx == sum && l != r) {
                vector<int> t;
                for (int i = l; i <= r; ++i) {
                    t.push_back(i);
                }
                v.push_back(t);
            }
        }
        return v;
    }
};

void print(vector<vector<int> > v) {
    for (int i = 0; i < v.size(); ++i) {
        for (int j = 0; j < v[i].size(); ++j) {
            cout << v[i][j] << " ";
        }
        cout << endl;
    }
}

int main() {
    Solution s;
    print(s.FindContinuousSequence(100));
    return 0;
}
