package com.rovger.leetcode;

import java.util.Stack;

/**
 * @Description: 二叉树遍历（迭代，6-8）
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 12:06
 */
public class BinaryTree_diedai {

    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7, null, null);
        TreeNode node6 = new TreeNode(6, null, null);
        TreeNode node5 = new TreeNode(5, node6, node7);
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node3 = new TreeNode(3, null, null);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode node1 = new TreeNode(1, node2, node3);

//        pre(node1);
        mid(node1);
//        suf(node1);

    }

    /**
     * （迭代）前序遍历：根左右，第一次成为栈顶元素就要打印
     * 栈递归遍历
     * @param root
     */
    private static void pre(TreeNode root) {
        // 迭代 循环
        if (root != null) {
            Stack<TreeNode> stack = new Stack();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                if (root != null) {
                    System.out.println(root.val);
                    stack.push(root.right);
                    stack.push(root.left);
                }
            }
        }
    }

    /**
     * (迭代)中序遍历：左中右，第二次成为栈顶元素就要打印
     * @param root
     */
    private static void mid(TreeNode root) {
        if (root != null) {
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    System.out.println(root.val);
                    root = root.right;
                }
            }
        }
    }

    /**
     * (迭代)后序遍历：左中右，第三次成为栈顶元素就要打印
     * @param root
     */
    private static void suf(TreeNode root) {

    }

}
