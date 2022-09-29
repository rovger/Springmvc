package com.rovger.threads.performance;

import org.springframework.util.StopWatch;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 使用CyclicBarrier实现多线程并发测试，主线程监控所有线程子线程执行完毕后 停止
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年09月15日 09:37
 */
public class PerformanceTest_CyclicBarrier {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        StopWatch stopWatch = new StopWatch("PerformanceTest_CyclicBarrier压测开始...");
        stopWatch.start();

        // start==0时，触发并发环境
        CountDownLatch start = new CountDownLatch(10);
        // parties==10时，会执行此回调
        CyclicBarrier end = new CyclicBarrier(10, () -> {
            stopWatch.stop();
            System.out.println("消耗总时间" + stopWatch.prettyPrint());
        });

        int counter = 10;
        do {
            counter--;
            new Thread(new ThreadTest_CyclicBarrier(start, end)).start();
        } while (counter > 0);
    }

    static class ThreadTest_CyclicBarrier implements Runnable {
        private CountDownLatch start;
        private CyclicBarrier end;

        public ThreadTest_CyclicBarrier(CountDownLatch start, CyclicBarrier end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
                System.out.println(Thread.currentThread().getName() + "已创建，等待执行...");

                // start计数减减
                start.countDown();

                doWork();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() throws InterruptedException, BrokenBarrierException {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
            System.out.println(Thread.currentThread().getName() + "执行完成！");

            // CyclicBarrier 计数加加
            end.await();
        }
    }
}
