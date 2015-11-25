package com.vn.dailycookapp.notification;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationActionImp implements NotificationAction {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static final NotificationActionImp instance = new NotificationActionImp();

    private final Queue<Notification> queue;

    private NotificationActionImp() {
        queue = new ConcurrentLinkedDeque<Notification>();
    }

    public static NotificationActionImp getInstance() {
        return instance;
    }

    @Override
    public void addNotification(String recipeId, String recipeTitle, String from, String to, String notiType) {
        if (from.equals(to)) {
            return;
        }

        synchronized (queue) {
            Notification noti = new Notification();
            noti.setFrom(from);
            noti.setTo(to);
            noti.setRecipeId(recipeId);
            noti.setRecipeTitle(recipeTitle);
            noti.setType(notiType);
            queue.add(noti);
            queue.notify();
        }
    }

    @Override
    public Notification getNoti() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            return queue.poll();
        }
    }

}
