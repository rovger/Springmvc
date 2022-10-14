package com.rovger.utils;

import org.springframework.core.NamedThreadLocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by weijlu on 2017/3/20.
 *
 * 5.【强制】 Simpledateformat是线程不安全的类,一般不要定义为 static 4变量,如果定义为 static,必须加锁,或者使用 Dateutils工具类 正例:注意线程安全,使用 Dateutils。
 * 说明:如果是JDK8的应用,可以使用 Instant代替Date, Local Datetime代替 Calendar,
 * Datettmeformatter代替 Simpledate Format,官方给出的解释: simple beautiful strong immutable thread-safe。
 *
 */
public class DateFormatDemo {

    /**
     * 方案一
     */
    public static final ThreadLocal<DateFormat> dateFormatTL = new NamedThreadLocal<DateFormat>("DateTimeFormatter") {
        @Override
        public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static void removeTL() {
        dateFormatTL.remove();
    }

    /**
     * 方案二
     */
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String parseMilliSec(long milliSec) {
        Date date = new Date(milliSec);
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return dateTime.format(DATETIME_FORMATTER);
    }
}
