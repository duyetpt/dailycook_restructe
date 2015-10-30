package com.vn.dailycookapp.restmodel.model;

import org.dao.FavoriteDAO;
import org.dao.FavoritedDAO;
import org.dao.RecipeDAO;
import org.entity.Notification;
import org.entity.Recipe;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.FavoriteResponseData;
import com.vn.dailycookapp.notification.NotificationActionImp;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class FavoriteRecipeModel extends AbstractModel {
	private static final String	FAVORITE_FLAG	= "1";
	private static final String	UNFAVORITE_FLAG	= "-1";
	
	private String				flag;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		try {
			recipeId = data[0];
			flag = data[1];
			userId = data[2];
		} catch (Exception ex) {
			throw new InvalidParamException();
		}
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		
		switch (flag) {
			case FAVORITE_FLAG:
				/**
				 * Increate favoriteNumber in recipe
				 * Push into Favorite, Favorited
				 * Return
				 */
				FavoriteDAO.getInstance().push(userId, recipeId);
				FavoritedDAO.getInstance().push(recipeId, userId);
				RecipeDAO.getInstance().increateFavoriteNumber(recipeId);
				
				// Notification
				NotificationActionImp.getInstance().addNotification(recipeId, userId, null, Notification.NEW_FAVORITE_TYPE);
				break;
			case UNFAVORITE_FLAG:
				/**
				 * Decrease favoriteNumber in recipe
				 * Pull into Favorite, Favorited
				 * Return
				 */
				FavoriteDAO.getInstance().pull(userId, recipeId);
				FavoritedDAO.getInstance().pull(recipeId, userId);
				RecipeDAO.getInstance().decreateFavoriteNumber(recipeId);
		}
		Recipe recipe = RecipeDAO.getInstance().get(recipeId);
		
		FavoriteResponseData data = new FavoriteResponseData();
		data.setFavoriteNumber(recipe.getFavoriteNumber());
		
		response.setData(data);
		return response;
	}
	
}
