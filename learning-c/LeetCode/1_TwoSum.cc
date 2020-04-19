// Given an array of integers, return indices of the two numbers such that they add up to a specific target.
//
// You may assume that each input would have exactly one solution, and you may not use the same element twice.
//
// Example:
//
// Given nums = [2, 7, 11, 15], target = 9,
//
// Because nums[0] + nums[1] = 2 + 7 = 9,
// return [0, 1].

#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

//// Two Sum
//// Time Complexity: O(n), Space Complexity: O(n)
//class Solution {
//public:
//    vector<int> twoSum(vector<int> &nums, int target) {
//        unordered_map<int, int> my_map;
//        vector<int> result;
//        for (int i = 0; i < nums.size(); ++i) {
//            my_map[nums[i]] = i;
//        }
//        for (int i = 0; i < nums.size(); ++i) {
//            auto iter = my_map.find(target - nums[i]);
//            if (iter != my_map.end() && iter->second > i) {
//                result.push_back(i);
//                result.push_back(iter->second);
//                break;
//            }
//        }
//        return result;
//    }
//};

// Two Sum
// Time Complexity: O(n), Space Complexity: O(n)
class Solution {
public:
    vector<int> twoSum(vector<int> &nums, int target) {
        unordered_map<int, int> my_map;
        vector<int> result;
        for (int i = 0; i < nums.size(); ++i) {
            auto iter = my_map.find(target - nums[i]);
            if (iter != my_map.end()) {
                result.push_back(iter->second);
                result.push_back(i);
                break;
            } else {
                my_map[nums[i]] = i;
            }
        }
        return result;
    }
};

void print(vector<int> v) {
    vector<int>::iterator it;
    for (it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    int a[] = {2, 7, 11, 15};
    vector<int> nums(a, a + 4);
    Solution s;
    print(s.twoSum(nums, 9));
    return 0;
}
