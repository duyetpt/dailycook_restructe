/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dao;

import java.util.List;
import org.entity.DeviceToken;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author duyetpt
 */
public class DeviceTokenDAO extends AbstractDAO<DeviceToken> {
    
    private DeviceTokenDAO() {
        datastore.ensureIndexes(DeviceToken.class);
    }
    
    private static DeviceTokenDAO instance = new DeviceTokenDAO();

    public static DeviceTokenDAO getInstance() {
        return instance;
    }

    public List<DeviceToken> getUserDevices(String userId) throws DAOException {
        try {
            Query<DeviceToken> query = datastore.createQuery(DeviceToken.class);
            query.filter("user_id", userId);

            return query.asList();
        } catch (Exception ex) {
            logger.error("get user devices error", ex);
            throw new DAOException();
        }
    }

    public void removeUserDevices(String userId) throws DAOException {
        try {
            Query<DeviceToken> query = datastore.createQuery(DeviceToken.class);
            query.filter("user_id", userId);

            datastore.delete(query);
        } catch (Exception ex) {
            logger.error("get user devices error", ex);
            throw new DAOException();
        }
    }
    
    public DeviceToken getDevice(String token) throws DAOException{
        try {
            Query<DeviceToken> query = datastore.createQuery(DeviceToken.class);
            query.filter("device_token", token);
            
            return query.get();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    
}
