package offer;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

/**
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
 */
public class ReConstructBinaryTree {

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        int inLen = in.length;
        if (inLen == 0) {
            return null;
        }
        TreeNode head = new TreeNode(pre[0]);
        int r = 0;
        for (int i = 0; i < inLen; i++) {
            if (in[i] == pre[0]) {
                r = i;
                break;
            }
        }
        int[] preLeft = new int[r];
        int[] preRight = new int[inLen - r - 1];
        int[] inLeft = new int[r];
        int[] inRight = new int[inLen - r - 1];
        for (int i = 0; i < r; i++) {
            preLeft[i] = pre[i + 1];
            inLeft[i] = in[i];
        }
        for (int i = r + 1; i < inLen; i++) {
            preRight[i - r - 1] = pre[i];
            inRight[i - r - 1] = in[i];
        }
        head.left = reConstructBinaryTree(preLeft, inLeft);
        head.right = reConstructBinaryTree(preRight, inRight);
        return head;
    }

    public static void print(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + " ");
            print(root.left);
            print(root.right);
        }
    }

    public static void main(String[] args) {
        int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] in = {4, 7, 2, 1, 5, 3, 8, 6};
        ReConstructBinaryTree rcbt = new ReConstructBinaryTree();
        TreeNode root = rcbt.reConstructBinaryTree(pre, in);
        print(root);
    }

}
