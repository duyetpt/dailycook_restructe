/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dao;

import java.util.Date;
import java.util.Iterator;
import org.entity.ActivityLog;
import org.entity.ActivityLog.Count;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author duyetpt
 */
public class ActivityLogDAO extends AbstractDAO<ActivityLog> {

    private static final ActivityLogDAO instance = new ActivityLogDAO();

    private ActivityLogDAO() {
        datastore.ensureIndexes(ActivityLog.class);
    }

    public static ActivityLogDAO getInstance() {
        return instance;
    }

    public ActivityLog get(String userId, long time) throws DAOException {
        try {
            Query<ActivityLog> query = datastore.createQuery(ActivityLog.class);
            query.filter("user_id", userId).filter("time", time);
            return query.get();
        } catch (Exception ex) {
            logger.error("ActivityLogDAO -> statistics error", ex);
            throw new DAOException();
        }
    }

    private Iterator<Count> statistics(Date from, Date to) throws DAOException {
        try {
            AggregationPipeline aggregation = datastore.createAggregation(ActivityLog.class);
            aggregation.match(datastore.createQuery(ActivityLog.class).filter("time >= ", from.getTime()).filter("time <=", to.getTime()));
            Iterator<Count> result = aggregation.group("time", Group.grouping("count", new Accumulator("$sum", 1))).aggregate(Count.class);

            return result;
        } catch (Exception ex) {
            logger.error("ActivityLogDAO -> statistics error", ex);
            throw new DAOException();
        }

    }
}
