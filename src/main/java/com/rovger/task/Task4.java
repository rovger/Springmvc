package com.rovger.task;

import com.rovger.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weijlu on 2018/7/26.
 *
 * 定时任务的另一种实现方式，与Task1,2,3激活job方式不同，不需要额外实现Runnable接口。但是都可以实现定时任务
 *
 */
@Component("task4")
@EnableScheduling
public class Task4 {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0 1 * * * *")
    public void testSchedule() {
        String now = sdf.format(new Date());

        logger.info("当前任务： " + this.getClass().getSimpleName() + "， 当前用户：" + Initializer.userName
                + "， 是否初始化成功：" + Initializer.isInitialized + "， 当前时间：" + now);
    }
}
