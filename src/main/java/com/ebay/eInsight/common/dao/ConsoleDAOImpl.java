package com.ebay.eInsight.common.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoException;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;

/**
 * Created by weijlu on 2017/6/23.
 */
public abstract class ConsoleDAOImpl {
    private static Logger s_logger = Logger.getLogger(ConsoleDAOImpl.class);

    private String m_logicalTableName;
    private String m_physicalTableName;
    private String m_dsName;
    private static final int write_time_out = 5000;
    private static final long query_time_out = 30000L;
    private static final int writeconcern_acknowledged_w = 1;
    private static final AggregationOptions options = AggregationOptions.builder().maxTime(query_time_out, TimeUnit.MILLISECONDS).build();
    private static final ReadPreference secondaryPreferred = ReadPreference.secondaryPreferred();
    private static final WriteConcern defaultWriteConcern = new WriteConcern(writeconcern_acknowledged_w, write_time_out);

    public ConsoleDAOImpl(String logicalTableName, String physicalTableName, String readWriteType) {
        this.m_logicalTableName = logicalTableName;
        this.m_physicalTableName = physicalTableName;
        this.m_dsName = getDsNameNoPfx(readWriteType);
    }

    protected abstract DBCollection getConnection();

    //TODO: get DB connection.
    public DBCollection getDBCollection() {
        return null;
    }

    public List aggregateList(DBObject matchWrap, DBObject projectWrap, DBObject groupWrap) {
        List<DBObject> resultlist = null;
        try {
            DBCollection collection = this.getConnection();
            Cursor cursor = collection.aggregate(Arrays.asList(new DBObject[]{matchWrap, projectWrap, groupWrap}), options, secondaryPreferred);
            resultlist = new ArrayList<DBObject>();
            while (cursor.hasNext()) {
                DBObject item = cursor.next();
                resultlist.add(item);
            }
        }catch (MongoException e1) {
            s_logger.error("aggregate error: ", e1);
            throw new MongoException("DB_ISSUE", e1);
        }
        return resultlist;
    }

