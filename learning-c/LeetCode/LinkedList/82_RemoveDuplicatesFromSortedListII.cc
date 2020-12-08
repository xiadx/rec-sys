// Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
//
// Return the linked list sorted as well.
//
// Example 1:
//
// Input: 1->2->3->3->4->4->5
// Output: 1->2->5
// Example 2:
//
// Input: 1->1->1->2->3
// Output: 2->3

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

class Solution {
public:
    ListNode *deleteDuplicates(ListNode *head) {
        ListNode *dummy = new ListNode();
        dummy->next = head;
        ListNode *pre = dummy, *cur = head, *temp = nullptr;
        while (cur) {
            bool duplicate = false;
            while(cur->next && cur->val == cur->next->val) {
                duplicate = true;
                temp = cur;
                cur = cur->next;
                delete temp;
            }
            if (duplicate) {
                temp = cur;
                cur = cur->next;
                delete temp;
                continue;
            } else {
                pre->next = cur;
                pre = pre->next;
                cur = cur->next;
            }
        }
        pre->next = cur;
        return dummy->next;
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
    vector<int> v = {1, 2, 3, 3, 4, 4, 5};
    ListNode *h = create(v);
    Solution s;
    print(s.deleteDuplicates(h));
    return 0;
}
