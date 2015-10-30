package com.vn.dailycookapp.notification;

import java.util.ArrayList;
import java.util.List;

import org.dao.DAOException;
import org.dao.FollowingDAO;
import org.dao.NotificationDAO;
import org.dao.RecipeDAO;
import org.entity.Following;
import org.entity.Notification;
import org.entity.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.utils.lang.Language;

public class NotificationWorker extends Thread {
	Logger	logger	= LoggerFactory.getLogger(getClass());
	
	public void run() {
		while (true) {
			try {
				Notification noti = NotificationActionImp.getInstance().getNoti();
				Recipe recipe = null;
				try {
					recipe = RecipeDAO.getInstance().get(noti.getRecipeId());
				} catch (Exception ex) {
					logger.error("get Notification from queue exceiption", ex);
				}
				
				List<Notification> list = null;
				switch (noti.getType()) {
					case Notification.NEW_COMMENT_TYPE:
						list = notiNewComment(recipe, noti.getFrom());
						break;
					case Notification.NEW_FAVORITE_TYPE:
						list = notiFavorite(recipe, noti.getFrom());
						break;
					case Notification.NEW_FOLLOWER_TYPE:
						list = notiFollower(noti.getFrom(), noti.getTo());
						break;
					case Notification.NEW_RECIPE_FROM_FOLLOWING_TYPE:
						list = notiNewRecipe(recipe, noti.getFrom());
						break;
					case Notification.WARM_TYPE:
						// TODO
						break;
				}
				
				// save to DB
				NotificationDAO.getInstance().save(list);
			} catch (Exception e) {
				logger.error("notification process exceiption", e);
			}
			
		}
	}
	
	private List<Notification> notiNewRecipe(Recipe recipe, String from) {
		String title = recipe.getTitle();
		CompactUserInfo user = null;
		Following follow = null;
		try {
			user = UserCache.getInstance().get(from);
			follow = FollowingDAO.getInstance().get(from, Following.class);
		} catch (DAOException e) {
			logger.error("process notiNewRecipe error", e);
		}
		
		List<Notification> notis = new ArrayList<Notification>();
		for (String follower : follow.getFollowers()) {
			String messageForm = Language.getInstance().getMessage(Notification.NEW_RECIPE_FROM_FOLLOWING_TYPE,
					user.getLanguage());
			String msg = String.format(messageForm, user.getDisplayName(), title);
			
			Notification noti = new Notification();
			noti.setFrom(from);
			noti.setTo(follower);
			noti.setMsg(msg);
			noti.setRecipeId(recipe.getId());
			noti.setType(Notification.NEW_RECIPE_FROM_FOLLOWING_TYPE);
			
			notis.add(noti);
		}
		
		return notis;
	}
	
	private List<Notification> notiNewComment(Recipe recipe, String from) {
		String title = recipe.getTitle();
		CompactUserInfo user = null;
		try {
			user = UserCache.getInstance().get(from);
			
		} catch (DAOException e) {
			logger.error("process notiNewComment error", e);
		}
		
		String messageForm = Language.getInstance().getMessage(Notification.NEW_COMMENT_TYPE, user.getLanguage());
		String msg = String.format(messageForm, user.getDisplayName(), title);
		
		Notification noti = new Notification();
		noti.setFrom(from);
		noti.setTo(recipe.getOwner());
		noti.setMsg(msg);
		noti.setRecipeId(recipe.getId());
		noti.setType(Notification.NEW_COMMENT_TYPE);
		
		List<Notification> notis = new ArrayList<Notification>();
		notis.add(noti);
		return notis;
	}
	
	private List<Notification> notiFavorite(Recipe recipe, String from) {
		String title = recipe.getTitle();
		CompactUserInfo user = null;
		try {
			user = UserCache.getInstance().get(from);
			
		} catch (DAOException e) {
			logger.error("process notiFavorite error", e);
		}
		
		String messageForm = Language.getInstance().getMessage(Notification.NEW_FAVORITE_TYPE, user.getLanguage());
		String msg = String.format(messageForm, user.getDisplayName(), title);
		
		Notification noti = new Notification();
		noti.setFrom(from);
		noti.setTo(recipe.getOwner());
		noti.setMsg(msg);
		noti.setRecipeId(recipe.getId());
		noti.setType(Notification.NEW_FAVORITE_TYPE);
		
		List<Notification> notis = new ArrayList<Notification>();
		notis.add(noti);
		return notis;
	}
	
	private List<Notification> notiFollower(String from, String to) {
		CompactUserInfo user = null;
		try {
			user = UserCache.getInstance().get(from);
			
		} catch (DAOException e) {
			logger.error("process notiFollower error", e);
		}
		
		String messageForm = Language.getInstance().getMessage(Notification.NEW_FOLLOWER_TYPE, user.getLanguage());
		String msg = String.format(messageForm, user.getDisplayName());
		
		Notification noti = new Notification();
		noti.setFrom(from);
		noti.setTo(to);
		noti.setMsg(msg);
		noti.setType(Notification.NEW_FOLLOWER_TYPE);
		
		List<Notification> notis = new ArrayList<Notification>();
		notis.add(noti);
		return notis;
	}
}
