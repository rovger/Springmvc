package com.rovger.java8;

import com.google.common.collect.Lists;
import com.rovger.entity.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Java8重要特性之Lambda表达式:
 * Lambda表达式可以理解为一种可传递的匿名函数：它没有名称，但有参数列表、函数主体、返回类型、可能还有一个可以抛出的异常列表
 */
public class LambdaExp {

    public static void main(String[] args) {
        boolean flag = true;
        UserInfo u_1 = new UserInfo(123L, true);
        UserInfo u_2 = new UserInfo(124L, false);
        UserInfo u_3 = new UserInfo(125L, false);
        UserInfo u_4 = new UserInfo(126L, true);
        List<UserInfo> userInfos = Lists.newArrayList(u_1, u_2, u_3, u_4);
        List<Long> userIds = userInfos.stream()
                .filter(u -> flag ? false : !u.getPaid())
                .map(u -> u.getId())
                .collect(Collectors.toList());
        System.out.println("filtered new ids: " + userIds);

        /**
         * Java8对排序的改变
         */
        List<String> names = Arrays.asList("weijlu", "luweijie", "rovger", "anna", "peter");
        //Java7及以前的List排序实现是下面这种
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("Java7按字母顺序：" + names.toString());

        //Java8排序写法如下
        Collections.sort(names, (String o1, String o2) -> o2.compareTo(o1));
        System.out.println("Java8按字母倒序：" + names.toString());


        /**
         * Stream接口
         */
        names.stream()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);

        //利用stream来处理List
        names.stream().forEach(s -> System.out.println(s));

        //遍历Map
        Map<String, Integer> items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);
        items.put("F", 60);
        items.forEach((k, v) -> {
            System.out.println("k: " + k + ", v: " + v);
            if ("E".equals(k)) {
                System.out.println("Hello E");
            }
        });


        /**
         * Java8对线程coding的改变
         */
        //Java7及以前线程写法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8");
            }
        }).start();

        //Java8的coding风格
        new Thread(() -> System.out.println("In Java8")).start();


        /**
         * Lambda表达式的
         * map：允许改变对象，将一下list中所有元素改变他们的数值，通过map方法可以将指定计算表达式应用到所有stream中所有元素
         * reduce：将集合中所有元素集合到一起，reduce有类似于SQL语句中的sum()，avg()，count()
         */
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        costBeforeTax.stream()
                .map((cost) -> cost + cost * .12)
                .forEach(System.out::println);

        double total = costBeforeTax.stream()
                .reduce((sum, cost) -> sum + cost)
                .get();
        System.out.println("Total:" + total);
    }
}
