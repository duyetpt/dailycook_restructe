package org.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractDAO<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Datastore datastore = ConnectionDAO.getDataStore();

    public void save(T t) throws DAOException {
        try {
            datastore.save(t);
        } catch (Exception ex) {
            logger.error("save error", ex);
            throw new DAOException();
        }
    }

    public T get(String id, Class<T> entityClass) throws DAOException {
        try {
            return datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id)).get();
        } catch (Exception ex) {
            logger.error("get error", ex);
            throw new DAOException();
        }
    }

    public void delete(String id, Class<T> entityClass) throws DAOException {
        try {
            Query<T> query = datastore.createQuery(entityClass).filter("_id", new ObjectId(id));
            this.datastore.delete(query);
        } catch (Exception ex) {
            logger.error("delete error", ex);
            throw new DAOException();
        }
    }

    protected boolean increaseForField(String id, int number, Class<T> entityClass, String field) throws DAOException {
        try {
            Query<T> query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
            UpdateOperations<T> updateO = datastore.createUpdateOperations(entityClass).inc(field, number);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    protected boolean pushToArray(String id, String arrayName, String value, Class<T> entityClass) throws DAOException {
        try {
            Query<T> query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
            UpdateOperations<T> pushOperation = datastore.createUpdateOperations(entityClass).add(arrayName, value, true);

            UpdateResults result = datastore.update(query, pushOperation);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    protected boolean pullToArray(String id, String arrayName, String value, Class<T> entityClass) throws DAOException {
        try {
            Query<T> query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
            UpdateOperations<T> pushOperation = datastore.createUpdateOperations(entityClass).removeAll(arrayName,
                    value);

            UpdateResults result = datastore.update(query, pushOperation);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

}
