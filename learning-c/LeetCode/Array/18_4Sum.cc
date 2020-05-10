// Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
//
// Note:
//
// The solution set must not contain duplicate quadruplets.
//
// Example:
//
// Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
//
// A solution set is:
// [
//   [-1,  0, 0, 1],
//   [-2, -1, 1, 2],
//   [-2,  0, 0, 2]
// ]

#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>
using namespace std;

//class Solution {
//public:
//    vector<vector<int> > fourSum(vector<int> &nums, int target) {
//        vector<vector<int> > r;
//        int n = nums.size();
//        if (n < 4) return r;
//        sort(nums.begin(), nums.end());
//        for (int i = 0; i < n - 3; ++i) {
//            if (i > 0 && nums[i] == nums[i - 1]) continue;
//            for (int j = i + 1; j < n - 2; ++j) {
//                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
//                int k = j + 1;
//                int l = n - 1;
//                while (k < l) {
//                    int sum = nums[i] + nums[j] + nums[k] + nums[l];
//                    if (sum < target) {
//                        ++k;
//                        while (nums[k] == nums[k - 1] && k < l) ++k;
//                    } else if (sum > target) {
//                        --l;
//                        while (nums[l] == nums[l + 1] && k < l) --l;
//                    } else {
//                        r.push_back({nums[i], nums[j], nums[k], nums[l]});
//                        ++k; --l;
//                        while (nums[k] == nums[k - 1] && k < l) ++k;
//                        while (nums[l] == nums[l + 1] && k < l) --l;
//                    }
//                }
//            }
//        }
//        return r;
//    }
//};

class Solution {
public:
    vector<vector<int> > fourSum(vector<int> &nums, int target) {
        vector<vector<int> > r;
        int n = nums.size();
        if (n < 4) return r;
        sort(nums.begin(), nums.end());
        unordered_map<int, vector<pair<int, int>>> cache;
        for (int a = 0; a < n; ++a) {
            for (int b = a + 1; b < n; ++b) {
                cache[nums[a] + nums[b]].push_back(pair<int, int>(a, b));
            }
        }
        for (int c = 0; c < n; ++c) {
            for (int d = c + 1; d < n; ++d) {
                int key = target - nums[c] - nums[d];
                if (cache.find(key) == cache.end()) continue;
                auto vec = cache[key];
                for (int k = 0; k < vec.size(); ++k) {
                    if (c <= vec[k].second) continue;
                    r.push_back({nums[vec[k].first], nums[vec[k].second], nums[c], nums[d]});
                }
            }
        }
        sort(r.begin(), r.end());
        r.erase(unique(r.begin(), r.end()), r.end());
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
    int a[] = {1, 0, -1, -1, 0, -2, 2};
    int target = 0;
    vector<int> nums(a, a + 7);
    Solution s;
    print(s.fourSum(nums, target));
    return 0;
}
