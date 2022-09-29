package com.rovger.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 假如有一个需求，要读取几万个文件的数据，因为都是IO密集型任务，我们可以启动几十个线程并发的读取，但是如果读到内存后，还需要存储到数据库中，
 * 而数据库的连接数只有10个，这时我们必须控制只有十个线程同时获取数据库连接保存数据，否则会报错无法获取数据库连接。
 * 这个时候，我们就可以使用Semaphore来做流控
 * @Author weijlu
 * @Date 2019/4/25 14:10
 */
public class SemaphoreDemo {

    private static final int threadSize = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(threadSize);
    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < threadSize; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //release函数和acquire并没有要求一定是同一个线程都调用，可以A线程申请资源，B线程释放资源
                        //调用release函数之前并没有要求一定要先调用acquire函数。
                        semaphore.acquire();
                        System.out.println("save data ====> " + counter.getAndIncrement());
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
