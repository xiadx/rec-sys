// 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。

#include <iostream>
#include <vector>
#include <stack>
using namespace std;

template<class T>
void print(const vector<T> &v) {
    typename vector<T>::const_iterator iter;
    for (iter = v.begin(); iter != v.end(); ++iter) {
        cout << *iter << " ";
    }
    cout << endl;
}

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    vector<int> printListFromTailToHead(ListNode *head) {
        vector<int> r;
        stack<int> s;
        ListNode *p = head;
        while (p != NULL) {
            s.push(p->val);
            p = p->next;
        }
        int n = s.size();
        for (int i = 0; i < n; ++i) {
            r.push_back(s.top());
            s.pop();
        }
        return r;
    }
};

int main() {
    ListNode *head = new ListNode(1);
    head->next = new ListNode(2);
    head->next->next = new ListNode(3);
    Solution s;
    vector<int> r = s.printListFromTailToHead(head);
    print(r);
    return 0;
}
