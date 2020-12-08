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
    ListNode *reverse(ListNode *head) {
        if (!head) return nullptr;
        ListNode *pre, *cur, *nxt;
        for (pre = nullptr, cur = head, nxt = cur->next; cur != nullptr; pre = cur, cur = nxt, nxt = cur ? cur->next : nullptr) cur->next = pre;
        return pre;
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
    print(h);
    print(s.reverse(h));
    return 0;
}