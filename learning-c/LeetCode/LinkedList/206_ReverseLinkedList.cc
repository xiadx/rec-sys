// Reverse a singly linked list.
//
// Example:
//
// Input: 1->2->3->4->5->NULL
// Output: 5->4->3->2->1->NULL
// Follow up:
//
// A linked list can be reversed either iteratively or recursively. Could you implement both?

#include <iostream>
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
//    ListNode *reverseList(ListNode *head) {
//        ListNode *p = head, *q;
//        ListNode *h = new ListNode();
//        while (p) {
//            q = p->next;
//            p->next = h->next;
//            h->next = p;
//            p = q;
//        }
//        return h->next;
//    }
//};

class Solution {
public:
    ListNode *reverseList(ListNode *head) {
        ListNode *p = nullptr, *q = head, *r = nullptr;
        while (q) {
            r = q->next;
            q->next = p;
            p = q;
            q = r;
        }
        return p;
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
    print(s.reverseList(h));
    return 0;
}
