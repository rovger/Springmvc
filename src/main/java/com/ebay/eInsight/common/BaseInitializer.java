package com.ebay.eInsight.common;

import com.ebay.eInsight.common.dao.impl.ConfigDAOImpl;

/**
 * Created by weijlu on 2017/6/23.
 */
public class BaseInitializer {
    //DataSource Name setting
    public static final String DS_NAME_write = "pmtbridgeaudit_writehost";
    public static final String DS_NAME_read = "pmtbridgeaudit_readhost";
    public static final int TASK_DEFAULT_TIME_OFFSET = 0;

    public void init() {
        System.out.println("===================start initilizing===============");

        ConfigDAOImpl.getReadInstance();
        ConfigDAOImpl.getWriteInstance();

        System.out.println("===================finish initilizing===============");
    }

}
