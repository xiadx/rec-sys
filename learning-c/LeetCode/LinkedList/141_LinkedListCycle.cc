// Given head, the head of a linked list, determine if the linked list has a cycle in it.
//
// There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
//
// Return true if there is a cycle in the linked list. Otherwise, return false.
//
// Follow up:
//
// Can you solve it using O(1) (i.e. constant) memory?
//
//
//
// Example 1:
//
//
// Input: head = [3,2,0,-4], pos = 1
// Output: true
// Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
// Example 2:
//
//
// Input: head = [1,2], pos = 0
// Output: true
// Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
// Example 3:
//
//
// Input: head = [1], pos = -1
// Output: false
// Explanation: There is no cycle in the linked list.
//
//
// Constraints:
//
// The number of the nodes in the list is in the range [0, 104].
// -105 <= Node.val <= 105
// pos is -1 or a valid index in the linked-list.

#include <iostream>
#include <unordered_map>
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
//    bool hasCycle(ListNode *head) {
//        unordered_map<ListNode *, int> m;
//        ListNode *h = head;
//        while (h) {
//            if (m[h] == 1) {
//                return true;
//            } else {
//                m[h] = 1;
//                h = h->next;
//            }
//        }
//        return false;
//    }
//};

//class Solution {
//public:
//    bool hasCycle(ListNode *head) {
//        unordered_map<ListNode *, int> m;
//        ListNode *h = head;
//        while (h) {
//            unordered_map<ListNode *, int>::iterator iter = m.find(h);
//            if (iter != m.end()) {
//                return true;
//            } else {
//                m[h] = 1;
//                h = h->next;
//            }
//        }
//        return false;
//    }
//};

//class Solution {
//public:
//    bool hasCycle(ListNode *head) {
//        if (!head) return false;
//        ListNode *slow = head, *fast = head->next;
//        while (fast && fast->next && slow != fast) {
//            slow = slow->next;
//            fast = fast->next->next;
//        }
//        return (slow && fast && slow == fast) ? true : false;
//    }
//};

class Solution {
public:
    bool hasCycle(ListNode *head) {
        if (!head) return false;
        ListNode *slow = head, *fast = head->next;
        while (slow != fast) {
            if (!fast || !fast->next) return false;
            slow = slow->next;
            fast = fast->next->next;
        }
        return true;
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
    h->next->next = h;
    Solution s;
    cout << s.hasCycle(h) << endl;
    return 0;
}
