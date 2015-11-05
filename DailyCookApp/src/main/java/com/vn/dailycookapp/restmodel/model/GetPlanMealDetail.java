package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;

import org.dao.FavoriteDAO;
import org.dao.MealDAO;
import org.dao.RecipeDAO;
import org.entity.Favorite;
import org.entity.Meal;
import org.entity.Recipe;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.SearchRecipeResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class GetPlanMealDetail extends AbstractModel {
	
	// owner, mealId
	private String	day;
	private String time;
	
	@Override
	protected void preExecute(String... data) throws InvalidParamException {
		myId = data[0];
		day = data[1];
		time = data[2];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		List<SearchRecipeResponseData> result = new ArrayList<SearchRecipeResponseData>();
		Meal meal = MealDAO.getInstance().getMeal(myId, day, time);
		
		if (meal != null) {
			Favorite favorite = null;
			if (myId != null) {
				favorite = FavoriteDAO.getInstance().get(myId, Favorite.class);
			}
			
			List<Recipe> recipes = RecipeDAO.getInstance().getRecipes(meal.getRecipeIds());
			for (Recipe recipe : recipes) {
				SearchRecipeResponseData resData = new SearchRecipeResponseData();
				CompactUserInfo user = UserCache.getInstance().get(recipe.getOwner());
				resData.setUsername(user.getDisplayName());
				resData.setCreateTime(recipe.getCreatedTime());
				resData.setNFavorite(recipe.getFavoriteNumber());
				resData.setRecipeId(recipe.getId());
				resData.setTitlel(recipe.getTitle());
				resData.setRecipePicture(recipe.getPictureUrl());
				resData.setRecipeStory(recipe.getStory());
				resData.setFavorite(favorite == null ? false : favorite.getRecipeIds().contains(recipe.getId()));
				
				result.add(resData);
			}
		}
		
		response.setData(result);
		return response;
	}
	
}
