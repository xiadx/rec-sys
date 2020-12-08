//Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
//
//There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
//
// Notice that you should not modify the linked list.
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
// Output: tail connects to node index 1
// Explanation: There is a cycle in the linked list, where tail connects to the second node.
// Example 2:
//
//
// Input: head = [1,2], pos = 0
// Output: tail connects to node index 0
// Explanation: There is a cycle in the linked list, where tail connects to the first node.
// Example 3:
//
//
// Input: head = [1], pos = -1
// Output: no cycle
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
//    ListNode *detectCycle(ListNode *head) {
//        unordered_map<ListNode *, int> m;
//        ListNode *h = head;
//        while (h) {
//            if (m[h] == 1) {
//                return h;
//            } else {
//                m[h] = 1;
//                h = h->next;
//            }
//        }
//        return nullptr;
//    }
//};

//class Solution {
//public:
//    ListNode *detectCycle(ListNode *head) {
//        unordered_map<ListNode *, int> m;
//        ListNode *h = head;
//        while (h) {
////            unordered_map<ListNode *, int>::iterator iter = m.find(h);
//            auto iter = m.find(h);
//            if (iter != m.end()) {
//                return iter->first;
//            } else {
//                m[h] = 1;
//                h = h->next;
//            }
//        }
//        return nullptr;
//    }
//};

class Solution {
public:
    ListNode *detectCycle(ListNode *head) {
        ListNode *slow = head, *fast = head;
        while (fast && fast->next) {
            slow = slow->next;
            fast = fast->next->next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow->next;
                    fast = fast->next;
                }
                return slow;
            }
        }
        return nullptr;
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
    cout << s.detectCycle(h)->val << endl;
    return 0;
}