    public List mapReduce(DBObject matchWrap, String map, String reduce) {
        List<DBObject> resultlist = null;
        try {
            DBCollection collection = this.getConnection();
            MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce,
                    null, MapReduceCommand.OutputType.INLINE, matchWrap);
            cmd.setReadPreference(secondaryPreferred);
            cmd.setMaxTime(query_time_out, TimeUnit.MILLISECONDS);
            MapReduceOutput fileAudits = collection.mapReduce(cmd);
            Iterator<DBObject> resultIterator = fileAudits.results().iterator();
            resultlist = new ArrayList<DBObject>();
            while (resultIterator.hasNext()) {
                DBObject item = resultIterator.next();
                resultlist.add(item);
            }
        }catch (MongoException e1) {
            s_logger.error("mapReduce error: ", e1);
            throw new MongoException("DB_ISSUE", e1);
        }
        return resultlist;
    }

    public Long getdbCount() {
        try {
            return this.getConnection().count();
        }catch (MongoException e1) {
            s_logger.error("getdbCount error: ", e1);
            throw new MongoException("DB_ISSUE", e1);
        }
    }

    public List query(DBObject queryCondition, DBObject fields, int limit, String sortField) {
        return this.query(queryCondition, fields, limit, sortField, true);
    }

    public List query(DBObject queryCondition, DBObject fields, int limit, String sortField, boolean isDescOrder) {
        try {
            return fetchOnce(queryCondition, fields, limit, sortField, isDescOrder);
        }catch (MongoException e1) {
            try{
                return fetchOnce(queryCondition, fields, limit, sortField, isDescOrder);
            }catch (MongoException e2) {
                try{
                    return fetchOnce(queryCondition, fields, limit, sortField, isDescOrder);
                }catch (MongoException e3) {
                    s_logger.error("query error: ", e1);
                    throw new MongoException("DB_ISSUE", e1);
                }
            }
        }
    }

    public List query(DBObject queryCondition, DBObject fields, int limit) {
        try {
            return fetchOnce(queryCondition, fields, limit);
        }catch (MongoException e1) {
            try{
                return fetchOnce(queryCondition, fields, limit);
            }catch (MongoException e2) {
                try{
                    return fetchOnce(queryCondition, fields, limit);
                }catch (MongoException e3) {
                    s_logger.error("query error: ", e1);
                    throw new MongoException("DB_ISSUE", e1);
                }
            }
        }
    }

    private List fetchOnce(DBObject queryCondition, DBObject fields, int limit, String sortField, boolean isDescOrder) {
        DBCursor fileAudits = null;
        if (limit<=0 || limit >= 1000) {
            limit = 1000;
        }
        int orderId = isDescOrder ? -1 : 1;
        fileAudits = this.getConnection().find(queryCondition, fields).limit(limit).sort(new BasicDBObject(sortField, orderId))
                .setReadPreference(secondaryPreferred).maxTime(query_time_out, TimeUnit.MILLISECONDS);
        return  fileAudits.toArray();
    }

    private List fetchOnce(DBObject queryCondition, DBObject fields, int limit) {
        DBCursor fileAudits = null;
        if (limit<=0 || limit >= 1000) {
            limit = 1000;
        }
        fileAudits = this.getConnection().find(queryCondition, fields).limit(limit)
                .setReadPreference(secondaryPreferred).maxTime(query_time_out, TimeUnit.MILLISECONDS);
        return  fileAudits.toArray();
    }

    public List query(DBObject queryCondition, int limit, String sortField) {
        try {
            return fetchOnce(queryCondition, limit, sortField);
        }catch (MongoException e1) {
            try{
                return fetchOnce(queryCondition, limit, sortField);
            }catch (MongoException e2) {
                try{
                    return fetchOnce(queryCondition, limit, sortField);
                }catch (MongoException e3) {
                    s_logger.error("query error: ", e1);
                    throw new MongoException("DB_ISSUE", e1);
                }
            }
        }
    }

    public List query(DBObject queryCondition) {
        return query(queryCondition, 0);
    }

    public List query(DBObject queryCondition, int limit) {
        try {
            return fetchOnce(queryCondition, limit);
        }catch (MongoException e1) {
            try{
                return fetchOnce(queryCondition, limit);
            }catch (MongoException e2) {
                try{
                    return fetchOnce(queryCondition, limit);
                }catch (MongoException e3) {
                    s_logger.error("query error: ", e1);
                    throw new MongoException("DB_ISSUE", e1);
                }
            }
        }
    }

    private List fetchOnce(DBObject queryCondition, int limit) {
        DBCursor fileAudits = null;
        if (limit<=0 || limit >= 1000) {
            limit = 1000;
        }
        DBCollection collection = this.getConnection();
        fileAudits = collection.find(queryCondition).limit(limit)
                .setReadPreference(secondaryPreferred).maxTime(query_time_out, TimeUnit.MILLISECONDS);
        return  fileAudits.toArray();
    }

    private List fetchOnce(DBObject queryCondition, int limit, String sortField) {
        DBCursor fileAudits = null;
        if (limit<=0 || limit >= 1000) {
            limit = 1000;
        }
        DBCollection collection = this.getConnection();
        fileAudits = collection.find(queryCondition).limit(limit).sort(new BasicDBObject(sortField, -1))
                .setReadPreference(secondaryPreferred).maxTime(query_time_out, TimeUnit.MILLISECONDS);
        return  fileAudits.toArray();
    }

    public WriteResult updateOrInsert(DBObject query ) {
        try {
            DBCollection collection = this.getConnection();
            return collection.save(query, defaultWriteConcern);
        }catch (MongoException e1) {
            s_logger.error("query error: ", e1);
            throw new MongoException("DB_ISSUE", e1);
        }
    }

    public WriteResult save(DBObject obj ) {
        try {
            DBCollection collection = this.getConnection();
            //WriteConcern concern = new WriteConcern( "majority", 0, false, false );
            return collection.save(obj, defaultWriteConcern);
        }catch (MongoException e1) {
            s_logger.error("query error: ", e1);
            throw new MongoException("DB_ISSUE", e1);
        }
    }

    public WriteResult remove(DBObject obj ) {
        try {
            DBCollection collection = this.getConnection();
            return collection.remove(obj, defaultWriteConcern);
        }catch (MongoException e1) {
            s_logger.error("query error: ", e1);
            throw new MongoException("DB_ISSUE", e1);
        }
    }

    private WriteResult updateOnce(DBObject q, DBObject o, boolean upsert, boolean multi, WriteConcern concern) {
        DBCollection collection = this.getConnection();
        return collection.update(q, o, upsert, multi, concern);
    }

    public int update(DBObject q, DBObject o, boolean upsert, boolean multi, WriteConcern concern) {
        WriteResult res = null;
        try {
            res = this.updateOnce(q, o, upsert, multi, concern);
        }catch (MongoException e1) {
            try {
                res = this.updateOnce(q, o, upsert, multi, concern);
            }catch (MongoException e2) {
                try {
                    res = this.updateOnce(q, o, upsert, multi, concern);
                }catch (MongoException e3) {
                    s_logger.error("query error: ", e1);
                    throw new MongoException("UPDATE_DB_ISSUE", e3);
                }
            }
        }
        return res.getN();
    }

    public int updateMajority(DBObject q, DBObject o, boolean upsert) {
        return this.update(q, o, upsert, false, defaultWriteConcern);
    }

    public static String getDsNameNoPfx(String dsName) {
        if(dsName != null && dsName.length() != 0) {
            if(dsName.startsWith("CORE_") || dsName.startsWith("INTL_")) {
                dsName = dsName.substring("CORE_".length());
            }

            if(dsName.startsWith("_")) {
                dsName = dsName.substring("_".length());
            }

            return dsName;
        } else {
            return dsName;
        }
    }
}
