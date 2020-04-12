// 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！

#include <iostream>
#include <string>
using namespace std;

//class Solution {
//public:
//    string LeftRotateString(string str, int n) {
//        string s;
//        for (int i = n; i < str.size(); ++i) {
//            s += str[i];
//        }
//        for (int i = 0; i < n; ++i) {
//            s += str[i];
//        }
//        return s;
//    }
//};

//class Solution {
//public:
//    string LeftRotateString(string str, int n) {
//        int l = str.size();
//        if (n <= 0 || l == 0) {
//            return str;
//        }
//        n %= l;
//        str += str;
//        return str.substr(n, l);
//    }
//};

//class Solution {
//public:
//    string LeftRotateString(string str, int n) {
//        int l = str.size();
//        if (n <= 0 || l == 0) {
//            return str;
//        }
//        n %= l;
//        for (int i = 0, j = n - 1; j > i; ++i, --j) swap(str[i], str[j]);
//        for (int i = n, j = l - 1; j > i; ++i, --j) swap(str[i], str[j]);
//        for (int i = 0, j = l - 1; j > i; ++i, --j) swap(str[i], str[j]);
//        return str;
//    }
//};

class Solution {
public:
    string LeftRotateString(string str, int n) {
        int l = str.size();
        if (n <= 0 || l == 0) {
            return str;
        }
        n %= l;
        reverse(str.begin(), str.begin() + n);
        reverse(str.begin() + n, str.end());
        reverse(str.begin(), str.end());
        return str;
    }
};

int main() {
    string str = "abc";
    Solution s;
    cout << s.LeftRotateString(str, 1) << endl;
    return 0;
}
