// 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。

#include <iostream>
#include <stack>
using namespace std;

class Solution {
private:
    stack<int> stack1;
    stack<int> stack2;
public:
    void push(int node) {
        stack1.push(node);
    }
    int pop() {
        int top = -1;
        if (!stack2.empty()) {
            top = stack2.top();
            stack2.pop();
        } else {
            while (!stack1.empty()) {
                stack2.push(stack1.top());
                stack1.pop();
            }
            top = stack2.top();
            stack2.pop();
        }
        return top;
    }
};

int main() {
    Solution s;
    s.push(1);
    s.push(2);
    s.push(3);
    cout << s.pop() << endl;
    cout << s.pop() << endl;
    cout << s.pop() << endl;
    return 0;
}
