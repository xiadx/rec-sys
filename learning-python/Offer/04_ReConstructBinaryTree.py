#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。"""


class TreeNode:

    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None


class Solution:

    def reConstructBinaryTree(self, pre, tin):
        if len(tin) == 0:
            return None
        head = TreeNode(pre[0])
        r = 0
        for i, e in enumerate(tin):
            if e == pre[0]:
                r = i
                break
        preLeft, inLeft, preRight, inRight = [], [], [], []
        for i in range(r):
            preLeft.append(pre[i + 1])
            inLeft.append(tin[i])
        for i in range(r + 1, len(tin)):
            preRight.append(pre[i])
            inRight.append(tin[i])
        head.left = self.reConstructBinaryTree(preLeft, inLeft)
        head.right = self.reConstructBinaryTree(preRight, inRight)
        return head


def traverse(root):
    if root is not None:
        print root.val, " ",
        traverse(root.left)
        traverse(root.right)


def main():
    pre = [1, 2, 4, 7, 3, 5, 6, 8]
    tin = [4, 7, 2, 1, 5, 3, 8, 6]
    s = Solution()
    root = s.reConstructBinaryTree(pre, tin)
    traverse(root)


if __name__ == "__main__":
    main()
