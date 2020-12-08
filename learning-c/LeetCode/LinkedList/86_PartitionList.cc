// Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
//
// You should preserve the original relative order of the nodes in each of the two partitions.
//
// Example:
//
// Input: head = 1->4->3->2->5->2, x = 3
// Output: 1->2->2->4->3->5

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
//    ListNode *partition(ListNode *head, int x) {
//        ListNode *dummy = new ListNode();
//        dummy->next = head;
//        ListNode *pre = dummy, *cur = head;
//        while (cur && cur->val < x) {
//            pre = cur;
//            cur = cur->next;
//        }
//        ListNode *con = pre;
//        while (cur) {
//            if (cur->val < x) {
//                pre->next = cur->next;
//                cur->next = con->next;
//                con->next = cur;
//                con = cur;
//                cur = pre->next;
//            } else {
//                pre = cur;
//                cur = cur->next;
//            }
//        }
//        return dummy->next;
//    }
//};

class Solution {
public:
    ListNode *partition(ListNode *head, int x) {
        ListNode *less_dummy = new ListNode();
        ListNode *greater_dummy = new ListNode();
        ListNode *less = less_dummy, *greater = greater_dummy;
        ListNode *p = head;
        while (p) {
            if (p->val < x) {
                less->next = p;
                less = less->next;
                p = p->next;
            } else {
                greater->next = p;
                greater = greater->next;
                p = p->next;
            }
        }
        greater->next = nullptr;
        less->next = greater_dummy->next;
        return less_dummy->next;
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
//    int a[] = {1, 4, 3, 2, 5, 2};
//    vector<int> v(a, a + 6);
    vector<int> v = {1, 4, 3, 2, 5, 2};
    ListNode *h = create(v);
    Solution s;
    print(s.partition(h, 3));
    return 0;
}
