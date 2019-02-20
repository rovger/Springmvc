package com.rovger.design.buy_ticket;

import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description: 生产者与消费者，在多线程下的实现
 * wait/notify/notifyAll的使用
 * @Author weijlu
 * @Date 2019/2/19 15:00
 */
public class ProducerConsumerDemo {

    static int maxQueueSize = 10;
    static PriorityQueue<Integer> queue = new PriorityQueue<>(10);

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer());
        Thread consumer = new Consumer();
        producer.start();
        consumer.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //中断标志设置为true，如果线程处于阻塞状态，那么线程将直接被interrupt所终止
        producer.interrupt();
        consumer.interrupt();

        /*try {//一个运行状态的线程，一旦其终端标志位为true，一旦线程调用了wait，sleep，join方法，立马抛出一个InterruptedException异常
            producer.join();
            consumer.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }*/

        System.out.println("主线程运行结束。。。");

        //使用Callable和Future来创建线程和接收线程参数
        FutureTask<Integer> future = new FutureTask(new Callable<Integer>() {
            @Override
            public Integer call() {
                return queue.size();
            }
        });
        Thread thread = new Thread(future);
        thread.start();
        try {
            int queueSize = future.get();
            System.out.println("当前queue包含元素数量："+ queueSize);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        future.cancel(true);
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {//保证只要中断标记为true就终止线程。
                    synchronized (queue) {
                        while (queue.size() == maxQueueSize) {
                            System.out.println("队列满，等待剩余空间。");
                            queue.wait();
                        }
                        queue.offer(1);
                        queue.notify();
                        System.out.println("从队列中插入一个元素，队列剩余" + queue.size() + "个元素。");
                    }
                }
            } catch (InterruptedException ie) {
                System.out.println("Producer is interrupted.");
            }
        }
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {//采用isInterrupted()整个try catch不会抛出异常
                    synchronized (queue) {
                        while (queue.size() == 0) {
                            System.out.println("队列空，等待数据。");
                            queue.wait();
                        }
                        queue.poll();
                        queue.notify();
                        System.out.println("从队列中取走队首元素，队列剩余" + queue.size() + "个元素。");
                    }
                }
            } catch (InterruptedException ie) {
                System.out.println("Consumer is interrupted.");
            }
        }
    }
}
