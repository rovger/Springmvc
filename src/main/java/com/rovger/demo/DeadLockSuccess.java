package com.rovger.demo;

public class DeadLockSuccess {
    Object obj1 = new Object();
    Object obj2 = new Object();

    public static void main(String[] args) {
        new DeadLockSuccess().deadLock();
    }

    private void deadLock() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj1) {
                    try {
                        System.out.println(Thread.currentThread().getName()+ " get obj1 ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName()+" after sleep 500ms!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" need obj2! Just waiting!");
                    synchronized (obj2) {
                        System.out.println(Thread.currentThread().getName()+" get obj2 ing!");
                    }
                }
            }
        }, "thread1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj2) {
                    try {
                        System.out.println(Thread.currentThread().getName()+" get obj2 ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName()+" after sleep 500ms!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" need obj1! Just waiting!");
                    synchronized (obj1) {
                        System.out.println(Thread.currentThread().getName()+" get obj1 ing!");
                    }
                }
            }
        }, "thread2").start();
    }
}
