// Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
//
// '?' Matches any single character.
// '*' Matches any sequence of characters (including the empty sequence).
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
// Input: s = "aa", p = "*"
// Output: true
// Explanation: '*' matches any sequence.
// Example 3:
//
// Input: s = "cb", p = "?a"
// Output: false
// Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
// Example 4:
//
// Input: s = "adceb", p = "*a*b"
// Output: true
// Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
// Example 5:
//
// Input: s = "acdcb", p = "a*c?b"
// Output: false
//
//
// Constraints:
//
// 0 <= s.length, p.length <= 2000
// s contains only lowercase English letters.
// p contains only lowercase English letters, '?' or '*'.

#include <iostream>
#include <string>
using namespace std;

//class Solution {
//public:
//    bool isMatch(string s, string p) {
//        return isMatch(s.c_str(), p.c_str());
//    }
//    bool isMatch(const char *s, const char *p) {
//        if (*s == '\0' && *p == '\0') return true;
//        if (*s != '\0' && *p == '\0') return false;
//        else if (*p == '*') {
//            while (*p == '*') ++p;
//            if (*p == '\0') return true;
//            while (*s != '\0' && !isMatch(s, p)) ++s;
//            return *s != '\0';
//        }
//        else if (*s == *p || (*s != '\0' && *p == '?')) return isMatch(++s, ++p);
//        else return false;
//    }
//};

//class Solution {
//public:
//    bool isMatch(string s, string p) {
//        int m = s.size(), n = p.size();
//        bool dp[m+1][n+1];
//        fill_n(&dp[0][0], (m+1) * (n+1), false);
//        dp[0][0] = true;
//        for (int i = 0; i <= m; ++i) {
//            for (int j = 1; j <= n; ++j) {
//                if (p[j-1] == '*') {
//                    dp[i][j] = dp[i][j-1] || (i >= 1 && dp[i-1][j]);
//                } else if ((i >= 1 && s[i-1] == p[j-1]) || (i >= 1 && p[j-1] == '?')) {
//                    dp[i][j] = dp[i-1][j-1];
//                } else {
//                    dp[i][j] = false;
//                }
//            }
//        }
//        return dp[m][n];
//    }
//};

//class Solution {
//public:
//    bool isMatch(string s, string p) {
//        int m = s.size(), n = p.size();
//        bool dp[m+1][n+1];
//        fill_n(&dp[0][0], (m+1) * (n+1), false);
//        dp[0][0] = true;
//        for (int i = 0; i <= m; ++i) {
//            for (int j = 1; j <= n; ++j) {
//                if (p[j-1] == '*') {
//                    dp[i][j] = dp[i][j-1] || (i >= 1 && dp[i-1][j]);
//                } else {
//                    dp[i][j] = ((i >= 1 && s[i-1] == p[j-1]) || (i >= 1 && p[j-1] == '?')) && dp[i-1][j-1];
//                }
//            }
//        }
//        return dp[m][n];
//    }
//};

class Solution {
public:
    bool isMatch(string s, string p) {
        return isMatch(s.c_str(), p.c_str());
    }
    bool isMatch(const char *s, const char *p) {
        bool star = false;
        const char *str, *ptr;
        for (str = s, ptr = p; *str != '\0'; str++, ptr++) {
            switch(*ptr) {
                case '?':
                    break;
                case '*':
                    star = true;
                    s = str; p = ptr;
                    while (*p == '*') p++;
                    if (*p == '\0') return true;
                    str = s - 1;
                    ptr = p - 1;
                    break;
                default:
                    if (*str != *ptr) {
                        if (!star) return false;
                        s++;
                        str = s - 1;
                        ptr = p - 1;
                    }
            }
        }
        while (*ptr == '*') ptr++;
        return (*ptr == '\0');
    }
};

int main() {
    string text = "abc", pattern = "*abc?*";
    Solution s;
    cout << s.isMatch(text, pattern) << endl;
    return 0;
}
