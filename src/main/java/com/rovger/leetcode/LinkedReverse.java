package com.rovger.leetcode;

/**
 * @Description: 链表反转 - 9
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 16:49
 */
public class LinkedReverse {

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        ListNode pre = test(node1);
    }

    /**
     * 递归
     * @param head
     * @return
     */
    private static ListNode test(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode temp = test(head.next);
        // 1->2->3->4->5
        head.next.next = head;
        head.next = null;

        return temp;
    }



    /**
     * 尾 递归法
     * @param head
     * @return
     */
    private static ListNode revert2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 避免next断掉，从后往前递归
        ListNode tail = revert2(head.next);

        head.next.next = head;
        head.next = null;

        return tail;
    }


    /**
     * 迭代法
     * @param head
     */
    private static ListNode revert(ListNode head) {
        // 变量
        ListNode prev = null, temp;
        ListNode cur = head;
        while (cur != null) {
            temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }
}
