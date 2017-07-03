package com.ebay.eInsight.common;

import com.ebay.eInsight.task.common.TaskTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by weijlu on 2017/6/23.
 */
public interface EasyInsightConfigInterface {

    public Map<String, String> getCoreConfig();
    public List<TaskTemplate> getTasks();
    public List<TaskTemplate> getBackEndTasks();

}
