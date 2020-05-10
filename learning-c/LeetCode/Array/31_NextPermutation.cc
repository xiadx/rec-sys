// Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
//
// If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
//
// The replacement must be in-place and use only constant extra memory.
//
// Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
//
// 1,2,3 → 1,3,2
// 3,2,1 → 1,2,3
// 1,1,5 → 1,5,1

#include <iostream>
#include <vector>
using namespace std;

//class Solution {
//public:
//    void nextPermutation(vector<int> &nums) {
//        int n = nums.size();
//        int i = n - 2;
//        while (i > -1 && nums[i] >= nums[i + 1]) --i;
//        if (i == -1) {
//            reverse(nums.begin(), nums.end());
//            return;
//        }
//        int j = n - 1;
//        while (j > i && nums[j] <= nums[i]) --j;
//        swap(nums[i], nums[j]);
//        reverse(nums.begin() + i + 1, nums.end());
//    }
//};

class Solution {
public:
    void nextPermutation(vector<int> &nums) {
        int begin = 0, end = nums.size();
        int i = end - 2;
        while (i > -1 && nums[i] >= nums[i + 1]) --i;
        if (i == -1) {
            reverse(&nums[begin], &nums[end]);
            return;
        }
        int j = end - 1;
        while (j > i && nums[j] <= nums[i]) --j;
        swap(nums[i], nums[j]);
        reverse(&nums[i + 1], &nums[end]);
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
    vector<int> nums({1, 2, 3});
    Solution s;
    s.nextPermutation(nums);
    print(nums);
    return 0;
}
