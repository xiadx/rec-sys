// 输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)

#include <iostream>
#include <vector>
using namespace std;

struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
public:
    vector<vector<int> > FindPath(TreeNode *root, int expectNumber) {
        vector<vector<int> > r;
        if (root == NULL) {
            return r;
        }
        if (root->val == expectNumber && root->left == NULL && root->right == NULL) {
            vector<int> v(1, root->val);
            r.push_back(v);
            return r;
        }
        vector<vector<int> > left = FindPath(root->left, expectNumber - root->val);
        vector<vector<int> > right = FindPath(root->right, expectNumber - root->val);
        for (int i = 0; i < left.size(); ++i) {
            vector<int> v = left[i];
            v.insert(v.begin(), root->val);
            r.push_back(v);
        }
        for (int i = 0; i < right.size(); ++i) {
            vector<int> v = right[i];
            v.insert(v.begin(), root->val);
            r.push_back(v);
        }
        return r;
    }
};

void print(vector<vector<int> > r) {
    for (int i = 0; i < r.size(); ++i) {
        vector<int> v = r[i];
        vector<int>::iterator it;
        for (it = v.begin(); it != v.end(); ++it) {
            cout << *it << " ";
        }
        cout << endl;
    }
}

int main() {
    TreeNode *r = new TreeNode(1);
    r->left = new TreeNode(2);
    r->right = new TreeNode(3);
    r->left->left = new TreeNode(1);
    Solution s;
    print(s.FindPath(r, 4));
    return 0;
}
