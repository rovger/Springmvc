package com.ebay.eInsight.common.dao.impl;

import com.ebay.eInsight.common.Initializer;
import com.ebay.eInsight.common.dao.ConsoleDAOImpl;
import com.mongodb.DBCollection;
import org.apache.log4j.Logger;

/**
 * Created by weijlu on 2017/6/23.
 */
public class ConfigDAOImpl extends ConsoleDAOImpl {
    private static Logger s_logger = Logger.getLogger(ConfigDAOImpl.class);
    private final static String CONFIG_COLLECTIONNAME = Initializer.DOMAIN + "_Config";
    private static volatile ConfigDAOImpl daoImplRead = null;
    private static volatile ConfigDAOImpl daoImplWrite = null;
    private static DBCollection conn;

    private ConfigDAOImpl(String readWriteType) {
        super(CONFIG_COLLECTIONNAME, CONFIG_COLLECTIONNAME, readWriteType);
    }

    protected DBCollection getConnection() {
        if (conn==null) {
            synchronized (this) {
                if (conn==null) {
                    conn = this.getDBCollection();
                }
            }
        }
        return conn;
    }

    public static ConfigDAOImpl getReadInstance() {
        if (daoImplRead==null) {
            synchronized (ConfigDAOImpl.class) {
                if (daoImplRead==null) {
                    daoImplRead = new ConfigDAOImpl(Initializer.DS_NAME_read);
                }
            }
        }
        return daoImplRead;
    }

    public static ConfigDAOImpl getWriteInstance() {
        if (daoImplWrite==null) {
            synchronized (ConfigDAOImpl.class) {
                if (daoImplWrite==null) {
                    daoImplWrite = new ConfigDAOImpl(Initializer.DS_NAME_write);
                }
            }
        }
        return daoImplWrite;
    }
}
