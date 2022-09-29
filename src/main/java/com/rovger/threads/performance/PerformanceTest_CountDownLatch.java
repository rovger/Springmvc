package com.rovger.threads.performance;

import org.springframework.util.StopWatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: weijlu
 * @Description: 使用CountDownLatch实现多线程并发测试，主线程监控所有线程子线程执行完毕后 停止
 * @Date: 2019-10-19 07:02
 */

public class PerformanceTest_CountDownLatch {
    public static void main(String[] args) throws InterruptedException {

        StopWatch stopWatch = new StopWatch("PerformanceTest_CountDownLatch压测开始...");
        stopWatch.start();

        // 初始化计数器为 1
        CountDownLatch start = new CountDownLatch(10);
        CountDownLatch end = new CountDownLatch(10);

        //模擬16个线程
        for (int i = 0; i < 10; i++) {
            new Thread(new TestThread_CountDownLatch(start, end)).start();
        }

        // 阻塞，知道计数器为0，触发所有线程继续向下执行
        start.await();
        end.await();

        //计数器为0，所有线程释放，同时并发
        stopWatch.stop();
        System.out.println("消耗总时间" + stopWatch.prettyPrint());
    }

    static class TestThread_CountDownLatch implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch endSignal;

        public TestThread_CountDownLatch(CountDownLatch startSignal, CountDownLatch endSignal) {
            super();
            this.startSignal = startSignal;
            this.endSignal = endSignal;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 等待执行...");
            //一直阻塞当前线程，直到计时器的值为0
            startSignal.countDown();
            //实际测试操作
            doWork();
        }

        private void doWork() {
            OrderTest test = new OrderTest();
            try {
                test.distributionTest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        class OrderTest {
            public void distributionTest() throws InterruptedException {
                TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                System.out.println(Thread.currentThread().getName() + " 执行完成！");
                // 子线程结束标志
                endSignal.countDown();
            }
        }
    }
}
