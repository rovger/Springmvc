package com.ebay.eInsight.task.executors;

import org.apache.log4j.Logger;

/**
 * Created by weijlu on 2017/6/26.
 */
public class NormalTaskExecutor extends TaskExecutor {
    private static Logger s_logger = Logger.getLogger(NormalTaskExecutor.class);
    private static volatile NormalTaskExecutor instance = null;

    private NormalTaskExecutor(){
    }

    public static NormalTaskExecutor getInstance() {
        if (instance==null) {
            synchronized(NormalTaskExecutor.class) {
                if (instance==null) {
                    instance = new NormalTaskExecutor();
                }
            }
        }
        return instance;
    }

    public void execute() {

    }
}
