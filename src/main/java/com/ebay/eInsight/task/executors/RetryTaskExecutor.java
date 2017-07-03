package com.ebay.eInsight.task.executors;

import org.apache.log4j.Logger;

/**
 * Created by weijlu on 2017/6/26.
 */
public class RetryTaskExecutor extends TaskExecutor {
    private static Logger s_Logger = Logger.getLogger(RetryTaskExecutor.class);
    private static volatile RetryTaskExecutor instance = null;

    private RetryTaskExecutor(){
    }

    public static RetryTaskExecutor getInstance() {
        if (instance==null) {
            synchronized (RetryTaskExecutor.class) {
                if (instance==null) {
                    instance = new RetryTaskExecutor();
                }
            }
        }
        return instance;
    }
    public void execute() {

    }
}
