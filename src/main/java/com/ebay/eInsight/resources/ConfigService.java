package com.ebay.eInsight.resources;

import com.ebay.eInsight.common.EasyInsightConfigInterface;
import com.ebay.eInsight.common.Initializer;
import com.ebay.eInsight.common.dao.impl.ConfigDAOImpl;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import groovy.lang.GroovyClassLoader;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by weijlu on 2017/6/23.
 */
@Service
@Scope("request")
@Path("/config")
public class ConfigService {
    private static Logger s_logger = Logger.getLogger(ConfigService.class);
    @Context
    HttpServletRequest servletRequest;

    @GET
    @Path("/initializerconfig")
    public String getInitializerconfig() {
        try {
            String response = getTaskConfig();
            return response==null?"No Config Exists!":response;
        } catch (Exception ex) {
            s_logger.error("Get initializer config failure: ", ex);
            return "";
        }
    }

    public String getTaskConfig() {
        DBObject condition = new BasicDBObject();
        condition.put("param_name", "InitializerConfig");
        try {
            List<DBObject> configs = ConfigDAOImpl.getReadInstance().getDBCollection().find(condition)
                    .sort(new BasicDBObject("CreationDate", -1)).limit(1).toArray();
            if (configs != null && configs.size() > 0) {
                condition = configs.get(0);
            }
        } catch (Exception ex) {
            s_logger.error("Get Task Configs Error: ", ex);
            return new Gson().toJson(new BasicDBObject("error", ex.getMessage()));
        }
        return condition.get("param_value").toString();
    }

    public EasyInsightConfigInterface parseConfig(String groovyString) throws Exception {
        EasyInsightConfigInterface config = null;
        if (StringUtils.isEmpty(groovyString)) return null;
        ClassLoader classLoader = Initializer.class.getClassLoader();
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(classLoader);
        Class groovyClass = groovyClassLoader.parseClass(groovyString);
        config = (EasyInsightConfigInterface) groovyClass.newInstance();
        return config;
    }
}
