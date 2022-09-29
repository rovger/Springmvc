package com.rovger.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description: 二叉树遍历（递归，1-5），迭代方式暂时先不看了
 *
 * 树：
 * 二叉树：无序
 * BST(二分查找树)：有序，左子树<root，右子树>root
 * AVL(平衡二叉树)：通过旋转，平衡插入和查找效率，保证O(logn)
 * 红黑树
 * B树：支持范围，有"度"的概念
 * B+树：子节点存储数据
 *
 *
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 12:06
 */
public class BinaryTree_digui {

    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7, null, null);
        TreeNode node6 = new TreeNode(6, null, null);
        TreeNode node5 = new TreeNode(5, node6, node7);
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node3 = new TreeNode(3, null, null);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode node1 = new TreeNode(1, node2, node3);

//        pre(node1);
//        mid(node1);
//        suf(node1);

//        List<Integer> vals = new ArrayList<>();
//        levelSort(node1, 1, vals);
//        System.out.println(Arrays.toString(vals.toArray()));

        levelSort2(node1);
    }

    /**
     * （递归）前序遍历：根左右，第一次成为栈顶元素就要打印
     *
     * 通过栈模型加以理解：遇到子节点为null，直接弹出，"打印栈顶"的逻辑
     *
     * 栈递归遍历
     * @param root
     */
    private static void pre(TreeNode root) {
        // 递归判断，跳出递归
        if (root == null) {
            return;
        }
        System.out.println(root.val);

        pre(root.left);
        pre(root.right);
    }

    /**
     * （递归）中序遍历：左根右，第二次成为栈顶元素就要打印
     * @param root
     */
    private static void mid(TreeNode root) {
        if (root == null) {
            return;
        }
        mid(root.left);
        System.out.println(root.val);
        mid(root.right);
    }

    /**
     * （递归）后序遍历：左右根，第三次成为栈顶元素就要打印
     * @param root
     */
    private static void suf(TreeNode root) {
        if (root == null) {
            return;
        }
        suf(root.left);
        suf(root.right);
        System.out.println(root.val);
    }

    /**
     * （递归）层序遍历
     * @param root
     * @param i
     * @param list，左节点在list index=2*i，右节点在list index=2*i+1
     */
    private static void levelSort(TreeNode root, int i, List<Integer> list) {
        if (root == null) {
            return;
        }

        // 填充null
        int length = list.size();
        if (length <= i) {
            for (int j=0; j<=i-length; j++) {
                list.add(length+j, null);
            }
        }

        list.set(i, root.val);
        levelSort(root.left, 2*i, list);
        levelSort(root.right, 2*i+1, list);
    }

    /**
     * （推荐）层序遍历2：队列，从上往下 从左往右，借助队列，先进先出
     * @param root
     */
    private static void levelSort2(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                System.out.print(node.val);
                queue.add(node.left);
                queue.add(node.right);
            }
        }
    }

}
