// 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。

#include <iostream>
#include <stack>
using namespace std;

class Solution {
private:
    stack<int> s1;
    stack<int> s2;
public:
    void push(int value) {
        s1.push(value);
        if (s2.empty()) {
            s2.push(value);
        }
        if (value < s2.top()) {
            s2.push(value);
        } else {
            s2.push(s2.top());
        }
    }
    void pop() {
        s1.pop();
        s2.pop();
    }
    int top() {
        return s1.top();
    }
    int min() {
        return s2.top();
    }
};

int main() {
    Solution s;
    s.push(1); s.push(2); s.push(3);
    cout << s.top() << endl;
    cout << s.min() << endl;
    return 0;
}
