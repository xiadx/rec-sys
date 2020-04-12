// 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    bool VerifySquenceOfBST(vector<int> sequence) {
        int length = sequence.size();
        if (length == 0) {
            return false;
        }
        int i = 0;
        while (--length) {
            while (sequence[i++] < sequence[length]);
            while (sequence[i++] > sequence[length]);
            if (i < length) {
                return false;
            }
            i = 0;
        }
        return true;
    }
};

int main() {
    int a[] = {1, 2, 5, 3, 4};
    int b[] = {1, 2, 5, 4, 3};
    vector<int> x(a, a + 5);
    vector<int> y(b, b + 5);
    Solution s;
    cout << s.VerifySquenceOfBST(x) << endl;
    cout << s.VerifySquenceOfBST(y) << endl;
    return 0;
}
