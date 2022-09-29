package com.rovger.threads.buy_ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Description: TODO
 * @Author weijlu
 * @Date 2018/12/26 15:18
 */
public class TicketProcess {

    //声明一个阻塞队列
    Queue queue;
    int q_size = 100;

    //总票数
    List<Integer> tickets;

    public TicketProcess(List<Integer> tickets) {
        this.tickets = tickets;
        queue = new PriorityQueue(q_size);
    }

    public static void main(String[] args) {
        List<Integer> tickets = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            tickets.add(i);
        }
        TicketProcess process = new TicketProcess(tickets);
        process.process();
    }

    public void process() {
        //queue put
        Thread putThread = new Thread(new PutThread(), "Put_Thread");

        Thread take_1 = new Thread(new TakeThread(), "Thread_1");
        Thread take_2 = new Thread(new TakeThread(), "Thread_2");
        Thread take_3 = new Thread(new TakeThread(), "Thread_3");

        putThread.start();
        take_1.start();
        take_2.start();
        take_3.start();

        //kill thread
        Thread killThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (queue.size() != 0) {
                        System.out.println("sleeping...................................");
                        Thread.sleep(5000);
                    }
                    putThread.interrupt();
                    take_1.interrupt();
                    take_2.interrupt();
                    take_3.interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        killThread.setDaemon(true);//上面的线程被终止调后，守护线程就自动被GC掉
        killThread.start();

        try {
            killThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        killThread.interrupt();
        System.out.println("kill thread done!");
    }

    /**
     * 从queue里拿数据
     */
    class TakeThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (queue) {
                        while (queue.size() == 0) {
                            System.out.println(Thread.currentThread().getName() + " queue is empty, waiting...");
                            queue.wait();
                        }
                        System.out.println(Thread.currentThread().getName() + " -- Queue take " + queue.peek());
                        queue.poll();
                        queue.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " Queue take operation done!");
            }

        }
    }

    /**
     * 一个往queue里put数据的线程
     */
    class PutThread implements Runnable {
        @Override
        public void run() {
            try {
                for (Integer ticket : tickets) {
                    synchronized (queue) {
                        while (queue.size() == q_size) {
                            System.out.println("queue is full, waiting...");
                            queue.wait();
                        }
                        queue.notifyAll();
                        System.out.println("Put to queue:" + ticket);
                        queue.offer(ticket);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " Queue put operation done!");
            }
        }
    }

}
