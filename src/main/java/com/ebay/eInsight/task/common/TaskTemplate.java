package com.ebay.eInsight.task.common;

import com.ebay.eInsight.common.TimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by weijlu on 2017/6/23.
 */
public abstract class TaskTemplate {
    
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private String taskName;
    private TimeType taskTimeType;
    private long taskoffset=0;
    private ValueMappingCallBack valueCallBack = new ValueMappingCallBack() {
        public String callback(List<String> countBy) {
            return null;
        }

        public List<String> reverseCallback(String callbackValue) {
            return null;
        }
    };

    public TaskTemplate(String taskName, TimeType taskTimeType) {
        this.taskName = taskName;
        this.taskTimeType = taskTimeType;
    }
    public TaskTemplate(String taskName, TimeType taskTimeType, long taskoffset) {
        this.taskName = taskName;
        this.taskTimeType = taskTimeType;
        this.taskoffset = taskoffset;
    }
    public TaskTemplate(String taskName, TimeType taskTimeType, ValueMappingCallBack valueCallBack) {
        this.taskName = taskName;
        this.taskTimeType = taskTimeType;
        this.valueCallBack = valueCallBack;
    }
    public TaskTemplate(String taskName, TimeType taskTimeType, ValueMappingCallBack valueCallBack, long taskoffset) {
        this.taskName = taskName;
        this.taskTimeType = taskTimeType;
        this.taskoffset = taskoffset;
        this.valueCallBack=valueCallBack;
    }

    public void run(Date startDate, Date endDate) {
        logger.info("task running...", taskName +"."+ taskTimeType.getName());
        try {
            if (startDate!=null && endDate!=null) {
                doCountTask(startDate, endDate);
            }
        } catch (Exception ex) {
            logger.error("task failure...", taskName +"."+ taskTimeType.getName());
        }
    }

    protected abstract void doCountTask(Date startDate, Date endDate);

    /**
     * getter & setter
     * @return
     */
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TimeType getTaskTimeType() {
        return taskTimeType;
    }

    public void setTaskTimeType(TimeType taskTimeType) {
        this.taskTimeType = taskTimeType;
    }

    public long getTaskoffset() {
        return taskoffset;
    }

    public void setTaskoffset(long taskoffset) {
        this.taskoffset = taskoffset;
    }

    public ValueMappingCallBack getValueCallBack() {
        return valueCallBack;
    }

    public void setValueCallBack(ValueMappingCallBack valueCallBack) {
        this.valueCallBack = valueCallBack;
    }
}
