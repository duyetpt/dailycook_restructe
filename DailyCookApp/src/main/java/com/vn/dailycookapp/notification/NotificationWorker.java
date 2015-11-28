package com.vn.dailycookapp.notification;

import java.util.ArrayList;
import java.util.List;

import org.dao.DAOException;
import org.dao.FollowingDAO;
import org.dao.NotificationDAO;
import org.dao.UserDAO;
import org.entity.Following;
import org.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.notification.appleservice.AppleNotificationManager;

public class NotificationWorker extends Thread {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void run() {
        while (true) {
            logger.info("Notificaion worker is running...");
            try {
                Notification noti = NotificationActionImp.getInstance().getNoti();
                // increate notification number
                CompactUserInfo toUser = UserCache.getInstance().get(noti.getTo());
                toUser.increaseNumberNotificaion();
                UserDAO.getInstance().increateNotificationNumber(toUser.getUserId());

                List<Notification> list = null;
                switch (noti.getType()) {
                    case Notification.NEW_COMMENT_TYPE:
                        list = notiNewComment(noti);
                        break;
                    case Notification.NEW_FAVORITE_TYPE:
                        list = notiFavorite(noti);
                        break;
                    case Notification.NEW_FOLLOWER_TYPE:
                        list = notiFollower(noti);
                        break;
                    case Notification.NEW_RECIPE_FROM_FOLLOWING_TYPE:
                        list = notiNewRecipe(noti);
                        break;
                    case Notification.REMOVE_RECIPE_TYPE:
                        list = notiRemoveRecipe(noti);
                        break;
                    case Notification.UNBAN_USER_TYPE:
                        // TODO
                        break;
                }

                // save to DB
                NotificationDAO.getInstance().save(list);
                // apple notificaton
                AppleNotificationManager.getInstance().pushs(list);
            } catch (Exception e) {
                logger.error("notification process exceiption", e);
            }

        }
    }

    private List<Notification> notiNewRecipe(Notification noti) {
        CompactUserInfo user = null;
        Following follow = null;
        try {
            user = UserCache.getInstance().get(noti.getFrom());
            follow = FollowingDAO.getInstance().get(noti.getFrom(), Following.class);
        } catch (DAOException e) {
            logger.error("process notiNewRecipe error", e);
        }

        List<Notification> notis = new ArrayList<Notification>();
        for (String follower : follow.getFollowers()) {
            Notification notiComp = new Notification();
            notiComp.setFrom(noti.getFrom());
            notiComp.setTo(follower);
            notiComp.setRecipeId(noti.getRecipeId());
            notiComp.setType(Notification.NEW_RECIPE_FROM_FOLLOWING_TYPE);
            notiComp.setFromAvatar(user.getAvatarUrl());
            notiComp.setFromName(user.getDisplayName());
            notiComp.setRecipeTitle(noti.getRecipeTitle());

            notis.add(noti);
        }

        return notis;
    }

    private List<Notification> notiNewComment(Notification notification) {
        CompactUserInfo user = null;
        try {
            user = UserCache.getInstance().get(notification.getFrom());

        } catch (DAOException e) {
            logger.error("process notiNewComment error", e);
        }

        Notification noti = new Notification();
        noti.setFrom(notification.getFrom());
        noti.setTo(notification.getTo());
        noti.setRecipeId(notification.getRecipeId());
        noti.setType(Notification.NEW_COMMENT_TYPE);
        noti.setFromAvatar(user.getAvatarUrl());
        noti.setFromName(user.getDisplayName());
        noti.setRecipeTitle(notification.getRecipeTitle());

        List<Notification> notis = new ArrayList<Notification>();
        notis.add(noti);
        return notis;
    }

    private List<Notification> notiFavorite(Notification notification) {
        CompactUserInfo user = null;
        try {
            user = UserCache.getInstance().get(notification.getFrom());

        } catch (DAOException e) {
            logger.error("process notiFavorite error", e);
        }

        Notification noti = new Notification();
        noti.setFrom(notification.getFrom());
        noti.setTo(notification.getTo());
        noti.setRecipeId(notification.getRecipeId());
        noti.setType(Notification.NEW_FAVORITE_TYPE);
        noti.setFromAvatar(user.getAvatarUrl());
        noti.setFromName(user.getDisplayName());
        noti.setRecipeTitle(notification.getRecipeTitle());

        List<Notification> notis = new ArrayList<Notification>();
        notis.add(noti);
        return notis;
    }

    private List<Notification> notiFollower(Notification notification) {
        CompactUserInfo user = null;
        try {
            user = UserCache.getInstance().get(notification.getFrom());

        } catch (DAOException e) {
            logger.error("process notiFollower error", e);
        }

        Notification noti = new Notification();
        noti.setFrom(notification.getFrom());
        noti.setTo(notification.getTo());
        noti.setType(Notification.NEW_FOLLOWER_TYPE);
        noti.setFromAvatar(user.getAvatarUrl());
        noti.setFromName(user.getDisplayName());

        List<Notification> notis = new ArrayList<Notification>();
        notis.add(noti);
        return notis;
    }
    
    private List<Notification> notiRemoveRecipe(Notification notification) {
       
        Notification noti = new Notification();
        noti.setFrom(notification.getFrom());
        noti.setTo(notification.getTo());
        noti.setType(Notification.NEW_FOLLOWER_TYPE);

        List<Notification> notis = new ArrayList<Notification>();
        notis.add(noti);
        return notis;
    }
}
