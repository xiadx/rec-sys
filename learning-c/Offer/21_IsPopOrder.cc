// 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）

#include <iostream>
#include <vector>
#include <stack>
using namespace std;

class Solution {
public:
    bool IsPopOrder(vector<int> pushV, vector<int> popV) {
        stack<int> s;
        int n = pushV.size();
        if (n == 0) {
            return false;
        }
        int j = 0;
        for (int i = 0; i < n; ++i) {
            s.push(pushV[i]);
            while (j < n && s.top() == popV[j]) {
                s.pop();
                ++j;
            }
        }
        if (s.empty()) {
            return true;
        } else {
            return false;
        }
    }
};

int main() {
    int a[] = {1, 2, 3, 4, 5};
    int b[] = {4, 3, 5, 1, 2};
    vector<int> pushV(a, a + 5);
    vector<int> popV(b, b + 5);
    Solution s;
    cout << s.IsPopOrder(pushV, popV) << endl;
    return 0;
}
