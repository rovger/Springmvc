package com.rovger.demo;

import java.util.concurrent.CyclicBarrier;

/**
 * @Description: TODO
 * @Author weijlu
 * @Date 2019/4/25 13:44
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        //参数parties指让多少个线程或者任务等待至barrier状态
        // 参数barrierAction为当这些线程都达到barrier状态时会执行的内容。
        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("线程组执行结束");
            }
        });
        for (int i=0; i<5; i++) {
            new Thread(new CyclicBarrierRunnable(i, barrier)).start();
        }
    }

    static class CyclicBarrierRunnable implements Runnable {
        int index;
        CyclicBarrier barrier;
        public CyclicBarrierRunnable(int index, CyclicBarrier barrier) {
            this.index = index;
            this.barrier = barrier;
        }
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("id:"+ index);
                try {
                    barrier.await();
                    System.out.println("线程组任务"+ index +"结束，其他任务继续");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
