// 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。

#include <iostream>
using namespace std;

//class Solution {
//public:
//    int Add(int num1, int num2) {
//        while (num2 != 0) {
//            int temp = num1;
//            num1 = num1 ^ num2;
//            num2 = (temp & num2) << 1;
//        }
//        return num1;
//    }
//};

class Solution {
public:
    int Add(int num1, int num2) {
        return num2 ? Add(num1 ^ num2, (num1 & num2) << 1) : num1;
    }
};

//class Solution {
//public:
//    int Add(int num1, int num2) {
//        __asm {
//            MOV EAX, a
//            MOV ECX, b
//            ADD EAX, ECX
//        }
//    }
//};

int main() {
    Solution s;
    cout << s.Add(1, 2) << endl;
    return 0;
}
