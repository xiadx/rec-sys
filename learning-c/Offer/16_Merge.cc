// 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。

#include <iostream>
using namespace std;

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *Merge(ListNode *pHead1, ListNode *pHead2) {
        if (pHead1 == NULL) {
            return pHead2;
        }
        if (pHead2 == NULL) {
            return pHead1;
        }
        ListNode *head = NULL;
        if (pHead1->val <= pHead2->val) {
            head = pHead1;
            head->next = Merge(pHead1->next, pHead2);
        } else {
            head = pHead2;
            head->next = Merge(pHead1, pHead2->next);
        }
        return head;
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
    ListNode *p1 = new ListNode(1);
    p1->next = new ListNode(3);
    ListNode *p2 = new ListNode(2);
    p2->next = new ListNode(4);
    Solution s;
    ListNode *h = s.Merge(p1, p2);
    print(h);
    return 0;
}
