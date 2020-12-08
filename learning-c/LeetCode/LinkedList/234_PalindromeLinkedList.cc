// Given a singly linked list, determine if it is a palindrome.
//
// Example 1:
//
// Input: 1->2
// Output: false
// Example 2:
//
// Input: 1->2->2->1
// Output: true
// Follow up:
// Could you do it in O(n) time and O(1) space?

#include <iostream>
#include <stack>
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
//    bool isPalindrome(ListNode *head) {
//        int l = length(head);
//        ListNode *slow = head, *fast = head;
//        while (fast && fast->next) {
//            slow = slow->next;
//            fast = fast->next->next;
//        }
//        if (l % 2 == 1) slow = slow->next;
//        stack<ListNode *> s;
//        while (slow) {
//            s.push(slow);
//            slow = slow->next;
//        }
//        ListNode *h = head;
//        while (!s.empty() && s.top()->val == h->val) {
//            s.pop();
//            h = h->next;
//        }
//        return s.empty() ? true : false;
//    }
//    int length(ListNode *head) {
//        if (!head) return 0;
//        ListNode *h = head;
//        int i = 0;
//        while (h) {
//            ++i;
//            h = h->next;
//        }
//        return i;
//    }
//};

//class Solution {
//public:
//    bool isPalindrome(ListNode *head) {
//        vector<int> v;
//        ListNode *h = head;
//        while (h) {
//            v.push_back(h->val);
//            h = h->next;
//        }
//        int i = 0, j = v.size() - 1;
//        while (i < j && v[i] == v[j]) {
//            ++i; --j;
//        }
//        return i >= j ? true : false;
//    }
//};

class Solution {
public:
    bool isPalindrome(ListNode *head) {
        int l = length(head);
        if (l <= 1) return true;
        ListNode *slow = head, *fast = head;
        while (fast && fast->next) {
            slow = slow->next;
            fast = fast->next->next;
        }
        if (l % 2 == 1) slow = slow->next;
        ListNode *begin = slow, *end = tail(head);
        reverse(head, begin, end);
        slow = head; fast = end;
        while (fast && slow->val == fast->val) {
            slow = slow->next;
            fast = fast->next;
        }
        reverse(head, end, begin);
        return !fast ? true : false;
    }
    void reverse(ListNode *head, ListNode *begin, ListNode *end) {
        ListNode *before_begin, *end_after;
        ListNode *h = head;
        while (h->next != begin) {
            h = h->next;
        }
        before_begin = h;
        end_after = end->next;
        ListNode *pre, *cur, *nxt;
        for (pre = before_begin, cur = begin, nxt = begin->next; cur != end_after; pre = cur, cur = nxt, nxt = cur ? cur->next : nullptr) cur->next = pre;
        before_begin->next = end;
        begin->next = end_after;
    }
    int length(ListNode *head) {
        if (!head) return 0;
        ListNode *h = head;
        int i = 0;
        while (h) {
            ++i;
            h = h->next;
        }
        return i;
    }
    ListNode *tail(ListNode *head) {
        if (!head) return nullptr;
        ListNode *h = head;
        while (h->next) {
            h = h->next;
        }
        return h;
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
    h->next->next = new ListNode(2);
    h->next->next->next = new ListNode(1);
    Solution s;
    cout << s.isPalindrome(h) << endl;
    return 0;
}
