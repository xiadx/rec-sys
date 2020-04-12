// 请实现两个函数，分别用来序列化和反序列化二叉树
// 二叉树的序列化是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使得内存中建立起来的二叉树可以持久保存。序列化可以基于先序、中序、后序、层序的二叉树遍历方式来进行修改，序列化的结果是一个字符串，序列化时通过 某种符号表示空节点（#），以 ！ 表示一个结点值的结束（value!）。
// 二叉树的反序列化是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。

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
    char *Serialize(TreeNode *root) {
        if (!root) return (char *)"#";
        string r = to_string(root->val);
        r.push_back(',');
        char *left = Serialize(root->left);
        char *right = Serialize(root->right);
        char *ret = new char[strlen(left) + strlen(right) + r.size()];
        strcpy(ret, r.c_str());
        strcat(ret, left);
        strcat(ret, right);
        return ret;
    }
    TreeNode *Deserialize(char *&str) {
        if (*str == '#') {
            str++;
            return NULL;
        }
        int num = 0;
        while (*str != ',') {
            num = (num << 1) + (num << 3) + (*(str++) & 0xf);
        }
        str++;
        TreeNode *root = new TreeNode(num);
        root->left = Deserialize(str);
        root->right = Deserialize(str);
        return root;
    }
};

int main() {
    TreeNode *root = new TreeNode(1);
    root->left = new TreeNode(2);
    root->right = new TreeNode(3);
    Solution s;
    cout << s.Serialize(root) << endl;
    char *str = s.Serialize(root);
    TreeNode *r = s.Deserialize(str);
    cout << "root:" << r->val << ",left:" << r->left->val << ",right:" << r->right->val << endl;
    return 0;
}
