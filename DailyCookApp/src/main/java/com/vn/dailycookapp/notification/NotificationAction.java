package com.vn.dailycookapp.notification;

import org.entity.Notification;

public interface NotificationAction {
	/**
	 * Add new notification
	 * 
	 * @param recipeId
	 * @param from
	 * @param to
	 * @param notiType
	 *            : Define in Notification entity
	 */
	public void addNotification(String recipeId, String recipeTitle, String from, String to, String notiType);
	
	/**
	 * Get noti from queue to process
	 * 
	 * @return
	 */
	public Notification getNoti();
}
