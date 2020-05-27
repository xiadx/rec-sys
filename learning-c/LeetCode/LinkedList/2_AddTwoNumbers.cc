// You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
//
// You may assume the two numbers do not contain any leading zero, except the number 0 itself.
//
// Example:
//
// Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
// Output: 7 -> 0 -> 8
// Explanation: 342 + 465 = 807.

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
//    ListNode *addTwoNumbers(ListNode *l1, ListNode *l2) {
//        ListNode *h = new ListNode();
//        ListNode *p = h;
//        int carry = 0, value = 0, a = 0, b = 0;
//        while (l1 || l2) {
//            if (l1) a = l1->val;
//            else a = 0;
//            if (l2) b = l2->val;
//            else b = 0;
//            value = (a + b + carry) % 10;
//            carry = (a + b + carry) / 10;
//            p->next = new ListNode(value);
//            p = p->next;
//            if (l1) l1 = l1->next;
//            if (l2) l2 = l2->next;
//        }
//        if (carry) {
//            p->next = new ListNode(carry);
//        }
//        return h->next;
//    }
//};

class Solution {
public:
    ListNode *addTwoNumbers(ListNode *l1, ListNode *l2) {
        ListNode *h = new ListNode();
        ListNode *p = h;
        int carry = 0, value = 0, a = 0, b = 0;
        while (l1 || l2) {
            int a = l1 ? l1->val : 0;
            int b = l2 ? l2->val : 0;
            value = (a + b + carry) % 10;
            carry = (a + b + carry) / 10;
            p->next = new ListNode(value);
            p = p->next;
            if (l1) l1 = l1->next;
            if (l2) l2 = l2->next;
        }
        if (carry) {
            p->next = new ListNode(carry);
        }
        return h->next;
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
    ListNode *l1 = new ListNode(2);
    l1->next = new ListNode(4);
    l1->next->next = new ListNode(3);
    ListNode *l2 = new ListNode(5);
    l2->next = new ListNode(6);
    l2->next->next = new ListNode(4);
    Solution s;
    print(s.addTwoNumbers(l1, l2));
    return 0;
}
