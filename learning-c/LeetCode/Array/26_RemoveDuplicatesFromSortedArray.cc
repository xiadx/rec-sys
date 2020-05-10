// Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.
//
// Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
//
// Example 1:
//
// Given nums = [1,1,2],
//
// Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
//
// It doesn't matter what you leave beyond the returned length.
// Example 2:
//
// Given nums = [0,0,1,1,1,2,2,3,3,4],
//
// Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.
//
// It doesn't matter what values are set beyond the returned length.

#include <iostream>
#include <vector>
using namespace std;

// Remove Duplicates form Sorted Array
// Time Complexity: O(n), Space Complexity: O(1)
class Solution {
public:
    int removeDuplicates(vector<int> &nums) {
        if (nums.empty()) return 0;
        int index = 1;
        for (int i = 1; i < nums.size(); ++i) {
            if (nums[i] != nums[index - 1]) {
                nums[index++] = nums[i];
            }
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
    int a[] = {1, 1, 2};
    vector<int> nums(a, a + 3);
    Solution s;
    cout << s.removeDuplicates(nums) << endl;
    print(nums);
    return 0;
}
