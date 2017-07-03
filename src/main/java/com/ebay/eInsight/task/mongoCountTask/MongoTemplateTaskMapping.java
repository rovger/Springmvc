package com.ebay.eInsight.task.mongoCountTask;

import com.ebay.eInsight.common.Initializer;
import com.ebay.eInsight.task.common.TaskTemplate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weijlu on 2017/6/23.
 */
public class MongoTemplateTaskMapping {
    private static Logger s_logger = Logger.getLogger(MongoTemplateTaskMapping.class);
    private static Map<String, String[]> taskName2queryCondition = new HashMap<String, String[]>();
    private static List<String> taskNames = new ArrayList<String>();

    public static void init() {
        try {
            for (TaskTemplate task : Initializer.getTasks()) {
                if (!taskNames.contains(task.getTaskName())) {
                    taskNames.add(task.getTaskName());
                }
                if (task instanceof MongoTemplateCountByPathTask) {
                    MongoTemplateCountByPathTask countByPathTask = (MongoTemplateCountByPathTask) task;
                    String[] countByPath = countByPathTask.getCountByPath();
                    if (countByPath != null && countByPath.length > 0) {
                        taskName2queryCondition.put(task.getTaskName(), countByPath);
                    }
                } else {
                    taskName2queryCondition.put(task.getTaskName(), null);
                }
            }
        } catch (Exception ex) {
            s_logger.error("Mongo Template Task Mapping Error: ", ex);
        }
    }

    public static String[] getCountByPath(String taskName) {
        return taskName2queryCondition.get(taskName);
    }

    public static List<String> getAllTaskNames() {
        return taskNames;
    }
}
