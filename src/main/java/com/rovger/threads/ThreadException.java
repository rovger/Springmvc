package com.rovger.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 测试InterruptedException
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年01月11日 18:21
 */
public class ThreadException {

    static Logger log = LoggerFactory.getLogger(ThreadException.class);

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试案例....");
            }
        }, "weijie-thread");
        t.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

}
