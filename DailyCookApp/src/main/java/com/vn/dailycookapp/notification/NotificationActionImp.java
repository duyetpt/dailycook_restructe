package com.vn.dailycookapp.notification;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationActionImp implements NotificationAction {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static final NotificationActionImp instance = new NotificationActionImp();

    private NotificationActionImp() {
    }

    public static NotificationActionImp getInstance() {
        return instance;
    }

    @Override
    public void addNotification(String recipeId, String recipeTitle, String from, String to, String notiType) {
        if (from.equals(to)) {
            return;
        }
        Notification noti = new Notification();
        noti.setFrom(from);
        noti.setTo(to);
        noti.setRecipeId(recipeId);
        noti.setRecipeTitle(recipeTitle);
        noti.setType(notiType);
        NotificationWorker.getInstance().pushNotification(noti);
    }

    @Override
    public Notification getNoti() {
        //TODO
        return null;
    }
}
