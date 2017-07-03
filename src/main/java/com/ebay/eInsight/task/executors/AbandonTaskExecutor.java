package com.ebay.eInsight.task.executors;

import org.apache.log4j.Logger;

/**
 * Created by weijlu on 2017/6/26.
 */
public class AbandonTaskExecutor extends TaskExecutor {
    private static Logger s_logger = Logger.getLogger(AbandonTaskExecutor.class);
    private static volatile AbandonTaskExecutor instance = null;
    private AbandonTaskExecutor(){
    }

    public static AbandonTaskExecutor getInstance() {
        if (instance==null) {
            synchronized (AbandonTaskExecutor.class) {
                if (instance==null) {
                    instance = new AbandonTaskExecutor();
                }
            }
        }
        return instance;
    }

    public void execute() {

    }
}
