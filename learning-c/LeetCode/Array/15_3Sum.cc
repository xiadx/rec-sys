// Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
//
// Note:
//
// The solution set must not contain duplicate triplets.
//
// Example:
//
// Given array nums = [-1, 0, 1, 2, -1, -4],
//
// A solution set is:
// [
//   [-1, 0, 1],
//   [-1, -1, 2]
// ]

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

class Solution {
public:
    vector<vector<int> > threeSum(vector<int> &nums) {
        vector<vector<int> > r;
        if (nums.size() < 3) return r;
        sort(nums.begin(), nums.end());
        int target = 0;
        for (auto i = nums.begin(); i < nums.end() - 2; ++i) {
            if (i > nums.begin() && *i == *(i - 1)) continue;
            auto j = i + 1;
            auto k = nums.end() - 1;
            while (j < k) {
                if (*i + *j + *k < target) {
                    ++j;
                    while (*j == *(j - 1) && j < k) ++j;
                } else if (*i + *j + *k > target) {
                    --k;
                    while (*k == *(k + 1) && j < k) --k;
                } else {
                    r.push_back({*i, *j, *k});
                    ++j; --k;
                    while (*j == *(j - 1) && j < k) ++j;
                    while (*k == *(k + 1) && j < k) --k;
                }
            }
        }
        return r;
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
    int a[] = {-1, 0, 1, 2, -1, -4};
    vector<int> v(a, a + 6);
    Solution s;
    print(s.threeSum(v));
    return 0;
}
