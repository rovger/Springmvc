package com.ebay.eInsight.task.mongoCountTask;

import com.ebay.eInsight.common.TimeType;
import com.ebay.eInsight.task.common.ValueMappingCallBack;

import java.util.Map;

/**
 * Created by weijlu on 2017/6/26.
 */
public class MongoMapReduceTask extends MongoTemplateTask {

    private String mapScript;
    private String reduceSript;
    public MongoMapReduceTask(String taskName, TimeType taskTimeType, String mapScript, String reduceScript) {
        super(taskName, taskTimeType, getTemplateName());
        this.mapScript = mapScript;
        this.reduceSript = reduceScript;
    }

    public MongoMapReduceTask(String taskName, TimeType taskTimeType, String mapScript, String reduceScript, long offset) {
        super(taskName, taskTimeType, getTemplateName(), offset);
        this.mapScript = mapScript;
        this.reduceSript = reduceScript;
    }

    public MongoMapReduceTask(String taskName, TimeType taskTimeType, ValueMappingCallBack valueCallBack, String mapScript, String reduceScript, long offset) {
        super(taskName, taskTimeType, getTemplateName(),valueCallBack, offset);
        this.mapScript = mapScript;
        this.reduceSript = reduceScript;
    }

    public MongoMapReduceTask(String taskName, TimeType taskTimeType, Map<String, Object> matchMap, String mapScript, String reduceScript, long offset) {
        super(taskName, taskTimeType, getTemplateName(), matchMap, offset);
        this.mapScript = mapScript;
        this.reduceSript = reduceScript;
    }

    public MongoMapReduceTask(String taskName, TimeType taskTimeType, ValueMappingCallBack valueCallBack, Map<String, Object> matchMap, String mapScript, String reduceScript, long offset) {
        super(taskName, taskTimeType, getTemplateName(), valueCallBack, matchMap, offset);
        this.mapScript = mapScript;
        this.reduceSript = reduceScript;
    }

    public static String getTemplateName() {
        return "MapReduce";
    }

    public String getMapScript() {
        return mapScript;
    }

    public String getReduceSript() {
        return reduceSript;
    }
}
