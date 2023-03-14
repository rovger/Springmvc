package com.rovger.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Description: Java8已经被广泛使用，Calendar处理日期时间有着性能差等问题:
 * 5.【强制】 Simpledateformat是线程不安全的类,一般不要定义为 static 4变量,如果定义为 static,必须加锁,或者使用 Dateutils工具类 正例:注意线程安全,使用 Dateutils。
 * * 说明:如果是JDK8的应用,可以使用 Instant代替Date, LocalDatetime代替 Calendar,
 * * Datettmeformatter代替 Simpledate Format,官方给出的解释: simple beautiful strong immutable thread-safe。
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年02月24日 12:51
 */
public class LocalDateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("------每分钟一个数值:" + System.currentTimeMillis() / 1000L / 60L);
        System.out.println("------每小时一个数值:" + System.currentTimeMillis() / 1000L / 3600L);

        /**
         * LocalDate
         */
        LocalDate today = LocalDate.now();
        LocalDate before3M = today.minusMonths(3);
        int year = today.getYear();
        int month = today.getMonthValue();
        int d = today.getDayOfMonth();

        LocalDate day = LocalDate.of(2021, 2, 20);
        System.out.println(today.isAfter(day));
        System.out.println(today.equals(day));

        /**
         * 时间格式化
         */
        String dayAfterTomorrow = "20220817";
//        LocalDate formatted = LocalDate.parse(dayAfterTomorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("TODAY formatted: " + today.format(FORMATTER));

        LocalDateTime day_f = LocalDateTime.now();
        DateTimeFormatter dtf_1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dtf_formatted = day_f.format(dtf_1);
        System.out.println("day dtf_1: " + dtf_formatted);

        LocalDate ld = LocalDate.parse(dtf_formatted, dtf_1);
        System.out.println("日期类型: " + ld);

        /**
         * 计算2个日期年数、天数、月数差，Period类
         */
        Period betweens = Period.between(day, today);
        System.out.println("between years:" + betweens.getYears() + ", between months:" + betweens.getMonths() + ", between days:" + betweens.getDays());

        LocalDate previousWeek = today.minus(1, ChronoUnit.WEEKS);
        System.out.println(previousWeek);
        System.out.println(today.minusWeeks(1));

        // 闰年
        System.out.println(today.isLeapYear());

        long seconds = System.currentTimeMillis() / 1000L;
        int timeScale_1 = 60, timeScale_2 = 3600;

        System.out.println(seconds / timeScale_1);
        System.out.println(seconds / timeScale_2);


        /**
         * LocalTime
         */
        LocalTime time = LocalTime.now();
        System.out.println("获取当前时间(不含年月日):" + time);

        LocalTime newTime = time.plusHours(3);
        System.out.println("3小时后时间(不含年月日):" + time);

        /**
         * 判断当前月份和当月天数
         */
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());

        YearMonth creditCardExpired = YearMonth.of(2022, Month.JULY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpired);

        /**
         * Instant
         */
        Instant timestamp = Instant.now();
        System.out.println("What is value if this instant:" + timestamp.getEpochSecond() + ", System.currentTimeMillis:" + System.currentTimeMillis());
        Date date = Date.from(timestamp);
        Instant instant = date.toInstant();


        long l = LocalDateTime.now().minusMonths(3).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(l);
    }

}
