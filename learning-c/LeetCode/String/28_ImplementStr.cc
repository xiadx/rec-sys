// Implement strStr().
//
// Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
//
// Clarification:
//
// What should we return when needle is an empty string? This is a great question to ask during an interview.
//
// For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
//
//
//
// Example 1:
//
// Input: haystack = "hello", needle = "ll"
// Output: 2
// Example 2:
//
// Input: haystack = "aaaaa", needle = "bba"
// Output: -1
// Example 3:
//
// Input: haystack = "", needle = ""
// Output: 0
//
//
// Constraints:
//
// 0 <= haystack.length, needle.length <= 5 * 104
// haystack and needle consist of only lower-case English characters.

#include <iostream>
#include <string>
#include <vector>
using namespace std;

//class Solution {
//public:
//    int strStr(string haystack, string needle) {
//        int l = haystack.size(), n = needle.size();
//        if (n == 0) return 0;
//        for (int i = 0; i < l - n + 1; ++i) {
//            int j = i, k = 0;
//            while (j < l && k < n && haystack[j] == needle[k]) {
//                ++j; ++k;
//            }
//            if (k == n) return i;
//        }
//        return -1;
//    }
//};

//class Solution {
//public:
//    int strStr(string haystack, string needle) {
//        int l = haystack.size(), n = needle.size();
//        int i = 0, j = 0;
//        while (i < l - n + 1) {
//            while (i < l && j < n && haystack[i] == needle[j]) {
//                ++i; ++j;
//            }
//            if (j == n) return i - n;
//            else {
//                i = i - j + 1; j = 0;
//            }
//        }
//        return -1;
//    }
//};

//class Solution {
//public:
//    int strStr(string haystack, string needle) {
//        return kmp(haystack, needle);
//    }
//    int kmp(string s1, string s2) {
//        int l = s1.size(), n = s2.size();
//        vector<int> next = get_next(s2);
//        int i = 0, j = 0;
//        while (i < l && j < n) {
//            if (j == -1 || s1[i] == s2[j]) {
//                ++i; ++j;
//            } else {
//                j = next[j];
//            }
//        }
//        return j == n ? i - j : -1;
//    }
//    vector<int> get_next(string s) {
//        int n = s.size();
//        vector<int> next(n, 0);
//        next[0] = -1;
//        int i = 0, j = -1;
//        while (i < n) {
//            if (j == -1 || s[i] == s[j]) {
//                ++i; ++j;
//                next[i] = j;
//            } else {
//                j = next[j];
//            }
//        }
//        return next;
//    }
//};

class Solution {
public:
    int strStr(string haystack, string needle) {
        return kmp(haystack.c_str(), needle.c_str());
    }
private:
    static void compute_next(const char *pattern, int next[]) {
        int i, j = -1;
        int m = strlen(pattern);
        next[0] = j;
        for (i = 1; i < m; ++i) {
            while (j > -1 && pattern[j+1] != pattern[i]) j = next[j];
            if (pattern[i] == pattern[j+1]) ++j;
            next[i] = j;
        }
    }
    static int kmp(const char *text, const char *pattern) {
        int i;
        int j = -1;
        int n = strlen(text);
        int m = strlen(pattern);
        if (n == 0 && m == 0) return 0;
        if (m == 0) return 0;
        int *next = (int *)malloc(sizeof(int) * m);
        compute_next(pattern, next);
        for (i = 0; i < n; ++i) {
            while (j > -1 && pattern[j+1] != text[i]) j = next[j];
            if (text[i] == pattern[j+1]) ++j;
            if (j == m - 1) {
                free(next);
                return i - j;
            }
        }
        free(next);
        return -1;
    }
};

void print(vector<int> v) {
    for (auto e : v) cout << e << " ";
    cout << endl;
}

int main() {
    string haystack = "hello", needle = "ll";
    Solution s;
    cout << s.strStr(haystack, needle) << endl;
    return 0;
}
