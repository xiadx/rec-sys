// Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
//
// Example:
//
// Input:  [1,2,3,4]
// Output: [24,12,8,6]
// Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array (including the whole array) fits in a 32 bit integer.
//
// Note: Please solve it without division and in O(n).
//
// Follow up:
// Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)

#include <iostream>
#include <vector>
using namespace std;

//class Solution {
//public:
//    vector<int> productExceptSelf(vector<int> &nums) {
//        int n = nums.size();
//        vector<int> a(n, 1);
//        vector<int> b(n, 1);
//        vector<int> c(n, 0);
//        if (n == 0) return c;
//        a[0] = 1; b[n - 1] = 1;
//        for (int i = 1; i < n; ++i) {
//            a[i] = a[i - 1] * nums[i - 1];
//        }
//        for (int i = n - 2; i >= 0; --i) {
//            b[i] = b[i + 1] * nums[i + 1];
//        }
//        for (int i = 0; i < n; ++i) {
//            c[i] = a[i] * b[i];
//        }
//        return c;
//    }
//};

class Solution {
public:
    vector<int> productExceptSelf(vector<int> &nums) {
        int n = nums.size();
        vector<int> a(n, 0);
        if (n == 0) return a;
        a[0] = 1;
        for (int i = 1; i < n; ++i) {
            a[i] = a[i - 1] * nums[i - 1];
        }
        int r = 1;
        for (int i = n - 1; i >= 0; --i) {
            a[i] *= r;
            r *= nums[i];
        }
        return a;
    }
};

void print(vector<int> v) {
    for (auto it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    vector<int> nums({1, 2, 3, 4});
    Solution s;
    print(s.productExceptSelf(nums));
    return 0;
}
