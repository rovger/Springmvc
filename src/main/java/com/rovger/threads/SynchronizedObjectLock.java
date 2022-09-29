package com.rovger.threads;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 09:22
 */
public class SynchronizedObjectLock implements Runnable {
    static SynchronizedObjectLock instance = new SynchronizedObjectLock();

    @Override
    public void run() {
        synchronized(this) {
            try {
                System.out.println(Thread.currentThread().getName() + "正在持有锁...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "释放锁！");
        }
        synchronized(this) {
            try {
                System.out.println(Thread.currentThread().getName() + "正在持有锁...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "释放锁！");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
