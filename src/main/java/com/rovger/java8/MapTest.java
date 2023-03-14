package com.rovger.java8;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年08月26日 16:30
 */
public class MapTest {

    private static Gson gson = new Gson();

    public static void main(String[] args) {

        Map<String, Long> lm = new HashMap<>();
        lm.put("duration:6:20221212", 0L);
        lm.put("duration:7:20221212", 0L);
        lm.put("duration:8:20221212", 0L);
        lm.put("duration:9:20221212", 0L);
        lm.put("duration:10:20221212", 0L);
        lm.put("duration:11:20221214", 0L);
        lm.put("duration:12:20221214", 0L);
        lm.put("duration:13:20221214", 0L);
        lm.put("duration:14:20221214", 0L);
        lm.put("duration:15:20221214", 0L);
        lm.put("duration:16:20221214", 0L);
        lm.put("duration:17:20221215", 0L);
        Optional<String> keyOptional = lm.keySet().stream()
                .sorted(Comparator.comparing(e -> Long.valueOf(String.valueOf(e).split(":")[1])).reversed())
                .findFirst();
        System.out.println("=====" + Long.valueOf(keyOptional.get().split(":")[1]));

        Map<Long, String> map = new HashMap<>();
        Long c = 1000L;
        map.put(c, "weijie");
        long b = 1000;
        System.out.println(map.containsKey(b));

        Long num = 1802879999000L;
        System.out.println(num.compareTo(System.currentTimeMillis()));

        System.out.println("PLAY_URL_START".startsWith("PLAY_"));


        Map<Long, UserInfo> userInfoMap = new HashMap<>();
        UserInfo userInfo_1 = new UserInfo(1L, true);
        UserInfo userInfo_2 = new UserInfo(2L, false);

        userInfoMap.put(1L, userInfo_1);
        userInfoMap.put(2L, userInfo_2);
//        userInfoMap.put(3L, null);

        Set<String> set = new HashSet<>();
        set.add("weijie:25:s");
        set.add("lily:26:s");
        set.add("eason:12:s");
        String max = set.stream()
                .sorted(Comparator.comparing(
                                e -> String.valueOf(e).split(":")[1])
                        .reversed()
                )
                .findFirst()
                .get();
        System.out.println("set sort:" + max);

        System.out.println(String.format("before convert map: %s", gson.toJson(userInfoMap)));

        List<UserInfo> userInfos = userInfoMap.entrySet()
                .stream()
                .filter(e -> e.getValue().getPaid())
                .map(e -> e.getValue())
                .collect(Collectors.toList());

        // list -> map
        Map<Long, UserInfo> newUserInfoMap2 = userInfos.stream()
                .collect(Collectors.toMap(
                        UserInfo::getId,
                        Function.identity(),
                        (v1, v2) -> v1));

        System.out.println(String.format("after filter list: %s", gson.toJson(userInfos)));

        Map<Long, List<Integer>> listMap = new HashMap<>();
        listMap.put(123L, Lists.newArrayList(1, 2));
        System.out.println("map values:" + listMap.values().contains(2));
//        System.out.println("npe test:" + listMap.get(456L).contains(10)); // 有空指针风险

        Map<Long, Long> a = new HashMap<>();
        a.put(100L, 1L);
        a.put(200L, 5L);
        List<Long> l = a.entrySet().stream()
                .filter(entry -> Lists.newArrayList(1, 2, 3, 4).contains(entry.getValue()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        System.out.println("l:" + l);

        Map<Long, UserInfo> newUserInfoMap = userInfoMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> new UserInfo(e.getValue().getId() + 1, e.getValue().getPaid()),
                        (v1, v2) -> v2));

        System.out.println(String.format("after changed value map: %s", gson.toJson(newUserInfoMap)));
    }
}
