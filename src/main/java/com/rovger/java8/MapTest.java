package com.rovger.java8;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<Long, UserInfo> userInfoMap = new HashMap<>();
        UserInfo userInfo_1 = new UserInfo(1L, true);
        UserInfo userInfo_2 = new UserInfo(2L, false);

        userInfoMap.put(1L, userInfo_1);
        userInfoMap.put(2L, userInfo_2);
//        userInfoMap.put(3L, null);

        System.out.println(String.format("before convert map: %s", gson.toJson(userInfoMap)));

        List<UserInfo> userInfos = userInfoMap.entrySet()
                .stream()
                .filter(e -> e.getValue().getPaid())
                .map(e -> e.getValue())
                .collect(Collectors.toList());

        System.out.println(String.format("after filter list: %s", gson.toJson(userInfos)));

        Map<Long, List<Integer>> listMap = new HashMap<>();
        listMap.put(123L, Lists.newArrayList(1, 2));
        System.out.println("map values:" + listMap.values().contains(2));
//        System.out.println("npe test:" + listMap.get(456L).contains(10)); // 有空指针风险

        Map<Long, Long> a = new HashMap<>();
        a.put(100L, 1L);
        a.put(200L, 5L);
        List<Long> l = a.entrySet().stream()
                .filter(entry -> Lists.newArrayList(1,2,3,4).contains(entry.getValue()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        System.out.println("l:" + l);

        Map<Long, UserInfo> newUserInfoMap = userInfoMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> new UserInfo(e.getValue().getId() + 1, e.getValue().getPaid())));
        System.out.println(String.format("after changed value map: %s", gson.toJson(newUserInfoMap)));
    }
}
