package com.rovger.utils;

/**
 * @Description: 链表及其结构
 * @Author weijlu
 * @Date 2019/4/26 10:21
 */
public class ChainTable<K, V> {

    /**
     * 无环情况下，判断两个链表是否相交，只需要遍历链表，判断尾节点是否相等即可。
     *
     * @param h1
     * @param h2
     * @return
     */
    public static boolean isJoinNoLoop(DataNode h1, DataNode h2) {
        DataNode p1 = h1;
        DataNode p2 = h2;
        while (null != p1.getNext())
            p1 = p1.getNext();
        while (null != p2.getNext())
            p2 = p2.getNext();
        return p1 == p2;
    }

    /**
     * 无环情况下找到第一个相交点
     * 方法： 算出两个链表的长度差为x,长链表先移动x步，之后两链表同时移动，直到相遇的第一个交点。
     *
     * @param h1
     * @param h2
     * @return
     */
    public static DataNode getFirstJoinNode(DataNode h1, DataNode h2) {
        int length1 = 0;
        int length2 = 0;
        while (null != h1.getNext()) {
            length1++;
            h1 = h1.getNext();
        }
        while (null != h2.getNext()) {
            length1++;
            h2 = h2.getNext();
        }
        return getNode(h1, length1, h2, length2);
    }

    private static DataNode getNode(DataNode h1, int length1, DataNode h2, int length2) {
        int gap = length1 - length2;
        for (int i = 0; i < length1; i++) {
            if (i < Math.abs(gap)) {
                h1 = h1.getNext();
                continue;
            }
            h2 = h2.getNext();
            if (h1 == h2) return h1;
        }
        return null;
    }

    /**
     * 判断是否存在环
     * 步骤：设置两个指针同时指向head，其中一个一次前进一个节点（P1），另外一个一次前进两个节点(P2)。
     * p1和p2同时走，如果其中一个遇到null，则说明没有环，如果走了N步之后，二者指向地址相同，那么说明链表存在环。
     *
     * @param h
     * @return
     */
    public static boolean isLoop(DataNode h) {
        DataNode p1 = h;
        DataNode p2 = h;
        while (p2.getNext() != null && p2.getNext().getNext() != null) {
            p1 = p1.getNext();
            p2 = p2.getNext().getNext();
            if (p1 == p2)
                break;
        }
        return !(p1 == null || p2 == null);
    }

    static class DataNode<V> {
        private V v;
        private DataNode next;

        public V getV() {
            return v;
        }

        public void setV(V v) {
            this.v = v;
        }

        public DataNode getNext() {
            return next;
        }

        public void setNext(DataNode next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        System.out.println(10024 == 10024);
    }
}
