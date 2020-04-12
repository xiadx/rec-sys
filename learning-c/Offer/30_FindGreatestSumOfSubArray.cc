// HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,他又发话了:在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)

#include <iostream>
#include <vector>
using namespace std;

//class Solution {
//public:
//    int FindGreatestSumOfSubArray(vector<int> array) {
//        if (array.empty()) {
//            return 0;
//        }
//        vector<int> dp(array.size());
//        dp[0] = array[0];
//        for (int i = 1; i < array.size(); ++i) {
//            dp[i] = max(array[i], array[i] + dp[i - 1]);
//        }
//        return *max_element(dp.begin(), dp.end());
//    }
//};

class Solution {
public:
    int FindGreatestSumOfSubArray(vector<int> array) {
        if (array.empty()) {
            return 0;
        }
        int total = array[0], maxSum = array[0];
        for (int i = 1; i < array.size(); ++i) {
            if (total >= 0) total += array[i];
            else total = array[i];
            if (total > maxSum) maxSum = total;
        }
        return maxSum;
    }
};

int main() {
    int a[] = {6, -3, -2, 7, -15, 1, 2, 2};
    vector<int> v(a, a + 8);
    Solution s;
    cout << s.FindGreatestSumOfSubArray(v) << endl;
    return 0;
}
