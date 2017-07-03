package com.ebay.eInsight.task.executors;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by weijlu on 2017/6/26.
 */
public abstract class TaskExecutor {
    private static Logger s_looger = Logger.getLogger(TaskExecutor.class);
    protected LinkedBlockingDeque<ExecutorJob> taskQueue;
    protected int MAX_TASKS = Integer.MAX_VALUE;
    protected int taskSubmited = 0;

    public abstract void execute();

    protected TaskExecutor() {
        taskQueue = new LinkedBlockingDeque<ExecutorJob>(MAX_TASKS);
    }


    public void submitJob(ExecutorJob job) {
        try {
            this.taskQueue.put(job);
            taskSubmited = taskSubmited + 1;
        } catch (Exception ex) {
            s_looger.error("Submit Job Error: ", ex);
        }
    }

    public List<ExecutorJob> getRestTasks() {
        List<ExecutorJob> restTasks = new ArrayList<ExecutorJob>();
        for (ExecutorJob job : taskQueue) {
            restTasks.add(job);
        }
        return restTasks;
    }

}
