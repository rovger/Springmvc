package com.rovger.leetcode;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 14:37
 */
public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    int deep;

    public TreeNode () {}
    public TreeNode (int val) {
        this.val = val;
    }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
