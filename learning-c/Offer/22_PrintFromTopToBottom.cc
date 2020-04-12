// 从上往下打印出二叉树的每个节点，同层节点从左至右打印。

#include <iostream>
#include <vector>
#include <queue>
using namespace std;

struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
public:
    vector<int> PrintFromTopToBottom(TreeNode *root) {
        vector<int> r;
        if (root == NULL) {
            return r;
        }
        queue<TreeNode *> q;
        TreeNode *v;
        q.push(root);
        while (!q.empty()) {
            v = q.front();
            r.push_back(v->val);
            if (v->left != NULL) {
                q.push(v->left);
            }
            if (v->right != NULL) {
                q.push(v->right);
            }
            q.pop();
        }
        return r;
    }
};

void print(vector<int> v) {
    vector<int>::iterator it;
    for (it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    TreeNode *r = new TreeNode(1);
    r->left = new TreeNode(2);
    r->right = new TreeNode(3);
    Solution s;
    print(s.PrintFromTopToBottom(r));
    return 0;
}
