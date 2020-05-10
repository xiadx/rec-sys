// Given an array, rotate the array to the right by k steps, where k is non-negative.
//
// Follow up:
//
// Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
// Could you do it in-place with O(1) extra space?
//
//
// Example 1:
//
// Input: nums = [1,2,3,4,5,6,7], k = 3
// Output: [5,6,7,1,2,3,4]
// Explanation:
// rotate 1 steps to the right: [7,1,2,3,4,5,6]
// rotate 2 steps to the right: [6,7,1,2,3,4,5]
// rotate 3 steps to the right: [5,6,7,1,2,3,4]
// Example 2:
//
// Input: nums = [-1,-100,3,99], k = 2
// Output: [3,99,-1,-100]
// Explanation:
// rotate 1 steps to the right: [99,-1,-100,3]
// rotate 2 steps to the right: [3,99,-1,-100]

#include <iostream>
#include <vector>
using namespace std;

//class Solution {
//public:
//    void rotate(vector<int> &nums, int k) {
//        int n = nums.size();
//        reverse(nums.begin(), nums.begin() + (n - (k % n)));
//        reverse(nums.begin() + (n - (k % n)), nums.end());
//        reverse(nums.begin(), nums.end());
//    }
//};

//class Solution {
//public:
//    void rotate(vector<int> &nums, int k) {
//        int n = nums.size();
//        for (int i = 0; i < k; ++i) {
//            int temp = nums[n - 1];
//            for (int j = n - 1; j >= 1; --j) {
//                nums[j] = nums[j - 1];
//            }
//            nums[0] = temp;
//        }
//    }
//};

//class Solution {
//public:
//    void rotate(vector<int> &nums, int k) {
//        int n = nums.size();
//        int temp, previous;
//        for (int i = 0; i < k; ++i) {
//            int previous = nums[n - 1];
//            for (int j = 0; j < n; ++j) {
//                temp = nums[j];
//                nums[j] = previous;
//                previous = temp;
//            }
//        }
//    }
//};

//class Solution {
//public:
//    void rotate(vector<int> &nums, int k) {
//        int n = nums.size();
//        vector<int> t(n, 0);
//        for (int i = 0; i < n; ++i) {
//            t[(i + k) % n] = nums[i];
//        }
//        for (int i = 0; i < n; ++i) {
//            nums[i] = t[i];
//        }
//    }
//};

//class Solution {
//public:
//    void rotate(vector<int> &nums, int k) {
//        int n = nums.size();
//        for (int start = 0, count = 0; count < n; ++start) {
//            int current = start;
//            int pre = nums[current];
//            int next, temp;
//            do {
//                next = (current + k) % n;
//                temp = nums[next];
//                nums[next] = pre;
//                pre = temp;
//                current = next;
//                ++count;
//            } while (current != start);
//        }
//    }
//};

class Solution {
public:
    void reverse(vector<int> &nums, int lo, int hi) {
        while (lo < hi) {
            swap(nums[lo], nums[hi]);
            ++lo; --hi;
        }
    }
    void rotate(vector<int> &nums, int k) {
        int n = nums.size();
        reverse(nums, 0, n - (k % n) - 1);
        reverse(nums, n - (k % n), n - 1);
        reverse(nums, 0, n - 1);
    }
};

void print(vector<int> v) {
    for (auto it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    vector<int> nums({1, 2, 3, 4, 5, 6, 7});
    int k = 3;
    Solution s;
    s.rotate(nums, k);
    print(nums);
    return 0;
}
