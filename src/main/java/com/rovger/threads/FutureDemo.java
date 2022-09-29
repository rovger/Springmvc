package com.rovger.threads;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.*;

/**
 * @Description: 异步线程Future 2种实现：线程池Executors、CompletableFuture(更优)
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年08月30日 15:12
 */
public class FutureDemo {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
//        executor();
//        completableFuture_thenCombine();
//        completableFuture_thenCompose();
        completableFuture_thenAccept();
    }

    /**
     * CompletableFuture：
     * CompletableFuture弥补了Future模式的缺点。在异步的任务完成后，需要用其结果继续操作时，无需等待。
     * 可以直接通过thenCombine、thenCompose、thenAccept、thenApply 等方式将前面异步处理的结果交给另外一个异步事件处理线程来处理。
     */
    private static void completableFuture_thenCombine() {
        System.out.println("开始计时：" + LocalTime.now());
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "weijie";
        });

        CompletableFuture<String> f2 = f1.thenCombine(
                CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return " hao!";
                }).thenApply(s -> s + " halo啊"),
                (s1, s2) -> s1 + s2
        );
        System.out.println("waiting combine task complete...");

        System.out.println("combine task done! rst:" + f2.join());

        System.out.println("结束计时：" + LocalTime.now());
    }

    /**
     * thenCompose：thenCompose可以用于组合多个CompletableFuture，将前一个结果作为下一个计算的参数，它们之间存在着先后顺序。
     */
    private static void completableFuture_thenCompose() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 12;
        });

        CompletableFuture<Integer> f2 = f1.thenCompose(num -> CompletableFuture.supplyAsync(() -> num * 2));

        try {
//            f2.isDone();
            System.out.println("compose rst: " + f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * thenAccept: thenAccept()是只会对计算结果进行消费而不会返回任何结果的方法。
     */
    private static void completableFuture_thenAccept() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s+ " World")
                .thenApply(s -> s+ "\n This is CompletableFuture demo")
                .thenApply(String::toLowerCase)
                .thenAccept(System.out::print);
    }

    /**
     * Callable和Future配合使用：通常
     */
    private static void executor() {
        Future<String> future = executor.submit(() -> {
            System.out.println();
            Thread.sleep(10000);
            return "return task";
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("do something...");

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
