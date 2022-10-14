package com.rovger.threads.performance;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: 比较CountDownLatch与CyclicBarrier与Semaphore
 * @Author weijlu
 * @Date 2019/4/25 10:55
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new CountDownLatchRunnable(i, latch)).start();
        }
        latch.await();//调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        // 有个问题 main线程提前结束
        System.out.println("线程执行结束...");
    }

    static class CountDownLatchRunnable implements Runnable {
        int index;
        CountDownLatch latch;

        public CountDownLatchRunnable(int index, CountDownLatch latch) {
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println("index:" + index);
                latch.countDown();//将count值减1
                System.out.println("线程组任务" + index + "结束，其他任务继续");
            }
        }
    }

}
