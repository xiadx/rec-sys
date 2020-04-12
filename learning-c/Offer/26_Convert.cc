// 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。

#include <iostream>
using namespace std;

struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
public:
    TreeNode *Convert(TreeNode *pRootOfTree) {
        if (pRootOfTree == NULL) {
            return NULL;
        }
        TreeNode *root = pRootOfTree;
        Convert(root->left);
        TreeNode *left = root->left;
        if (left != NULL) {
            while (left->right != NULL) {
                left = left->right;
            }
            root->left = left;
            left->right = root;
        }
        Convert(root->right);
        TreeNode *right = root->right;
        if (right != NULL) {
            while (right->left) {
                right = right->left;
            }
            root->right = right;
            right->left = root;
        }
        while (root->left != NULL) {
            root = root->left;
        }
        return root;
    }
};

void print(TreeNode *r) {
    while (r != NULL) {
        cout << r->val << " ";
        r = r->right;
    }
    cout << endl;
}

int main() {
    TreeNode *r = new TreeNode(2);
    r->left = new TreeNode(1);
    r->right = new TreeNode(3);
    Solution s;
    print(s.Convert(r));
    return 0;
}
