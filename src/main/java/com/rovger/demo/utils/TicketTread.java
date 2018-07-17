package com.rovger.demo.utils;

public class TicketTread {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        Thread t1 = new Thread(ticket, "t1");
        Thread t2 = new Thread(ticket, "t2");
        Thread t3 = new Thread(ticket, "t3");
        //start
        t1.start();t2.start();t3.start();

        /*try {
            //main thread sleep for threads sync
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}

class Ticket implements Runnable {
    private int counter = 1000;
    @Override
    public void run() {
        while (true) {
            synchronized (Ticket.class) {
                if (counter > 0) {
                    System.out.println(Thread.currentThread().getName() +" ==> 剩余票数:"+ --counter);
                }
            }
        }
    }
}