package com.rovger.demo;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.*;

/**
 * 制造OOM异常
 */
public class OOMDemo {

    private static ReferenceQueue queue = new ReferenceQueue();

    public static void main(String[] args) {
//        WeakHashMap wmap = new WeakHashMap();
//        Map sync_map = Collections.synchronizedMap(wmap);
        String str = new String("hello");
        SoftReference sr = new SoftReference(str, queue);
//        WeakReference sr = new WeakReference(str, queue);
        str = null;
        System.gc();
        System.out.println((String)sr.get());
        Reference ref = queue.poll();
        if (ref == null) {
            System.out.println("queue is empty");
        }
//        oomTest();
    }

    private static void oomTest() {
        /**
         * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
         */
        List heapOomList = new ArrayList();
        for (int i=0; i<10000; i++) {
            heapOomList.add(new byte[1024000]);
        }
    }
}
