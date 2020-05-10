// Given an array of integers, find out whether there are two distinct indices i and j in the array such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.
//
// Example 1:
//
// Input: nums = [1,2,3,1], k = 3, t = 0
// Output: true
// Example 2:
//
// Input: nums = [1,0,1,1], k = 1, t = 2
// Output: true
// Example 3:
//
// Input: nums = [1,5,9,1,5,9], k = 2, t = 3
// Output: false

#include <iostream>
#include <vector>
#include <set>
#include <unordered_map>
using namespace std;

//class Solution {
//public:
//    bool containsNearbyAlmostDuplicate(vector<int> &nums, int k, int t) {
//        int n = nums.size();
//        for (int i = 0; i < n; ++i) {
//            for (int j = max(i - k, 0); j < i; ++j) {
//                if (abs(nums[j] - nums[i]) <= t) return true;
//            }
//        }
//        return false;
//    }
//};

//class Solution {
//public:
//    bool containsNearbyAlmostDuplicate(vector<int> &nums, int k, int t) {
//        int n = nums.size();
//        set<int> s;
//        for (int i = 0; i < n; ++i) {
//            auto g = s.lower_bound(nums[i]);
//            if (g != s.end() && (long)*g - (long)nums[i] <= t) return true;
//            auto l = s.upper_bound(nums[i]);
//            if (l != s.begin() && (long)nums[i] - (long)*(--l) <= t) return true;
//            s.insert(nums[i]);
//            if (s.size() > k) s.erase(nums[i - k]);
//        }
//        return false;
//    }
//};

class Solution {
public:
    long getBucket(long x, long w) {
        return x < 0 ? (x + 1) / w - 1 : x / w;
    }
    bool containsNearbyAlmostDuplicate(vector<int> &nums, int k, int t) {
        if (t < 0) return false;
        int n = nums.size();
        long w = (long)t + 1;
        unordered_map<long, long> m;
        for (int i = 0; i < n; ++i) {
           long b = getBucket(nums[i], w);
            if (m.find(b) != m.end()) return true;
            if (m.find(b - 1) != m.end() && abs(m[b - 1] - nums[i]) <= t) return true;
            if (m.find(b + 1) != m.end() && abs(m[b + 1] - nums[i]) <= t) return true;
            m[b] = nums[i];
            if (m.size() > k) m.erase(getBucket(nums[i - k], w));
        }
        return false;
    }
};

int main() {
    vector<int> nums({1, 2, 3, 1});
    int k = 3, t = 0;
    Solution s;
    cout << s.containsNearbyAlmostDuplicate(nums, k, t) << endl;
    return 0;
}
