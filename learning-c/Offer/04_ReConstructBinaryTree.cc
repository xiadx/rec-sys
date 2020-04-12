// 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。

#include <iostream>
#include <vector>
using namespace std;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

void print(TreeNode *r) {
    if (r != NULL) {
        cout << r->val << " ";
        print(r->left);
        print(r->right);
    }
}

class Solution {
public:
    struct TreeNode *reConstructBinaryTree(vector<int> pre, vector<int> in) {
        int inLen = in.size();
        if (inLen == 0) {
            return NULL;
        }
        vector<int> leftPre, leftIn, rightPre, rightIn;
        TreeNode *head = new TreeNode(pre[0]);
        int r = 0;
        for (int i = 0; i < inLen; ++i) {
            if (in[i] == pre[0]) {
                r = i;
                break;
            }
        }
        for (int i = 0; i < r; ++i) {
            leftPre.push_back(pre[i + 1]);
            leftIn.push_back(in[i]);
        }
        for (int i = r + 1; i < inLen; ++i) {
            rightPre.push_back(pre[i]);
            rightIn.push_back(in[i]);
        }
        head->left = reConstructBinaryTree(leftPre, leftIn);
        head->right = reConstructBinaryTree(rightPre, rightIn);
        return head;
    }
};

int main() {
    int a[8] = {1, 2, 4, 7, 3, 5, 6, 8};
    int b[8] = {4, 7, 2, 1, 5, 3, 8, 6};
    vector<int> pre(a, a + 8);
    vector<int> in(b, b + 8);
    Solution s;
    TreeNode *head = s.reConstructBinaryTree(pre, in);
    print(head);
    cout << endl;
    return 0;
}
