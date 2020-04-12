// 请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。

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
    void mirror(TreeNode *root) {
        if (root == NULL) return;
        TreeNode *left = root->left;
        TreeNode *right = root->right;
        mirror(left);
        mirror(right);
        root->left = right; root->right = left;
    }
    bool sample(TreeNode *r1, TreeNode *r2) {
        if (r1 == NULL && r2 == NULL) return true;
        return r1 && r2 && (r1->val == r2->val) && sample(r1->left, r2->left) && sample(r1->right, r2->right);
    }
    bool isSymmetrical(TreeNode *pRoot) {
        if (pRoot == NULL) return true;
        TreeNode *left = pRoot->left;
        TreeNode *right = pRoot->right;
        mirror(right);
        return sample(left, right);
    }
};

int main() {
    TreeNode *r = new TreeNode(1);
    r->left = new TreeNode(2);
    r->right = new TreeNode(2);
    Solution s;
    cout << s.isSymmetrical(r) << endl;
    return 0;
}
