// 输入一棵二叉树，判断该二叉树是否是平衡二叉树。

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
    int depth(TreeNode *pRoot) {
        if (pRoot == NULL) {
            return 0;
        }
        return max(depth(pRoot->left), depth(pRoot->right)) + 1;
    }
    bool IsBalanced_Solution(TreeNode *pRoot) {
        if (pRoot == NULL) {
            return true;
        }
        int left = depth(pRoot->left);
        int right = depth(pRoot->right);
        if (left == right || abs(left - right) == 1) {
            return IsBalanced_Solution(pRoot->left) && IsBalanced_Solution(pRoot->right);
        } else {
            return false;
        }
    }
};

int main() {
    TreeNode *r = new TreeNode(1);
    Solution s;
    cout << s.IsBalanced_Solution(r) << endl;
    return 0;
}
