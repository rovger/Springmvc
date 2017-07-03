package com.ebay.eInsight.common;

import com.ebay.eInsight.resources.ConfigService;
import com.ebay.eInsight.task.common.TaskTemplate;
import com.ebay.eInsight.task.mongoCountTask.MongoTemplateTaskMapping;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weijlu on 2017/6/23.
 */
public class Initializer extends BaseInitializer {
    private static Logger s_logger = Logger.getLogger(Initializer.class);

    //Tips: Can jar dependency, likes: EasyInsightConstants.DOMAIN
    public static String DOMAIN = "";
    public static String EVENT_COLLECTIONNAME = "";

    //Tips: Java内存模型下，线程可以把变量保存在本地内存(比如机器的寄存器)，而不是直接在主存中进行读写。
    // 这样就可能造成一个线程在主存中修改了一个变量的值，而另外一个线程好继续使用它在寄存器中的变量值copy，造成数据不一致。
    public volatile boolean isInitialized = false;
    private volatile static EasyInsightConfigInterface config;
    private static ConfigService configSvc = new ConfigService();

    public void init() {
        try {
            this.loadConfig();
        } catch(Exception ex) {
            config = null;
            s_logger.error("init error: ", ex);
        }
    }

    private boolean loadConfig() {
        return loadConfig(configSvc.getTaskConfig());
    }

    private boolean loadConfig(String groovyString) {
        boolean isloadSuccess = false;
        try {
            config = configSvc.parseConfig(groovyString);
            if (config!=null) {
                EVENT_COLLECTIONNAME = config.getCoreConfig().get("EVENT_COLLECTIONNAME");
                if (!StringUtils.isEmpty(EVENT_COLLECTIONNAME)) {
                    if (!isInitialized) {
                        super.init();
                        isInitialized = true;
                    }
                    MongoTemplateTaskMapping.init();
                    isloadSuccess = true;
                }
            }
        } catch (Exception ex) {
            config = null;
            s_logger.error("Initializer loadConfig error: ", ex);
        }
        return isloadSuccess&&isInitialized;
    }

    public static Map<String, String> getCoreConfigs() {
        Map<String, String> coreConfigs = new HashMap<String, String>();
        if (config!=null && config.getCoreConfig()!=null && config.getCoreConfig().size()>0) {
            coreConfigs.putAll(config.getCoreConfig());
        }
        return coreConfigs;
    }

    public static List<TaskTemplate> getTasks() {
        List<TaskTemplate> tasks = new ArrayList<TaskTemplate>();
        if (config!=null && config.getTasks()!=null && config.getTasks().size()>0) {
            tasks.addAll(config.getTasks());
        }
        return tasks;
    }

    public static List<TaskTemplate> getBackEndTasks() {
        List<TaskTemplate> backEndTasks = new ArrayList<TaskTemplate>();
        if (config!=null && config.getBackEndTasks()!=null && config.getBackEndTasks().size()>0) {
            backEndTasks.addAll(config.getBackEndTasks());
        }
        return backEndTasks;
    }
}
