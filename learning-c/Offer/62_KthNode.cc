// 给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。

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
    void inorder(TreeNode *root, TreeNode *&ans) {
        if (root) {
            inorder(root->left, ans);
            count--;
            if (!count) ans = root;
            inorder(root->right, ans);
        }
    }
    TreeNode *KthNode(TreeNode *pRoot, int k) {
       if (!pRoot || k < 1) return NULL;
       TreeNode *ans = NULL;
       count = k;
       inorder(pRoot, ans);
       return ans;
    }
private:
    int count;
};

int main() {
    TreeNode *r = new TreeNode(2);
    r->left = new TreeNode(1);
    r->right = new TreeNode(3);
    Solution s;
    cout << s.KthNode(r, 1)->val << endl;
    return 0;
}
