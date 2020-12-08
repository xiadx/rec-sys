// A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
//
// Return a deep copy of the list.
//
// The Linked List is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:
//
// val: an integer representing Node.val
// random_index: the index of the node (range from 0 to n-1) where random pointer points to, or null if it does not point to any node.
//
//
// Example 1:
//
//
// Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
// Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
// Example 2:
//
//
// Input: head = [[1,1],[2,1]]
// Output: [[1,1],[2,1]]
// Example 3:
//
//
//
// Input: head = [[3,null],[3,0],[3,null]]
// Output: [[3,null],[3,0],[3,null]]
// Example 4:
//
// Input: head = []
// Output: []
// Explanation: Given linked list is empty (null pointer), so return null.
//
//
// Constraints:
//
// -10000 <= Node.val <= 10000
// Node.random is null or pointing to a node in the linked list.
// Number of Nodes will not exceed 1000.

#include <iostream>
#include <unordered_map>
#include <vector>
#include <string>
using namespace std;

// Definition for a Node.
class Node {
public:
    int val;
    Node *next;
    Node *random;
    Node(int _val) {
        val = _val;
        next = NULL;
        random = NULL;
    }
};

//class Solution {
//public:
//    unordered_map<Node *, Node *> visited;
//    Node *copyRandomList(Node *head) {
//        if (!head) return head;
//        auto iter = visited.find(head);
//        if (iter != visited.end()) {
//            return iter->second;
//        } else {
//            Node *copy = new Node(head->val);
//            visited[head] = copy;
//            copy->next = copyRandomList(head->next);
//            copy->random = copyRandomList(head->random);
//            return copy;
//        }
//    }
//};

//class Solution {
//public:
//    unordered_map<Node *, Node *> visited;
//    Node *get(Node *oldNode) {
//        if (oldNode == nullptr) return nullptr;
//        auto iter = visited.find(oldNode);
//        if (iter != visited.end()) return iter->second;
//        Node *newNode = new Node(oldNode->val);
//        visited[oldNode] = newNode;
//        return newNode;
//    }
//    Node *copyRandomList(Node *head) {
//        if (head == nullptr) return nullptr;
//        Node *oldNode = head;
//        Node *newNode = get(oldNode);
//        while (oldNode) {
//            newNode->next = get(oldNode->next);
//            newNode->random = get(oldNode->random);
//            oldNode = oldNode->next;
//            newNode = newNode->next;
//        }
//        return visited[head];
//    }
//};

class Solution {
public:
    Node *copyRandomList(Node *head) {
        if (head == nullptr) return nullptr;
        Node *cur = head;
        while (cur) {
            Node *nxt = new Node(cur->val);
            nxt->next = cur->next;
            cur->next = nxt;
            cur = nxt->next;
        }
        cur = head;
        while (cur && cur->next) {
            cur->next->random = (cur->random ? cur->random->next : nullptr);
            cur = cur->next->next;
        }
        Node *dummy = new Node(-1);
        dummy->next = head;
        Node *pre = dummy;
        cur = head;
        while (cur && cur->next) {
            pre->next = cur->next;
            pre = pre->next;
            cur->next = cur->next->next;
            cur = cur->next;
        }
        return dummy->next;
    }
};

void print(Node *h) {
    vector<vector<string> > v;
    unordered_map<Node *, int> m;
    Node *p = h;
    int i = 0;
    while (p) {
        m[p] = i++;
        p = p->next;
    }
    while (h) {
        v.push_back({to_string(h->val), h->random ? to_string(m[h->random]) : "null"});
        h = h->next;
    }
    string ret = "[";
    for (int i = 0; i < v.size(); ++i) {
        ret += "[";
        for (int j = 0; j < v[i].size(); ++j) {
            ret += v[i][j];
            if (j != v[i].size() - 1) {
                ret += ",";
            }
        }
        ret += "]";
        if (i != v.size() - 1) {
            ret += ",";
        }
    }
    ret += "]";
    cout << ret << endl;
}

int main() {
    Node *head = new Node(7);
    head->next = new Node(13);
    head->next->next = new Node(11);
    head->next->next->next = new Node(10);
    head->next->next->next->next = new Node(1);
    head->random = nullptr;
    head->next->random = head;
    head->next->next->random = head->next->next->next->next;
    head->next->next->next->random = head->next->next;
    head->next->next->next->next->random = head;
    Solution s;
    print(s.copyRandomList(head));
    return 0;
}
