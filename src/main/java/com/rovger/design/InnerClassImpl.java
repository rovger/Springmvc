package com.rovger.design;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 设计一个匿名内部类，实现一种回调函数
 * @Author weijlu
 * @Date 2018/12/13 17:42
 */
public class InnerClassImpl {

    public interface Counter<T> {
        List<T> count(String name, int number);
    }

    public static <T> List<T> query(String name, int number, Counter<T> counter) {
        List<T> results = counter.count(name, number);
        return results;
    }

    public static void main(String[] args) {
        List<String> list = InnerClassImpl.query("weijlu", 10, new Counter<String>() {
            @Override
            public List<String> count(String name, int number) {
                List<String> list = new ArrayList<>();
                for ( ;number>=0; number--) {
                    list.add(name + " , 减数: " + number);
                }
                return list;
            }
        });
        for (int i = 0; i<list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
