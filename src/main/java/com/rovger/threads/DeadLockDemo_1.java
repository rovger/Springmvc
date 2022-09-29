package com.rovger.threads;

/**
 * 局部内部类实现“死锁”案例，局部内部类只能供该类使用，其他外部类无法访问到
 * Created by weijlu on 2018/5/10.
 */
public class DeadLockDemo_1 {

    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Thread t1 = new Thread(new DeathLockRunner(obj1, obj2, true), "thread1");
        Thread t2 = new Thread(new DeathLockRunner(obj1, obj2, false), "thread2");
        t1.start();
        t2.start();
    }
}

class DeathLockRunner implements Runnable {
    boolean flag;
    Object obj1;
    Object obj2;

    public DeathLockRunner(Object obj1, Object obj2, boolean flag) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.flag = flag;
    }

    @Override
    public void run() {
        if (this.flag) {
            synchronized (obj1) {
                try {
                    System.out.println(Thread.currentThread().getName() + " get obj1 ing!");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName() + " after sleep 500ms!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " need obj2! Just waiting!");
                synchronized (obj2) {
                    System.out.println(Thread.currentThread().getName() + " get obj2 ing!");
                }
            }
        } else {
            synchronized (obj2) {
                try {
                    System.out.println(Thread.currentThread().getName() + " get obj2 ing!");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName() + " after sleep 500ms!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " need obj1! Just waiting!");
                synchronized (obj1) {
                    System.out.println(Thread.currentThread().getName() + " get obj1 ing!");
                }
            }
        }
    }
}