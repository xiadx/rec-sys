//Given two binary strings, return their sum (also a binary string).
//
//The input strings are both non-empty and contains only characters 1 or 0.
//
//Example 1:
//
//Input: a = "11", b = "1"
//Output: "100"
//Example 2:
//
//Input: a = "1010", b = "1011"
//Output: "10101"
//
//
//Constraints:
//
//Each string consists only of '0' or '1' characters.
//1 <= a.length, b.length <= 10^4
//Each string is either "0" or doesn't contain any leading zero.

#include <iostream>
#include <string>
#include <stack>
using namespace std;

//class Solution {
//public:
//    string addBinary(string a, string b) {
//        stack<int> s1;
//        stack<int> s2;
//        stack<int> s;
//        int m = a.size(), n = b.size();
//        for (int i = 0; i < m; ++i) s1.push(a[i] - '0');
//        for (int i = 0; i < n; ++i) s2.push(b[i] - '0');
//        int carry = 0, value = 0, current = 0;
//        while (!s1.empty() && !s2.empty()) {
//           value = s1.top() + s2.top() + carry;
//           carry = value / 2;
//           current = value % 2;
//           s.push(current);
//           s1.pop(); s2.pop();
//        }
//        while (!s1.empty()) {
//            value = s1.top() + carry;
//            carry = value / 2;
//            current = value % 2;
//            s.push(current);
//            s1.pop();
//        }
//        while (!s2.empty()) {
//            value = s2.top() + carry;
//            carry = value / 2;
//            current = value % 2;
//            s.push(current);
//            s2.pop();
//        }
//        if (carry == 1) s.push(carry);
//        string r = "";
//        while (!s.empty()) {
//            r += to_string(s.top()); s.pop();
//        }
//        return r;
//    }
//};

class Solution {
public:
    string addBinary(string a, string b) {
        int i = a.length() - 1, j = b.length() - 1;
        int carry = 0;
        string r;
        while (i >= 0 || j >= 0 || carry) {
            int va = i < 0 ? 0 : a[i--] - '0';
            int vb = j < 0 ? 0 : b[j--] - '0';
            int sum = va + vb + carry;
            carry = sum / 2;
            r.insert(r.begin(), sum % 2 + '0');
        }
        return r;
    }
};

int main() {
    string a = "1010";
    string b = "1011";
    Solution s;
    cout << s.addBinary(a, b) << endl;
    return 0;
}
