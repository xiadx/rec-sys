// You are climbing a stair case. It takes n steps to reach to the top.
//
// Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
//
// Note: Given n will be a positive integer.
//
// Example 1:
//
// Input: 2
// Output: 2
// Explanation: There are two ways to climb to the top.
// 1. 1 step + 1 step
// 2. 2 steps
// Example 2:
//
// Input: 3
// Output: 3
// Explanation: There are three ways to climb to the top.
// 1. 1 step + 1 step + 1 step
// 2. 1 step + 2 steps
// 3. 2 steps + 1 step

#include <iostream>
#include <cmath>
using namespace std;

//class Solution {
//public:
//    int climbStairs(int n) {
//        int prev = 0, cur = 1;
//        for (int i = 1; i <= n; ++i) {
//            int tmp = cur;
//            cur += prev;
//            prev = tmp;
//        }
//        return cur;
//    }
//};

class Solution {
public:
    int climbStairs(int n) {
        double s = sqrt(5);
        return floor((pow((1+s)/2, n+1) + pow((1-s)/2, n+1)) / s + 0.5);
    }
};

int main() {
    Solution s;
    cout << s.climbStairs(3) << endl;
    return 0;
}
