// 输入一个链表，反转链表后，输出新链表的表头。

#include <iostream>
using namespace std;

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *ReverseList(ListNode *pHead) {
        if (pHead == NULL) {
            return NULL;
        }
        ListNode *q = pHead, *r = pHead->next, *p = NULL;
        q->next = NULL;
        while (r) {
            p = q;
            q = r;
            r = r->next;
            q->next = p;
        }
        return q;
    }
};

void print(ListNode *h) {
    while (h) {
        cout << h->val << " ";
        h = h->next;
    }
    cout << endl;
}

int main() {
    ListNode *h = new ListNode(1);
    h->next = new ListNode(2);
    h->next->next = new ListNode(3);
    Solution s;
    ListNode *t = s.ReverseList(h);
    print(t);
    return 0;
}
