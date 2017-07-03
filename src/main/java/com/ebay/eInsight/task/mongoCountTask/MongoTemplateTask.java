package com.ebay.eInsight.task.mongoCountTask;

import com.ebay.eInsight.common.TimeType;
import com.ebay.eInsight.task.common.TaskTemplate;
import com.ebay.eInsight.task.common.ValueMappingCallBack;

import java.util.Date;
import java.util.Map;

/**
 * Created by weijlu on 2017/6/23.
 */
public class MongoTemplateTask extends TaskTemplate {

    private String templateName;
    private Map<String, Object> matchMap;

    public MongoTemplateTask(String taskName, TimeType taskTimeType, String templateName) {
        super(taskName, taskTimeType);
        this.templateName = templateName;
    }

    public MongoTemplateTask(String taskName, TimeType taskTimeType, String templateName, long offset) {
        super(taskName, taskTimeType, offset);
        this.templateName = templateName;
    }

    public MongoTemplateTask(String taskName, TimeType taskTimeType, String templateName, ValueMappingCallBack valueCallBack, long offset) {
        super(taskName, taskTimeType, valueCallBack, offset);
        this.templateName = templateName;
    }

    public MongoTemplateTask(String taskName, TimeType taskTimeType, String templateName, Map<String, Object> matchMap, long offset) {
        super(taskName, taskTimeType, offset);
        this.templateName = templateName;
        this.matchMap = matchMap;
    }

    public MongoTemplateTask(String taskName, TimeType taskTimeType, String templateName, ValueMappingCallBack valueCallBack, Map<String, Object> matchMap, long offset) {
        super(taskName, taskTimeType, valueCallBack, offset);
        this.templateName = templateName;
        this.matchMap = matchMap;
    }

    protected void doCountTask(Date startDate, Date endDate) {

    }
}
