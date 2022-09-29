package com.rovger.leetcode;

/**
 * @Description: 二分查找树，插入、删除、搜索
 * BST(二分查找树)：有序，左子树 < root < 右子树
 *
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年05月05日 09:06
 */
public class BinarySearchTree {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1, null, null);
        TreeNode node3 = new TreeNode(3, null, null);
        TreeNode node2 = new TreeNode(2, node1, node3);
        TreeNode node7 = new TreeNode(7, null, null);
        TreeNode node4 = new TreeNode(4, node2, node7);

        // 插入操作
        TreeNode rst1 = insertIntoBST(node4, 5);
        // 删除操作
        TreeNode rst2 = delBSTNode(node4, 4);
        // 搜索操作
        TreeNode rst3 = searchBST(node4, 2);
    }

    // 中序遍历，获取"后继节点"：表示中序遍历的下一个节点，即：比当前节点大的最小节点
    private static int successor(TreeNode root) {
        root = root.right;
        while (root.left != null) root = root.left;
        return root.val;
    }

    // "前驱节点"：表示中序遍历的前一个节点，即：比当前节点小的最大节点
    private static int preorder(TreeNode root) {
        root = root.left;
        while (root.right != null) root = root.right;
        return root.val;
    }

    // 递归法
    private static TreeNode delBSTNode(TreeNode root, int val) {
        if (root == null) return null; // 直接置空，即删除
        if (val > root.val) {
            root.right = delBSTNode(root.right, val);
        } else if (val < root.val) {
            root.left = delBSTNode(root.left, val);
        } else {
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.right != null) {
                root.val = successor(root); // 中序遍历（left < root < right），获取当前删除节点的"后继节点" 替代root节点
                root.right = delBSTNode(root.right, root.val); // 递归删除该后继节点
            } else {
                root.val = preorder(root); // 获取当前删除节点的"后继节点" 替代root节点
                root.left = delBSTNode(root.left, root.val);
            }
        }
        return root;
    }

    // 迭代法
    private static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        TreeNode pos = root;
        while (pos != null) {
            if (pos.val > val) {
                if (pos.left == null) {
                    pos.left = new TreeNode(val);
                    break;
                } else {
                    pos = pos.left;
                }
            } else {
                if (pos.right == null) {
                    pos.right = new TreeNode(val);
                    break;
                } else {
                    pos = pos.right;
                }
            }
        }
        return root;
    }

    private static TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;

        return searchBST(val > root.val ? root.right : root.left, val);
    }
}
