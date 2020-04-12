// 输入两个链表，找出它们的第一个公共结点。（注意因为传入数据是链表，所以错误测试数据的提示是用其他方式显示的，保证传入数据是正确的）

#include <iostream>
#include <stack>
using namespace std;

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *FindFirstCommonNode(ListNode *pHead1, ListNode *pHead2) {
        stack<ListNode *> s1;
        stack<ListNode *> s2;
        while (pHead1) {
            s1.push(pHead1);
            pHead1 = pHead1->next;
        }
        while (pHead2) {
            s2.push(pHead2);
            pHead2 = pHead2->next;
        }
        ListNode *c = NULL;
        while (!s1.empty() && !s2.empty() && s1.top() == s2.top()) {
            c = s1.top();
            s1.pop();
            s2.pop();
        }
        return c;
    }
};

int main() {
    ListNode *l1 = new ListNode(1);
    l1->next = new ListNode(2);
    l1->next->next = new ListNode(3);
    ListNode *l2 = new ListNode(4);
    l2->next = l1->next;
    Solution s;
    cout << s.FindFirstCommonNode(l1, l2)->val << endl;
    return 0;
}
