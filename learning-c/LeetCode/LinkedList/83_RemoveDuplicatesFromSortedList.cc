// Given a sorted linked list, delete all duplicates such that each element appear only once.
//
// Example 1:
//
// Input: 1->1->2
// Output: 1->2
// Example 2:
//
// Input: 1->1->2->3->3
// Output: 1->2->3

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
//    ListNode *deleteDuplicates(ListNode *head) {
//        if (!head) return nullptr;
//        ListNode *pre = head, *cur = head;
//        while (cur && cur->next) {
//            if (cur->val == cur->next->val) {
//                cur = cur->next;
//            } else {
//                pre->next = cur->next;
//                pre = pre->next;
//                cur = cur->next;
//            }
//        }
//        pre->next = nullptr;
//        return head;
//    }
//};

class Solution {
public:
    ListNode *deleteDuplicates(ListNode *head) {
        if (!head) return nullptr;
        for (ListNode *pre = head, *cur = head->next; cur != nullptr; cur = pre->next) {
            if (pre->val == cur->val) {
                pre->next = cur->next;
                delete cur;
            } else {
                pre = cur;
            }
        }
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
    vector<int> v = {1, 1, 2, 3, 3};
    ListNode *h = create(v);
    Solution s;
    print(s.deleteDuplicates(h));
    return 0;
}
