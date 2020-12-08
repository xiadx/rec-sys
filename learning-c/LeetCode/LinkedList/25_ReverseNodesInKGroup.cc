// Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
//
// k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
//
// Example:
//
// Given this linked list: 1->2->3->4->5
//
// For k = 2, you should return: 2->1->4->3->5
//
// For k = 3, you should return: 3->2->1->4->5
//
// Note:
//
// Only constant extra memory is allowed.
// You may not alter the values in the list's nodes, only nodes itself may be changed.

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
//    ListNode *reverseKGroup(ListNode *head, int k) {
//        if (!head || !head->next || k < 2) return head;
//        ListNode *dummy = new ListNode(-1);
//        dummy->next = head;
//        ListNode *prev, *end;
//        for (prev = dummy, end = prev->next; end; end = prev->next) {
//            for (int i = 1; i < k && end; ++i) end = end->next;
//            if (!end) break;
//            prev = reverse(prev, prev->next, end);
//        }
//        return dummy->next;
//    }
//    ListNode *reverse(ListNode *prev, ListNode *begin, ListNode *end) {
//        ListNode *pre, *cur, *nxt;
//        ListNode *enxt = end->next;
//        for (pre = prev, cur = begin, nxt = cur->next; cur && cur != enxt; pre = cur, cur = nxt, nxt = cur ? cur->next : nullptr) cur->next = pre;
//        begin->next = enxt;
//        prev->next = end;
//        return begin;
//    }
//};

class Solution {
public:
    ListNode *reverseKGroup(ListNode *head, int k) {
        if (!head || !head->next || k < 2) return head;
        ListNode *enxt = head;
        for (int i = 0; i < k; ++i) {
            if (enxt) enxt = enxt->next;
            else return head;
        }
        ListNode *gro = reverseKGroup(enxt, k);
        ListNode *pre = nullptr, *cur = head;
        while (cur != enxt) {
            ListNode *nxt = cur->next;
            cur->next = pre ? pre : gro;
            pre = cur;
            cur = nxt;
        }
        return pre;
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
    int k = 2;
    Solution s;
    print(s.reverseKGroup(h, k));
    return 0;
}
