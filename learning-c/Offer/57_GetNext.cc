// 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。

#include <iostream>
using namespace std;

struct TreeLinkNode {
    int val;
    struct TreeLinkNode *left;
    struct TreeLinkNode *right;
    struct TreeLinkNode *next;
    TreeLinkNode(int x) : val(x), left(NULL), right(NULL), next(NULL) {}
};

class Solution {
public:
    TreeLinkNode *GetNext(TreeLinkNode *pNode) {
        if (pNode == NULL) {
            return NULL;
        }
        if (pNode->right != NULL) {
            TreeLinkNode *r = pNode->right;
            while (r->left) {
                r = r->left;
            }
            return r;
        } else {
            TreeLinkNode *p = pNode->next;
            TreeLinkNode *c = pNode;
            while (p && p->left != c) {
                c = p;
                p = p->next;
            }
            return p;
        }
    }
};

int main() {
    TreeLinkNode *r = new TreeLinkNode(1);
    r->left = new TreeLinkNode(2);
    r->right = new TreeLinkNode(3);
    r->left->next = r;
    r->right->next = r;
    Solution s;
    cout << s.GetNext(r->left)->val << endl;
    return 0;
}
