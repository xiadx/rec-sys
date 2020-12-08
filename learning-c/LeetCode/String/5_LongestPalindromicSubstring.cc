// Given a string s, return the longest palindromic substring in s.
//
//
//
// Example 1:
//
// Input: s = "babad"
// Output: "bab"
// Note: "aba" is also a valid answer.
// Example 2:
//
// Input: s = "cbbd"
// Output: "bb"
// Example 3:
//
// Input: s = "a"
// Output: "a"
// Example 4:
//
// Input: s = "ac"
// Output: "a"
//
//
// Constraints:
//
// 1 <= s.length <= 1000
// s consist of only digits and English letters (lower-case and/or upper-case),

#include <iostream>
#include <algorithm>
#include <string>
#include <vector>
using namespace std;

//class Solution {
//public:
//    string longestPalindrome(string s) {
//        int n = s.size();
//        bool f[n][n];
//        fill_n(&f[0][0], n * n, false);
//        int start = 0, len = 1;
//        for (int i = 0; i < n; ++i) {
//            f[i][i] = true;
//            for (int j = i; j >= 0; --j) {
//                f[j][i] = s[j] == s[i] && (i - j < 2 || f[j + 1][i - 1]);
//                if (f[j][i] && i - j + 1 > len) {
//                    start = j; len = i - j + 1;
//                }
//            }
//        }
//        return s.substr(start, len);
//    }
//};

//class Solution {
//public:
//    string longestPalindrome(string s) {
//        int n = s.size();
//        vector<vector<int> > dp(n, vector<int>(n));
//        int start = 0, len = 0;
//        for (int l = 0; l < n; ++l) {
//            for (int i = 0; i + l < n; ++i) {
//                int j = i + l;
//                if (i == j) dp[i][j] = 1;
//                else if (j == i + 1) dp[i][j] = (s[i] == s[j]);
//                else dp[i][j] = (s[i] == s[j]) && (dp[i+1][j-1]);
//                if (dp[i][j] == 1 && l > len) {
//                    start = i; len = l;
//                }
//            }
//        }
//        return s.substr(start, len + 1);
//    }
//};

class Solution {
public:
    string longestPalindrome(string s) {
        int n = s.size();
        int start = 0, end = 0;
        for (int i = 0; i < n - 1; ++i) {
            auto p1 = expand(s, i, i);
            auto p2 = expand(s, i, i + 1);
            if (p1.second - p1.first > end - start) {
                start = p1.first; end = p1.second;
            }
            if (p2.second - p2.first > end - start) {
                start = p2.first; end = p2.second;
            }
        }
        return s.substr(start, end - start + 1);
    }
    pair<int, int> expand(string s, int start, int end) {
        int n = s.size();
        int left = start, right = end;
        while (left >= 0 && right <= n - 1 && s[left] == s[right]) {
            --left; ++right;
        }
        return {left + 1, right - 1};
    }
};

int main() {
    string str = "babad";
    Solution s;
    cout << s.longestPalindrome(str) << endl;
    return 0;
}
