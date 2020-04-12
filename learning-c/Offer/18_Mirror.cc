// 操作给定的二叉树，将其变换为源二叉树的镜像。

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
    void *Mirror(TreeNode *pRoot) {
        if (pRoot == NULL) {
            return NULL;
        }
        TreeNode *left = pRoot->left, *right = pRoot->right;
        Mirror(left);
        Mirror(right);
        pRoot->left = right; pRoot->right = left;
        return pRoot;
    }
};

void print(TreeNode *r) {
    if (r != NULL) {
        cout << r->val << " ";
        print(r->left);
        print(r->right);
    }
}

int main() {
    TreeNode *r = new TreeNode(1);
    r->left = new TreeNode(2); r->right = new TreeNode(3);
    Solution s;
    s.Mirror(r);
    print(r);
    cout << endl;
    return 0;
}
