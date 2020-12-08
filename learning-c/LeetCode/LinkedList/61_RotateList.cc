// Given a linked list, rotate the list to the right by k places, where k is non-negative.
//
// Example 1:
//
// Input: 1->2->3->4->5->NULL, k = 2
// Output: 4->5->1->2->3->NULL
// Explanation:
// rotate 1 steps to the right: 5->1->2->3->4->NULL
// rotate 2 steps to the right: 4->5->1->2->3->NULL
// Example 2:
//
// Input: 0->1->2->NULL, k = 4
// Output: 2->0->1->NULL
// Explanation:
// rotate 1 steps to the right: 2->0->1->NULL
// rotate 2 steps to the right: 1->2->0->NULL
// rotate 3 steps to the right: 0->1->2->NULL
// rotate 4 steps to the right: 2->0->1->NULL

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
//    ListNode *rotateRight(ListNode *head, int k) {
//        if (!head) return nullptr;
//        ListNode *dummy = new ListNode();
//        dummy->next = head;
//        int l = length(head);
//        int p = l - k % l;
//        ListNode *pre = dummy;
//        while (p > 0) {
//            pre = pre->next;
//            --p;
//        }
//        ListNode *cur = pre->next;
//        pre->next = nullptr;
//        ListNode *tail = dummy;
//        while (cur) {
//            pre = cur;
//            cur = cur->next;
//            pre->next = tail->next;
//            tail->next = pre;
//            tail = pre;
//        }
//        return dummy->next;
//    }
//    int length(ListNode *head) {
//        ListNode *p = head;
//        int l = 0;
//        while (p) {
//            ++l;
//            p = p->next;
//        }
//        return l;
//    }
//};

class Solution {
public:
    ListNode *rotateRight(ListNode *head, int k) {
        if (!head || k == 0) return head;
        ListNode *p = head;
        int l = 1;
        while (p->next) {
            ++l; p = p->next;
        }
        int s = l - k % l;
        p->next = head;
        while (s > 0) {
            p = p->next;
            --s;
        }
        head = p->next;
        p->next = nullptr;
        return head;
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
    print(s.rotateRight(h, 2));
    return 0;
}
