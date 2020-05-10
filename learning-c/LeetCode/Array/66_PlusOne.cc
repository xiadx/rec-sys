// Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
//
// The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.
//
// You may assume the integer does not contain any leading zero, except the number 0 itself.
//
// Example 1:
//
// Input: [1,2,3]
// Output: [1,2,4]
// Explanation: The array represents the integer 123.
// Example 2:
//
// Input: [4,3,2,1]
// Output: [4,3,2,2]
// Explanation: The array represents the integer 4321.

#include <iostream>
#include <vector>
using namespace std;

//class Solution {
//public:
//    vector<int> plusOne(vector<int> &digits) {
//        int c = 1;
//        for (vector<int>::reverse_iterator it = digits.rbegin(); it != digits.rend(); ++it) {
//            *it += c;
//            c = *it / 10;
//            *it %= 10;
//        }
//        if (c > 0) digits.insert(digits.begin(), 1);
//        return digits;
//    }
//};

class Solution {
public:
    vector<int> plusOne(vector<int> &digits) {
        int c = 1;
        for (auto it = digits.rbegin(); it != digits.rend(); ++it) {
            *it += c;
            c = *it / 10;
            *it %= 10;
        }
        if (c > 0) digits.insert(digits.begin(), 1);
        return digits;
    }
};

void print(vector<int> v) {
    for (int i = 0; i < v.size(); ++i) {
        cout << v[i] << " ";
    }
    cout << endl;
}

int main() {
    vector<int> digits({1, 2, 3});
    Solution s;
    print(s.plusOne(digits));
    return 0;
}
