// 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。

#include <iostream>
#include <vector>
#include <deque>
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
        deque<TreeNode *> q1, q2;
        q1.push_back(pRoot);
        TreeNode *c;
        while (!q1.empty() || !q2.empty()) {
            if (!q1.empty()) {
                vector<int> v;
                while (!q1.empty()) {
                    c = q1.front();
                    v.push_back(c->val);
                    q1.pop_front();
                    if (c->left) {
                        q2.push_back(c->left);
                    }
                    if (c->right) {
                        q2.push_back(c->right);
                    }
                }
                r.push_back(v);
            }
            if (!q2.empty()) {
                vector<int> v;
                while (!q2.empty()) {
                    c = q2.front();
                    v.push_back(c->val);
                    q2.pop_front();
                    if (c->left) {
                        q1.push_back(c->left);
                    }
                    if (c->right) {
                        q1.push_back(c->right);
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
