// Given a linked list, swap every two adjacent nodes and return its head.
//
// You may not modify the values in the list's nodes, only nodes itself may be changed.
//
//
//
// Example:
//
// Given 1->2->3->4, you should return the list as 2->1->4->3.

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
//    ListNode *swapPairs(ListNode *head) {
//        ListNode *p = head;
//        while (p && p->next) {
//            swap(p->val, p->next->val);
//            p = p->next->next;
//        }
//        return head;
//    }
//};

class Solution {
public:
    ListNode *swapPairs(ListNode *head) {
        if (!head || !head->next) return head;
        ListNode dummy(-1);
        dummy.next = head;
        for (ListNode *pre = &dummy, *cur = pre->next, *next = cur->next; next; pre = cur, cur = cur->next, next = cur ? cur->next : nullptr) {
            pre->next = next;
            cur->next = next->next;
            next->next = cur;
        }
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
    vector<int> v = {1, 2, 3, 4};
    ListNode *h = create(v);
    Solution s;
    print(s.swapPairs(h));
    return 0;
}
