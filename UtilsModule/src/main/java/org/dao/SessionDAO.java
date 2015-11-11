/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dao;

import org.entity.Session;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author duyetpt
 */
public class SessionDAO extends AbstractDAO<Session> {

    private static final SessionDAO instance = new SessionDAO();

    private SessionDAO() {
        datastore.ensureIndexes();
    }

    public static SessionDAO getInstance() {
        return instance;
    }

    public Session getSession(String token) {
        Query<Session> query = datastore.createQuery(Session.class).filter("token", token);
        return query.get();
    }

    public void logout(String token) {
        Query<Session> query = datastore.createQuery(Session.class).filter("token", token);
        datastore.delete(query);
    }

    public void removeAllSession(String userId) {
        Query<Session> query = datastore.createQuery(Session.class).filter("userId", userId);
        datastore.delete(query);
    }

    public void updateSession(Session session) {
        Query<Session> query = datastore.createQuery(Session.class).filter("token", session.getToken());
        UpdateOperations<Session> update = datastore.createUpdateOperations(Session.class).set("timeToLife", session.getTimeToLife());

        datastore.update(query, update);
    }
}
