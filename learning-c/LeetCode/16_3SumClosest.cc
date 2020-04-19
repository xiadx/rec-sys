// Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.
//
// Example:
//
// Given array nums = [-1, 2, 1, -4], and target = 1.
//
// The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

class Solution {
public:
    int threeSumClosest(vector<int> &nums, int target) {
        if (nums.size() < 3) return 0;
        sort(nums.begin(), nums.end());
        int result = 0;
        int minGap = INT_MAX;
        for (auto a = nums.begin(); a != prev(nums.end(), 2); ++a) {
            auto b = next(a);
            auto c = prev(nums.end());
            while (b < c) {
                int sum = *a + *b + *c;
                int gap = abs(sum - target);
                if (gap < minGap) {
                    result = sum;
                    minGap = gap;
                }
                if (sum < target) {
                    ++b;
                } else {
                    --c;
                }
            }
        }
        return result;
    }
};

int main() {
    int a[] = {-1, 2, 1, -4};
    int target = 1;
    vector<int> nums(a, a + 4);
    Solution s;
    cout << s.threeSumClosest(nums, target) << endl;
    return 0;
}
