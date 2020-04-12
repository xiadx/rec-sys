// 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    vector<int> maxInWindows(const vector<int> &num, unsigned int size) {
        vector<int> max;
        if (num.empty() || size > num.size() || size < 1) {
            return max;
        }
        int m;
        for (int i = 0; i < num.size() - size + 1; ++i) {
            m = num[i];
            for (int j = i + 1; j < i + size; ++j) {
                if (num[j] > m) {
                    m = num[j];
                }
            }
            max.push_back(m);
        }
        return max;
    }
};

void print(vector<int> v) {
    vector<int>::iterator it;
    for (it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    int a[] = {2, 3, 4, 2, 6, 2, 5, 1};
    vector<int> v(a, a + 8);
    Solution s;
    print(s.maxInWindows(v, 3));
    return 0;
}
