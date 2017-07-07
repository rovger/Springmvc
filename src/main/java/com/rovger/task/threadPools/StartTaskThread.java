package com.rovger.task.threadPools;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by weijlu on 2017/7/5.
 * Tip:
 * ThreadPoolTaskExecutor submit()和executor()方法区别：
 *  1. 接收的参数不一样，submit()-->Callable<>  execute()-->Runnable<>
 *  2. submit方法没有返回值，而execute方法有
 *  3. submit方便Exception处理，在外界沟通过捕获Future.get()抛出的异常
 */
public class StartTaskThread implements Runnable {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private int num;

    public StartTaskThread(ThreadPoolTaskExecutor executor, int num) {
        this.threadPoolTaskExecutor = executor;
        this.num = num;
    }

    public void run() {
        final List<Future<String>> futureList = new ArrayList<Future<String>>();
        final String task = "task@" + num;
//        for (int i=0; i<100; i++) {
//        final int index = i;
        System.out.println("创建任务并提交到线程池中："+ task);
        Future<String> future = threadPoolTaskExecutor.submit(new Callable<String>() {
            public String call() {
                //开始任务
                System.out.println("开始执行任务：" + task);
                return task +" --- result: OK";
            }
        });
        futureList.add(future);
//        }
        try {
            for (Future f : futureList) {
                System.out.println(f.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
