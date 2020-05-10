// Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
//
// Example 1:
//
// Input: nums = [1,2,3,1], k = 3
// Output: true
// Example 2:
//
// Input: nums = [1,0,1,1], k = 1
// Output: true
// Example 3:
//
// Input: nums = [1,2,3,1,2,3], k = 2
// Output: false

#include <iostream>
#include <vector>
#include <unordered_set>
using namespace std;

//class Solution {
//public:
//    bool containsNearbyDuplicate(vector<int> &nums, int k) {
//        int n = nums.size();
//        for (int i = 0 ;i < n; ++i) {
//            for (int j = max(i - k, 0); j < i; ++j) {
//                if (nums[j] == nums[i]) return true;
//            }
//        }
//        return false;
//    }
//};

class Solution {
public:
    bool containsNearbyDuplicate(vector<int> &nums, int k) {
        int n = nums.size();
        unordered_set<int> s;
        for (int i = 0 ;i < n; ++i) {
            if (s.find(nums[i]) != s.end()) return true;
            s.insert(nums[i]);
            if (s.size() > k) s.erase(nums[i - k]);
        }
        return false;
    }
};

int main() {
    vector<int> nums({1, 2, 3, 1});
    int k = 3;
    Solution s;
    cout << s.containsNearbyDuplicate(nums, k) << endl;
    return 0;
}
