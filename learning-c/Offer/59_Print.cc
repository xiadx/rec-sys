// 请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推。

#include <iostream>
#include <vector>
#include <stack>
using namespace std;

struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
public:
    vector<vector<int> > Print(TreeNode *pRoot) {
        vector<vector<int> > r;
        if (pRoot == NULL) {
            return r;
        }
        stack<TreeNode *> s1, s2;
        s1.push(pRoot);
        TreeNode *c;
        while (!s1.empty() || !s2.empty()) {
            if (!s1.empty()) {
                vector<int> v;
                while (!s1.empty()) {
                    c = s1.top();
                    v.push_back(c->val);
                    s1.pop();
                    if (c->left) {
                        s2.push(c->left);
                    }
                    if (c->right) {
                        s2.push(c->right);
                    }
                }
                r.push_back(v);
            }
            if (!s2.empty()) {
                vector<int> v;
                while (!s2.empty()) {
                    c = s2.top();
                    v.push_back(c->val);
                    s2.pop();
                    if (c->right) {
                        s1.push(c->right);
                    }
                    if (c->left) {
                        s1.push(c->left);
                    }
                }
                r.push_back(v);
            }
        }
        return r;
    }
};

void print(vector<vector<int> > v) {
    for (int i = 0; i < v.size(); ++i) {
        for (int j = 0; j < v[i].size(); ++j) {
            cout << v[i][j] << " ";
        }
        cout << endl;
    }
}

int main() {
    TreeNode *r = new TreeNode(1);
    r->left = new TreeNode(2);
    r->right = new TreeNode(3);
    Solution s;
    print(s.Print(r));
    return 0;
}
