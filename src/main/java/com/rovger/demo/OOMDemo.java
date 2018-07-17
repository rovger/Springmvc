package com.rovger.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 制造OOM异常
 */
public class OOMDemo {

    public static void main(String[] args) {
        /**
         * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
         */
        List heapOomList = new ArrayList();
        for (int i=0; i<10000; i++) {
            heapOomList.add(new byte[1024000]);
        }
    }
}
