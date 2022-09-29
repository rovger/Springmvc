package com.rovger.threads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: JVM安全点：
 *
 * 是HotSpot虚拟机为了避免安全点过多带来过重的负担，对循环还有一项优化措施，认为循环次数较少的话，执行时间应该也不会太长，
 * 所以使用int类型或范围更小的数据类型作为索引值的循环默认是不会被放置安全点的。这种循环被称为可数循环（Counted Loop），
 * 相对应地，使用long或者范围更大的数据类型作为索引值的循环就被称为不可数循环（Uncounted Loop），将会被放置安全点。
 *
 * 安全点位置：方法调用、循环跳出、异常跳转、执行native方法（如：Thread.sleep(0)等）
 *
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年09月05日 09:43
 */
public class SafePoint {

    static AtomicInteger counter = new AtomicInteger(0);

    /**
     * 验证sleep(0)的native方法执行 与 for long对safePoint作用
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        /*Runnable runnable = () -> {
            for (long i  = 0; i < 1000000000; i++) {
                counter.getAndAdd(1);
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕！");
        };*/

//        ------------------ 等价于 ------------------

        Runnable runnable = () -> {
            for (int i = 0; i < 1000000000; i++) {
                counter.getAndAdd(1);
                // prevent gc，（代码来源，rocketMq）每1000次迭代，执行一次native方法，相当于在循环体内插入safaPoint
                if (i % 1000 ==0) {
                    try {
                        Thread.sleep(0); // 触发操作系统立刻重新进行一次CPU竞争，插入一个safa point
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕！");
        };

        new Thread(runnable).start();
        new Thread(runnable).start();

        // 现象：主线程"counter：xxx" 1s后被打印，但程序没有结束，直到2个子线程执行完毕
        Thread.sleep(1000);
        System.out.println("counter: " + counter);
    }
}
