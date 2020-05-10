// Given an array of integers, find if the array contains any duplicates.
//
// Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.
//
// Example 1:
//
// Input: [1,2,3,1]
// Output: true
// Example 2:
//
// Input: [1,2,3,4]
// Output: false
// Example 3:
//
// Input: [1,1,1,3,3,4,3,2,4,2]
// Output: true

#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

//class Solution {
//public:
//    bool containsDuplicate(vector<int> &nums) {
//        unordered_map<int, int> m;
//        for (int n : nums) {
//            if (++m[n] > 1) return true;
//        }
//        return false;
//    }
//};

//class Solution {
//public:
//    bool containsDuplicate(vector<int> &nums) {
//        sort(nums.begin(), nums.end());
//        auto it = unique(nums.begin(), nums.end());
//        if (it != nums.end()) return true;
//        else return false;
//    }
//};

class Solution {
public:
    bool containsDuplicate(vector<int> &nums) {
        sort(nums.begin(), nums.end());
        int n = nums.size();
        for (int i = 0; i < n - 1; ++i) {
            if (nums[i] == nums[i + 1]) return true;
        }
        return false;
    }
};

int main() {
    vector<int> nums({1, 2, 3, 1});
    Solution s;
    cout << s.containsDuplicate(nums) << endl;
    return 0;
}
