// 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。

#include <iostream>
using namespace std;

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *EntryNodeOfLoop(ListNode *pHead) {
        if (pHead == NULL || pHead->next == NULL || pHead->next->next == NULL) {
            return NULL;
        }
        ListNode *slow = pHead->next;
        ListNode *fast = pHead->next->next;
        while (slow != fast) {
            if (fast != NULL && fast->next != NULL) {
                slow = slow->next;
                fast = fast->next->next;
            } else {
                return NULL;
            }
        }
        slow = pHead;
        while (slow != fast) {
            slow = slow->next;
            fast = fast->next;
        }
        return slow;
    }
};

int main() {
    ListNode *h = new ListNode(1);
    h->next = new ListNode(2);
    h->next->next = new ListNode(3);
    h->next->next->next = h->next;
    Solution s;
    cout << s.EntryNodeOfLoop(h)->val << endl;
    return 0;
}
