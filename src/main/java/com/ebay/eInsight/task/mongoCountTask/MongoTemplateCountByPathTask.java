package com.ebay.eInsight.task.mongoCountTask;

import com.ebay.eInsight.common.TimeType;
import com.ebay.eInsight.task.common.ValueMappingCallBack;

import java.util.Map;

/**
 * Created by weijlu on 2017/6/23.
 */
public class MongoTemplateCountByPathTask extends MongoTemplateTask {

    private String[] countByPath;
    public MongoTemplateCountByPathTask(String taskName, TimeType taskTimeType, String... countByPath) {
        super(taskName, taskTimeType, getTemplateName());
        this.countByPath = countByPath;
    }

    public MongoTemplateCountByPathTask(String taskName, TimeType taskTimeType, long offset, String... countByPath) {
        super(taskName, taskTimeType, getTemplateName(), offset);
        this.countByPath = countByPath;
    }

    public MongoTemplateCountByPathTask(String taskName, TimeType taskTimeType, ValueMappingCallBack valueCallBack, long offset, String... countByPath) {
        super(taskName, taskTimeType, getTemplateName(), valueCallBack, offset);
        this.countByPath = countByPath;
    }

    public MongoTemplateCountByPathTask(String taskName, TimeType taskTimeType, ValueMappingCallBack valueCallBack, Map<String, Object> matchMap, long offset, String... countByPath) {
        super(taskName, taskTimeType, getTemplateName(), valueCallBack, matchMap, offset);
        this.countByPath = countByPath;
    }

    public static String getTemplateName() {
        return "CountByPath";
    }

    public String[] getCountByPath() {
        return countByPath;
    }
}
