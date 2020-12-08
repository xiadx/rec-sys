// Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*' where:
//
// '.' Matches any single character.​​​​
// '*' Matches zero or more of the preceding element.
// The matching should cover the entire input string (not partial).
//
//
//
// Example 1:
//
// Input: s = "aa", p = "a"
// Output: false
// Explanation: "a" does not match the entire string "aa".
// Example 2:
//
// Input: s = "aa", p = "a*"
// Output: true
// Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
// Example 3:
//
// Input: s = "ab", p = ".*"
// Output: true
// Explanation: ".*" means "zero or more (*) of any character (.)".
// Example 4:
//
// Input: s = "aab", p = "c*a*b"
// Output: true
// Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
// Example 5:
//
// Input: s = "mississippi", p = "mis*is*p*."
// Output: false
//
//
// Constraints:
//
// 0 <= s.length <= 20
// 0 <= p.length <= 30
// s contains only lowercase English letters.
// p contains only lowercase English letters, '.', and '*'.

#include <iostream>
#include <string>
using namespace std;

//class Solution {
//public:
//    bool isMatch(string s, string p) {
//        return isMatch(s.c_str(), p.c_str());
//    }
//    bool matchFirst(const char *s, const char *p) {
//        return (*p == *s) || (*p == '.' && *s != '\0');
//    }
//    bool isMatch(const char *s, const char *p) {
//        if (*p == '\0') return *s == '\0';
//        if (*(p+1) != '*') return matchFirst(s, p) && isMatch(s+1, p+1);
//        else {
//            if (isMatch(s, p+2)) return true;
//            while (matchFirst(s, p)) {
//                ++s;
//                if (isMatch(s, p+2)) return true;
//            }
//            return false;
//        }
//    }
//};

//class Solution {
//public:
//    bool isMatch(string s, string p) {
//        return isMatch(s.c_str(), p.c_str());
//    }
//    bool matchFirst(const char *s, const char *p) {
//        return (*p == *s) || (*p == '.' && *s != '\0');
//    }
//    bool isMatch(const char *s, const char *p) {
//        if (*p == '\0') return *s == '\0';
//        if (*(p+1) != '*') return matchFirst(s, p) && isMatch(s+1, p+1);
//        else {
//            if (!matchFirst(s, p)) return isMatch(s, p+2);
//            else return isMatch(s, p+2) || isMatch(s+1, p);
//        }
//    }
//};

class Solution {
public:
    bool isMatch(string s, string p) {
        int m = s.size(), n = p.size();
        bool dp[m+1][n+1];
        fill_n(&dp[0][0], (m+1) * (n+1), false);
        dp[0][0] = true;
        for (int i = 0; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p[j-1] == '*') {
                    dp[i][j] = (j >= 2 && dp[i][j-2]) || (j >= 2 && i >= 1 && match(s, p, i, j-1) && dp[i-1][j]);
                } else {
                    dp[i][j] = match(s, p, i, j) && dp[i-1][j-1];
                }
            }
        }
        return dp[m][n];
    }
    bool match(string s, string p, int i, int j) {
        if (i == 0) return false;
        if (p[j-1] == '.') return true;
        return s[i-1] == p[j-1];
    }
};

int main() {
    string text = "aab", pattern = "c*a*b";
    Solution s;
    cout << s.isMatch(text, pattern) << endl;
}
