package com.rovger.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weijlu on 2017/5/26.
 * 适合ERROR_TYPE("ERROR_CODE")这种表达方式
 * TYPE_1("9001"),
 * TYPE_2(9002),
 * ...
 */
public enum TimeType {

    MINUTELY("MINUTE"),
    HOURLY("HOUR"),
    DAILY("DAY"),
    WEEKLY("WEEK"),
    MONTHLY("MONTH");
    private String type;

    TimeType(String type) {
        this.type = type;
    }

    /**
     * name：表示括弧里的说明
     * values：表示MINUTELY, HOURLY...
     *
     * @param name
     * @return
     */
    private static Map<String, TimeType> typeMap = new HashMap<String, TimeType>();

    public static TimeType getByName(String name) {
        if (!typeMap.isEmpty()) return typeMap.get(name);
        for (TimeType unit : TimeType.values()) {
            typeMap.put(name, unit);
        }
        return typeMap.get(name);
    }

}
