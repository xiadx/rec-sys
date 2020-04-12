// 输入一个链表，输出该链表中倒数第k个结点。

#include <iostream>
using namespace std;

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *FindKthToTail(ListNode *pListHead, unsigned int k) {
        ListNode *p = pListHead, *q = pListHead;
        if (k <= 0 || p == NULL) {
            return NULL;
        }
        while (k && q) {
            q = q->next;
            --k;
        }
        if (k > 0) {
            return NULL;
        } else {
            while (q) {
                p = p->next;
                q = q->next;
            }
            return p;
        }
    }
};

int main() {
    ListNode *head = new ListNode(1);
    head->next = new ListNode(2);
    head->next->next = new ListNode(3);
    Solution s;
    cout << s.FindKthToTail(head, 2)->val << endl;
    return 0;
}
