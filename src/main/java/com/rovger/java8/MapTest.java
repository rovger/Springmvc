package com.rovger.java8;

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

        System.out.println(String.format("before convert map: %s", gson.toJson(userInfoMap)));

        List<UserInfo> userInfos = userInfoMap.entrySet()
                .stream()
                .filter(e -> Boolean.TRUE.equals(e.getValue().getPaid()))
                .map(map -> map.getValue())
                .collect(Collectors.toList());

        System.out.println(String.format("after filter list: %s", gson.toJson(userInfos)));

        Map<Long, UserInfo> newUserInfoMap = userInfoMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> new UserInfo(e.getValue().getId()+1, e.getValue().getPaid())));
        System.out.println(String.format("after changed value map: %s", gson.toJson(newUserInfoMap)));
    }
}
