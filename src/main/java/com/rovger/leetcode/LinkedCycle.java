package com.rovger.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 判断环形链表
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 10:57
 */
public class LinkedCycle {

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
//        node5.next = node3;

        System.out.println(hasCycle2(node1));
    }

    /**
     * 解法2：双指针算法，占用空间复杂度小，时间复杂度与解法1都是0（N）
     * @param node
     * @return
     */
    private static boolean hasCycle2(ListNode node) {
        if (node == null || node.next == null) {
            return false;
        }
        ListNode slow = node;
        ListNode fast =  node.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 解法1：迭代
     * @param node
     * @return
     */
    private static boolean hasCycle(ListNode node) {
        Set<ListNode> set = new HashSet<>();
        while (node.next != null) {
            if (!set.add(node)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }
}
