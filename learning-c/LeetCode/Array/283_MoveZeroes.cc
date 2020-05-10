// Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
//
// Example:
//
// Input: [0,1,0,3,12]
// Output: [1,3,12,0,0]
// Note:
//
// You must do this in-place without making a copy of the array.
// Minimize the total number of operations.

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    void moveZeroes(vector<int> &nums) {
        int index = 0;
        int n = nums.size();
        for (int i = 0; i < n; ++i) {
            if (nums[i] != 0) {
                nums[index++] = nums[i];
            }
        }
        while (index < n) nums[index++] = 0;
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
    vector<int> nums({0, 1, 0, 3, 12});
    Solution s;
    s.moveZeroes(nums);
    print(nums);
    return 0;
}
