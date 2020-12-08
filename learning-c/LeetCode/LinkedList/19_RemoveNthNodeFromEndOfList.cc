// Given a linked list, remove the n-th node from the end of list and return its head.
//
// Example:
//
// Given linked list: 1->2->3->4->5, and n = 2.
//
// After removing the second node from the end, the linked list becomes 1->2->3->5.
// Note:
//
// Given n will always be valid.
//
// Follow up:
//
// Could you do this in one pass?

#include <iostream>
#include <vector>
using namespace std;

// Definition for singly-linked list.
struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

//class Solution {
//public:
//    ListNode *removeNthFromEnd(ListNode *head, int n) {
//        if (!head || n == 0) return head;
//        int l = 0;
//        ListNode *p = head;
//        while (p) {
//            ++l; p = p->next;
//        }
//        p = head;
//        int s = l - n % l;
//        while (s > 1) {
//            p = p->next; --s;
//        }
//        if (p->next) {
//            ListNode *t = p->next;
//            p->next = t->next;
//            delete t;
//        } else {
//            ListNode *t = head;
//            head = t->next;
//            delete t;
//        }
//        return head;
//    }
//};

class Solution {
public:
    ListNode *removeNthFromEnd(ListNode *head, int n) {
        ListNode dummy(-1, head);
        ListNode *p = &dummy, *q = &dummy, *t = nullptr;
        while (n > 0) {
            q = q->next;
            --n;
        }
        while (q->next) {
            p = p->next;
            q = q->next;
        }
        t = p->next;
        p->next = t->next;
        delete t;
        return dummy.next;
    }
};

ListNode *create(vector<int> v) {
    ListNode *dummy = new ListNode();
    ListNode *p = dummy, *q;
    for (int i = 0; i < v.size(); ++i) {
        q = new ListNode(v[i]);
        p->next = q;
        p = q;
    }
    return dummy->next;
}

void print(ListNode *h) {
    while (h) {
        cout << h->val << " ";
        h = h->next;
    }
    cout << endl;
}

int main() {
    vector<int> v = {1, 2, 3, 4, 5};
    ListNode *h = create(v);
    Solution s;
    print(s.removeNthFromEnd(h, 2));
    return 0;
}
