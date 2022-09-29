package com.rovger.threads.buy_ticket;

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
        Thread consumer = new Thread(new Consumer());
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

        System.out.println("当前queue包含元素数量_1：" + queue.size());

        //使用Callable和Future来创建线程和接收线程参数，这里多此一举，只为了练习创建线程的第3种方法
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
            System.out.println("当前queue包含元素数量_2：" + queueSize);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        future.cancel(true);
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {//采用isInterrupted()整个try catch不会抛出异常
                    /**
                     * 为什么wait()、notify()、notifyAll()需要在synchronized代码块中执行？--> 用于多线程间通信
                     * 1. Java 会抛出 IllegalMonitorStateException，因为synchronized通过共享变量创造多线程间"互斥"环境，否则代码块任意线程都可以在未获得锁的情况下，执行代码块。
                     * 2. Java 中 wait 和 notify 方法之间的任何潜在竞争条件，否则会出现虚假唤醒问题
                     *
                     * // 生产者伪代码
                     * Producer:
                     * 	count ++;
                     * 	notify();
                     *
                     * // 消费者伪代码
                     * Consumer:
                     * 	while(count <= 0){
                     *         wait();
                     *         count --;
                     * }
                     * 这段代码在多线程环境下可能出现一个线程还没来得及 调用 wait方法时 就发生上下文切换从而导致 另外一个线程调用notify() 或者是 notifyAll() 方法时出现唤醒失败的情况，这种就是所谓的 无效唤醒
                     * 简单来说，当一个条件满足时，很多线程都可能被唤醒，但是只有其中部分是有用的唤醒，其它的唤醒都是多余的。比如说自动贩卖机卖货，如果本来没有货物，突然进了一件货物，这时所有的顾客都被通知了，但是只能一个人买，那么其他人都是无用的通知。
                     *
                     */
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

    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {//保证只要中断标记为true就终止线程。
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
