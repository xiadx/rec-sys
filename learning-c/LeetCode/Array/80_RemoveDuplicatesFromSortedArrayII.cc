// Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return the new length.
//
// Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
//
// Example 1:
//
// Given nums = [1,1,1,2,2,3],
//
// Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.
//
// It doesn't matter what you leave beyond the returned length.
// Example 2:
//
// Given nums = [0,0,1,1,1,1,2,3,3],
//
// Your function should return length = 7, with the first seven elements of nums being modified to 0, 0, 1, 1, 2, 3 and 3 respectively.
//
// It doesn't matter what values are set beyond the returned length.

#include <iostream>
#include <vector>
using namespace std;

//// Revove Duplicates from Sorted Array II
//// Time Complexity: O(n), Space Complexity: O(1)
//class Solution {
//public:
//    int removeDuplicates(vector<int> &nums) {
//        if (nums.size() <= 2) return nums.size();
//        int index = 2;
//        for (int i = 2; i < nums.size(); ++i) {
//            if (nums[i] != nums[index - 2]) {
//                nums[index++] = nums[i];
//            }
//        }
//        return index;
//    }
//};

// Revove Duplicates from Sorted Array II
// Time Complexity: O(n), Space Complexity: O(1)
class Solution {
public:
    int removeDuplicates(vector<int> &nums) {
        int index = 0;
        for (int i = 0; i < nums.size(); ++i) {
            if (i > 0 && i < nums.size() - 1 && nums[i] == nums[i - 1] && nums[i] == nums[i + 1]) continue;
            nums[index++] = nums[i];
        }
        return index;
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
    int a[] = {1, 1, 1, 2, 2, 3};
    vector<int> nums(a, a + 6);
    Solution s;
    cout << s.removeDuplicates(nums) << endl;
    print(nums);
    return 0;
}
