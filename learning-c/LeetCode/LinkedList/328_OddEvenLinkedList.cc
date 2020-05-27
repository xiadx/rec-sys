// Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.
//
// You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
//
// Example 1:
//
// Input: 1->2->3->4->5->NULL
// Output: 1->3->5->2->4->NULL
// Example 2:
//
// Input: 2->1->3->5->6->4->7->NULL
// Output: 2->3->6->7->1->5->4->NULL
// Note:
//
// The relative order inside both the even and odd groups should remain as it was in the input.
// The first node is considered odd, the second node even and so on ...

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
//    ListNode *oddEvenList(ListNode *head) {
//        if (!head) return head;
//        if (!head->next) return head;
//        ListNode *odd = head;
//        ListNode *even = head->next;
//        ListNode *p = odd, *q = even;
//        while (q) {
//            p->next = q->next;
//            p = p->next;
//            if (p) q->next = p->next;
//            else q->next = nullptr;
//            q = q->next;
//        }
//        p = odd;
//        while (p->next) {
//            p = p->next;
//        }
//        p->next = even;
//        return odd;
//    }
//};

class Solution {
public:
    ListNode *oddEvenList(ListNode *head) {
        if (!head) return head;
        ListNode *odd = head;
        ListNode *even = head->next;
        ListNode *p = odd, *q = even;
        while (q && q->next) {
            p->next = q->next;
            p = p->next;
            q->next = p->next;
            q = q->next;
        }
        p->next = even;
        return odd;
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
    h->next->next->next = new ListNode(4);
    h->next->next->next->next = new ListNode(5);
    Solution s;
    print(s.oddEvenList(h));
    return 0;
}
