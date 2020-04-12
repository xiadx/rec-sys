// 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）

#include <iostream>
using namespace std;

struct RandomListNode {
    int label;
    struct RandomListNode *next, *random;
    RandomListNode(int x) : label(x), next(NULL), random(NULL) {}
};

class Solution {
public:
    RandomListNode *Clone(RandomListNode *pHead) {
        if (pHead == NULL) {
            return NULL;
        }
        RandomListNode *p = pHead;
        RandomListNode *q = NULL;
        while (p != NULL) {
            q = new RandomListNode(p->label);
            q->next = p->next;
            p->next = q;
            p = q->next;
        }
        p = pHead;
        while (p != NULL) {
            q = p->next;
            if (p->random != NULL) {
                q->random = p->random->next;
            }
            p = q->next;
        }
        p = pHead;
        RandomListNode *h = p->next;
        while (p->next != NULL) {
            q = p->next;
            p->next = q->next;
            p = q;
        }
        return h;
    }
};

void print(RandomListNode *h) {
    while (h != NULL) {
        cout << h->label << " ";
        h = h->next;
    }
    cout << endl;
}

int main() {
    RandomListNode *h = new RandomListNode(1);
    h->next = new RandomListNode(2);
    h->next->next = new RandomListNode(3);
    h->random = h->next->next;
    Solution s;
    print(s.Clone(h));
    return 0;
}
