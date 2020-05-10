// Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
//
// You may assume that the array is non-empty and the majority element always exist in the array.
//
// Example 1:
//
// Input: [3,2,3]
// Output: 3
// Example 2:
//
// Input: [2,2,1,1,1,2,2]
// Output: 2

#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

//class Solution {
//public:
//    int majorityElement(vector<int> &nums) {
//        unordered_map<int, int> m;
//        int majority = 0, cnt = 0;
//        for (int n : nums) {
//            ++m[n];
//            if (m[n] > cnt) {
//                majority = n;
//                cnt = m[n];
//            }
//        }
//        return majority;
//    }
//};

//class Solution {
//public:
//    int majorityElement(vector<int> &nums) {
//        sort(nums.begin(), nums.end());
//        return nums[nums.size() / 2];
//    }
//};

//class Solution {
//public:
//    int majorityElement(vector<int> &nums) {
//        while (true) {
//            int candidate = nums[rand() % nums.size()];
//            int count = 0;
//            for (int n : nums) {
//                if (candidate == n) {
//                    ++count;
//                }
//            }
//            if (count > nums.size() / 2) {
//                return candidate;
//            }
//        }
//        return -1;
//    }
//};

//class Solution {
//public:
//    int countInRange(vector<int> &nums, int target, int lo, int hi) {
//        int count = 0;
//        for (int i = lo; i <= hi; ++i) {
//            if (nums[i] == target) {
//                ++count;
//            }
//        }
//        return count;
//    }
//    int majorityElementRec(vector<int> &nums, int lo, int hi) {
//        if (lo == hi) return nums[lo];
//        int mid = (lo + hi) / 2;
//        int leftMajority = majorityElementRec(nums, lo, mid);
//        int rightMajority = majorityElementRec(nums, mid + 1, hi);
//        if (countInRange(nums, leftMajority, lo, hi) > (hi - lo + 1) / 2 ) {
//            return leftMajority;
//        }
//        if (countInRange(nums, rightMajority, lo, hi) > (hi - lo + 1) / 2) {
//            return rightMajority;
//        }
//        return -1;
//    }
//    int majorityElement(vector<int> &nums) {
//        return majorityElementRec(nums, 0, nums.size() - 1);
//    }
//};

class Solution {
public:
    int majorityElement(vector<int> &nums) {
        int candidate = -1;
        int count = 0;
        for (int n : nums) {
            if (n == candidate) ++count;
            else {
                if (--count < 0) {
                    candidate = n;
                    count = 1;
                }
            }
        }
        return candidate;
    }
};

int main() {
    vector<int> nums({3, 2, 3});
    Solution s;
    cout << s.majorityElement(nums) << endl;
    return 0;
}
