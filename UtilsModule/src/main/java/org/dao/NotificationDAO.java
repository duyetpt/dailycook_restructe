package org.dao;

import java.util.List;

import org.entity.Notification;

public class NotificationDAO extends AbstractDAO<Notification> {
	private static final NotificationDAO	instance	= new NotificationDAO();
	
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
}
