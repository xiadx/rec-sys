// 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5

#include <iostream>
using namespace std;

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *deleteDuplication(ListNode *pHead) {
        ListNode *p = NULL, *q = pHead, *r = NULL;
        while (q) {
            if (q->next && q->next->val == q->val) {
                r = q->next;
                while (r->next && r->next->val == q->val) {
                    r = r->next;
                }
                if (q == pHead) {
                    pHead = r->next;
                } else {
                    p->next = r->next;
                }
                q = r->next;
            } else {
                p = q;
                q = q->next;
            }
        }
        return pHead;
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
    h->next->next = new ListNode(2);
    h->next->next->next = new ListNode(5);
    Solution s;
    print(s.deleteDuplication(h));
    return 0;
}
