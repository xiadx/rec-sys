// Write a function to find the longest common prefix string amongst an array of strings.
//
// If there is no common prefix, return an empty string "".
//
//
//
// Example 1:
//
// Input: strs = ["flower","flow","flight"]
// Output: "fl"
// Example 2:
//
// Input: strs = ["dog","racecar","car"]
// Output: ""
// Explanation: There is no common prefix among the input strings.
//
//
// Constraints:
//
// 0 <= strs.length <= 200
// 0 <= strs[i].length <= 200
// strs[i] consists of only lower-case English letters.

#include <iostream>
#include <string>
#include <vector>
using namespace std;

//class Solution {
//public:
//    string longestCommonPrefix(vector<string>& strs) {
//        int n = strs.size();
//        if (n == 0) return "";
//        int m = INT_MAX, c = 0;
//        for (int i = 0; i < n; ++i) {
//            int l = strs[i].length();
//            if (l < m) {
//                m = l; c = i;
//            }
//        }
//        for (int j = 0; j < m; ++j) {
//            char ch = strs[c][j];
//            for (int i = 0; i < n; ++i) {
//                if (strs[i][j] == ch) continue;
//                else return strs[c].substr(0, j);
//            }
//        }
//        return strs[c];
//    }
//};

class Solution {
public:
    string longestCommonPrefix(vector<string>& strs) {
        if (strs.empty()) return "";
        for (int j = 0; j < strs[0].size(); ++j) {
            for (int i = 1; i < strs.size(); ++i) {
                if (strs[i][j] != strs[0][j]) {
                    return strs[0].substr(0, j);
                }
            }
        }
        return strs[0];
    }
};

int main() {
    vector<string> v = {"flower", "flow", "flight"};
    Solution s;
    cout << s.longestCommonPrefix(v) << endl;
    return 0;
}
