package com.ebay.eInsight.task.common;

import com.ebay.eInsight.common.Initializer;
import com.ebay.eInsight.common.TimeType;
import com.ebay.eInsight.task.executors.ExecutorJob;
import com.ebay.eInsight.task.executors.NormalTaskExecutor;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by weijlu on 2017/6/26.
 */
public class HeartBeatRunnableTask implements Runnable {

    private TimeType heartBeatTimeType;

    public HeartBeatRunnableTask(TimeType taskTimeType) {
        this.heartBeatTimeType = taskTimeType;
    }

    public void run() {
        Date now = new Date();
        now = DateUtils.addMinutes(now, Initializer.TASK_DEFAULT_TIME_OFFSET);

        Date startDate = null;
        Date endDate = null;

        if (heartBeatTimeType==TimeType.MINUTELY) {
            now = DateUtils.setMilliseconds(now, 0);
            now = DateUtils.setSeconds(now, 0);
            startDate = DateUtils.addMinutes(now, -1);
            endDate = now;
        } else if (heartBeatTimeType==TimeType.HOURLY) {
            now = DateUtils.setMilliseconds(now, 0);
            now = DateUtils.setSeconds(now, 0);
            now = DateUtils.setMinutes(now, 0);
            startDate = DateUtils.addHours(now, -1);
            endDate = now;
        } else if (heartBeatTimeType==TimeType.DAILY) {
            now = DateUtils.setMilliseconds(now, 0);
            now = DateUtils.setSeconds(now, 0);
            now = DateUtils.setMinutes(now, 0);
            now = DateUtils.setHours(now, 0);
            startDate = DateUtils.addHours(now, -24);
            endDate = now;
        } else if (heartBeatTimeType==TimeType.WEEKLY) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, 1);
            now = cal.getTime();
            now = DateUtils.setMilliseconds(now, 0);
            now = DateUtils.setSeconds(now, 0);
            now = DateUtils.setMinutes(now, 0);
            now = DateUtils.setHours(now, 0);
            startDate = DateUtils.addWeeks(now, -1);
            endDate = now;
        } else if (heartBeatTimeType==TimeType.MONTHLY) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            now = cal.getTime();
            now = DateUtils.setMilliseconds(now, 0);
            now = DateUtils.setSeconds(now, 0);
            now = DateUtils.setMinutes(now, 0);
            now = DateUtils.setHours(now, 0);
            startDate = DateUtils.addMonths(now, -1);
            endDate = now;
        } else {
        }

        //submit time base job
        for (TaskTemplate task : Initializer.getTasks()) {
            if (heartBeatTimeType==task.getTaskTimeType()) {
                NormalTaskExecutor.getInstance().submitJob(new ExecutorJob(task, startDate, endDate));
            }
        }
        for (TaskTemplate task : Initializer.getBackEndTasks()) {
            if (heartBeatTimeType==task.getTaskTimeType()) {
                NormalTaskExecutor.getInstance().submitJob(new ExecutorJob(task, startDate, endDate));
            }
        }
    }
}
