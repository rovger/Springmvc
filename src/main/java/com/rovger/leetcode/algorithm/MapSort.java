package com.rovger.leetcode.algorithm;

import java.util.*;

/**
 * @Description: Map<String ,   Integer>双列集合的排序方法:
 * 按key排序
 * 按value排序
 * @Author weijlu
 * @Date 2019/2/25 16:26
 */
public class MapSort {

    public static void main(String[] args) {
        Map m = new Hashtable();
        Map<String, Integer> map = new HashMap<>();
        map.put("weijlu", 26);
        map.put("yancheng", 4);
        map.put("lily", 27);

//        sortByKeyOrValue(map);
        Map<String, String> linkedMap = new LinkedHashMap<>();
        //实现LRU cache数据结构，将最近距离访问的元素丢到List底部
        Map<String, String> linkedMap_sort = new LinkedHashMap<>(16, 0.75f, true);
        linkedMap_sort.put("111", "111");
        linkedMap_sort.put("222", "222");
        linkedMap_sort.put("333", "333");
        linkedMap_sort.put("444", "444");
        loopLinkedHashMap(linkedMap_sort);
        linkedMap_sort.get("111");
        loopLinkedHashMap(linkedMap_sort);
    }

    private static void loopLinkedHashMap(Map<String, String> linkedMap_sort) {
        // 找出values中最大的value,从而可以找到key
        /*Collection<Integer> maxEle = linkedMap_sort.values();
        int max = Collections.max(maxEle);*/

        for (Map.Entry<String, String> entry : linkedMap_sort.entrySet()) {
            System.out.println(entry);
        }

        /*Set<Map.Entry<String, String>> set = linkedMap_sort.entrySet();
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }*/
        System.out.println("\n");
    }

    private static void sortByKeyOrValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> mapList = new ArrayList<>();
        mapList.addAll(map.entrySet());
        Collections.sort(mapList, (o1, o2) -> {
//                return o2.getValue() - o1.getValue();
            return o1.getKey().compareTo(o2.getKey());
        });
        for (Iterator<Map.Entry<String, Integer>> it = mapList.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
