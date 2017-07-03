package com.ebay.eInsight.task;

import com.ebay.eInsight.common.TimeType;
import com.ebay.eInsight.task.common.HeartBeatRunnableTask;
import com.ebay.eInsight.task.executors.AbandonTaskExecutor;
import com.ebay.eInsight.task.executors.NormalTaskExecutor;
import com.ebay.eInsight.task.executors.RetryTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by weijlu on 2017/6/26.
 */
@Service
public class TemplateTaskSchedulerManager {

    @Autowired
    @Qualifier("heartBeatTaskScheduler")
    TaskScheduler scheduler;

    @PostConstruct
    public void scheduler() {
        new Thread() {
            @Override
            public void run() {
                NormalTaskExecutor.getInstance().execute();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                RetryTaskExecutor.getInstance().execute();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                AbandonTaskExecutor.getInstance().execute();
            }
        }.start();

        //heart beat start
        scheduler.schedule(new HeartBeatRunnableTask(TimeType.MINUTELY), new CronTrigger("5 * * * * *"));
    }
}
