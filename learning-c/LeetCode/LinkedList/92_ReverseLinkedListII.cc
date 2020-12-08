// Reverse a linked list from position m to n. Do it in one-pass.
//
// Note: 1 ≤ m ≤ n ≤ length of list.
//
// Example:
//
// Input: 1->2->3->4->5->NULL, m = 2, n = 4
// Output: 1->4->3->2->5->NULL

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
//    ListNode *left;
//    bool stop;
//public:
//    ListNode *reverseBetween(ListNode *head, int m, int n) {
//        left = head;
//        stop = false;
//        recurseAndReverse(head, m, n);
//        return head;
//    }
//    void recurseAndReverse(ListNode *right, int m, int n) {
//        if (n == 1) return;
//        right = right->next;
//        if (m > 1) left = left->next;
//        recurseAndReverse(right, m - 1, n - 1);
//        if (left == right || right->next == left) stop = true;
//        if (!stop) {
//            int t = left->val;
//            left->val = right->val;
//            right->val = t;
//            left = left->next;
//        }
//    }
//};

//class Solution {
//public:
//    ListNode *reverseBetween(ListNode *head, int m, int n) {
//        ListNode *p, *q, *l, *r;
//        int s = length(head);
//        if (m <= 1) p = nullptr;
//        else p = point(head, m - 1);
//        if (n >= s) q = nullptr;
//        else q = point(head, n + 1);
//        point(head, n)->next = nullptr;
//        l = point(head, m);
//        l = reverse(l);
//        r = tail(l);
//        if (p) {
//            p->next = l;
//            r->next = q;
//        } else {
//            head = l;
//            r->next = q;
//        }
//        return head;
//    }
//    ListNode *point(ListNode *head, int m) {
//        int l = length(head);
//        if (m < 1) m = 1;
//        if (m > l) m = l;
//        ListNode *p = head;
//        while (m-- > 1) p = p->next;
//        return p;
//    }
//    ListNode *reverse(ListNode *head) {
//        ListNode *dummy = new ListNode(0);
//        ListNode *p = head, *q = nullptr;
//        while (p) {
//            q = p->next;
//            p->next = dummy->next;
//            dummy->next = p;
//            p = q;
//        }
//        return dummy->next;
//    }
//    int length(ListNode *head) {
//        int l = 0;
//        ListNode *p = head;
//        while (p) {
//            ++l; p = p->next;
//        }
//        return l;
//    }
//    ListNode *tail(ListNode *head) {
//        ListNode *p = head;
//        while (p->next) p = p->next;
//        return p;
//    }
//};

class Solution {
public:
    ListNode *reverseBetween(ListNode *head, int m, int n) {
        ListNode *pre = nullptr, *cur = head;
        while (m > 1) {
            pre = cur;
            cur = cur->next;
            --m;
            --n;
        }
        ListNode *con = pre, *tail = cur;
        ListNode *third = nullptr;
        while (n > 0) {
            third = cur->next;
            cur->next = pre;
            pre = cur;
            cur = third;
            --n;
        }
        if (con) {
            con->next = pre;
            tail->next = cur;
        } else {
            head = pre;
            tail->next = cur;
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
    ListNode *h = new ListNode(1);
    h->next = new ListNode(2);
    h->next->next = new ListNode(3);
    h->next->next->next = new ListNode(4);
    h->next->next->next->next = new ListNode(5);
    Solution s;
    print(s.reverseBetween(h, 2, 4));
    return 0;
}
