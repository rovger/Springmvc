package com.ebay.eInsight.task.executors;

import com.ebay.eInsight.task.common.TaskTemplate;

import java.util.Date;

/**
 * Created by weijlu on 2017/6/26.
 */
public class ExecutorJob {
    private TaskTemplate task;
    private Date startDate;
    private Date endDate;
    private String executeStatus;
    private int executeCount = 0;

    public ExecutorJob(TaskTemplate task, Date startDate, Date endDate) {
        this.task = task;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ExecutorJob [task="+ task +", startDate="+ startDate +", endDate="+ endDate
                +", executeStatus="+ executeStatus +", executeCount="+ executeCount +"]";
    }
}
