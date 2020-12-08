// Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
//
// Note: For the purpose of this problem, we define empty string as valid palindrome.
//
// Example 1:
//
// Input: "A man, a plan, a canal: Panama"
// Output: true
// Example 2:
//
// Input: "race a car"
// Output: false
//
//
// Constraints:
//
// s consists only of printable ASCII characters.

#include <iostream>
#include <string>
using namespace std;

class Solution {
public:
    bool isPalindrome(string s) {
        transform(s.begin(), s.end(), s.begin(), ::tolower);
        int i = 0, j = s.size() - 1;
        while (i < j) {
            if (!::isalnum(s[i])) ++i;
            else if (!::isalnum(s[j])) --j;
            else if (s[i] == s[j]) {
                ++i; --j;
            } else {
                break;
            }
        }
        return i >= j ? true : false;
    }
};

int main() {
    string str = "A man, a plan, a canal: Panama";
    Solution s;
    cout << s.isPalindrome(str) << endl;
    return 0;
}
