// Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
//
// Your algorithm should run in O(n) complexity.
//
// Example:
//
// Input: [100, 4, 200, 1, 3, 2]
// Output: 4
// Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

#include <iostream>
#include <vector>
#include <unordered_set>
using namespace std;

// Longest Consecutive Sequence
// Time Complexity: O(n), Space Complexity: O(1)
class Solution {
public:
    int longestConsecutive(vector<int> &nums) {
        unordered_set<int> my_set;
        for (auto i : nums) {
            my_set.insert(i);
        }
        int longest = 0;
        for (auto i : nums) {
            int length = 1;
            for (int j = i - 1; my_set.find(j) != my_set.end(); --j) {
                my_set.erase(j);
                ++length;
            }
            for (int j = i + 1; my_set.find(j) != my_set.end(); ++j) {
                my_set.erase(j);
                ++length;
            }
            longest = max(longest, length);
        }
        return longest;
    }
};

int main() {
    int a[] = {100, 4, 200, 1, 3, 2};
    vector<int> nums(a, a + 6);
    Solution s;
    cout << s.longestConsecutive(nums) << endl;
    return 0;
}
