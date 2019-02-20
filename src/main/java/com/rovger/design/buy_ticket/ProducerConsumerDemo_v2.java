package com.rovger.design.buy_ticket;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 生产者与消费者，在多线程下的实现
 * Condition/ReentrantLock的使用
 * @Author weijlu
 * @Date 2019/2/19 15:00
 */
public class ProducerConsumerDemo_v2 {

    static int maxQueueSize = 10;
    static PriorityQueue<Integer> queue = new PriorityQueue<>(10);

    static Lock lock = new ReentrantLock();
    static Condition notFull = lock.newCondition();
    static Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        Thread producer = new Producer();
        Thread consumer = new Consumer();
        producer.start();
        consumer.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //中断标志设置为true
        producer.interrupt();
        consumer.interrupt();

    }

    static class Producer extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {//保证只要中断标记为true就终止线程。
                    lock.lock();
                    while (queue.size() == maxQueueSize) {
                        System.out.println("队列满，等待剩余空间。");
                        notFull.await();
                    }
                    queue.offer(1);
                    notEmpty.signal();
                    System.out.println("从队列中插入一个元素，队列剩余" + queue.size() + "个元素。");
                }
            } catch (InterruptedException ie) {
                System.out.println("Producer is interrupted.");
            } finally {
                lock.unlock();//手动unlock
            }
        }
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    lock.lock();
                    while (queue.size() == 0) {
                        System.out.println("队列空，等待数据。");
                        notEmpty.await();
                    }
                    queue.poll();
                    notFull.signal();
                    System.out.println("从队列中取走队首元素，队列剩余" + queue.size() + "个元素。");
                }
            } catch (InterruptedException ie) {
                System.out.println("Consumer is interrupted.");
            } finally {
                lock.unlock();
            }
        }
    }
}
