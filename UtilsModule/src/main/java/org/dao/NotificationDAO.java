package org.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.entity.Notification;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class NotificationDAO extends AbstractDAO<Notification> {

    private static final NotificationDAO instance = new NotificationDAO();

    private NotificationDAO() {
        datastore.ensureIndexes(Notification.class);
    }

    public static NotificationDAO getInstance() {
        return instance;
    }

    public void save(List<Notification> list) throws DAOException {
        try {
            datastore.save(list);
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    /**
     * Get list Notification sort by status and date
     *
     * @param userId
     * @param skip
     * @param take
     * @return
     * @throws DAOException
     */
    public List<Notification> list(String userId, int skip, int take)
            throws DAOException {
        try {
            Query<Notification> query = datastore
                    .createQuery(Notification.class);
            query.field("to").equal(userId).order("-status").order("-sentDate")
                    .offset(skip).limit(take);

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    /**
     * Update status of specify notification
     *
     * @param notiId
     * @throws DAOException
     */
    public void update(String notiId) throws DAOException {
        try {
            UpdateOperations<Notification> update = datastore
                    .createUpdateOperations(Notification.class);
            update.set("status", Notification.READED_STATUS);

            Query<Notification> query = datastore.createQuery(
                    Notification.class).filter("_id", new ObjectId(notiId));
            datastore.update(query, update);
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

//	/**
//	 * Update all notification of specify people to Readed
//	 * 
//	 * @param userId
//	 * @throws DAOException
//	 */
//	public void markReadAll(String userId) throws DAOException {
//		try {
//			UpdateOperations<Notification> update = datastore
//					.createUpdateOperations(Notification.class);
//			update.set("status", Notification.READED_STATUS);
//
//			Query<Notification> query = datastore
//					.createQuery(Notification.class)
//					.filter("status", Notification.UNREAD_STATUS)
//					.filter("to", userId);
//			datastore.update(query, update);
//		} catch (Exception ex) {
//                        logger.error("");
//			throw new DAOException();
//		}
//	}
    public void deleteNotificatonOfRecipe(String recipeId) throws DAOException {
        try {
            Query<Notification> query = datastore.createQuery(Notification.class);
            query.filter("recipe_id", recipeId);

            datastore.delete(query);
        } catch (Exception ex) {
            logger.error("delete notificaton errror", ex);
            throw new DAOException();
        }
    }

    public List<Notification> allNotificationOfRecipe(String recipeId) throws DAOException {
        try {
            Query<Notification> query = datastore.createQuery(Notification.class);
            query.filter("recipe_id", recipeId).filter("status", Notification.UNREAD_STATUS);
            
            return query.asList();
        } catch (Exception ex) {
            logger.error("delete notificaton errror", ex);
            throw new DAOException();
        }
    }
}
