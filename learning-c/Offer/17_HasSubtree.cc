// 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）

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
    bool same(TreeNode *r1, TreeNode *r2) {
        if (r2 == NULL) {
            return true;
        }
        if (r1 == NULL) {
            return false;
        }
        if (r1->val != r2->val) {
            return false;
        } else {
            return same(r1->left, r2->left) && same(r1->right, r2->right);
        }
    }
    bool HasSubtree(TreeNode *pRoot1, TreeNode *pRoot2) {
        if (pRoot1 == NULL) {
            return false;
        }
        if (pRoot2 == NULL) {
            return false;
        }
        return same(pRoot1, pRoot2) || HasSubtree(pRoot1->left, pRoot2) || HasSubtree(pRoot1->right, pRoot2);
    }
};

int main() {
    TreeNode *r1 = new TreeNode(1);
    r1->left = new TreeNode(2);
    r1->right = new TreeNode(3);
    TreeNode *r2 = new TreeNode(2);
    Solution s;
    cout << s.HasSubtree(r1, r2) << endl;
    return 0;
}
