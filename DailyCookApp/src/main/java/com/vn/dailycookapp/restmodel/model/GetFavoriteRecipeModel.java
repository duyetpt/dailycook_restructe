package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;

import org.dao.FavoriteDAO;
import org.dao.RecipeDAO;
import org.entity.Favorite;
import org.entity.Recipe;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.SearchRecipeResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class GetFavoriteRecipeModel extends AbstractModel {
	private int	skip;
	private int	take;
	
	@Override
	protected void preExecute(String... data) throws InvalidParamException {
		myId = data[0];
		skip = Integer.parseInt(data[1]);
		take = Integer.parseInt(data[2]);
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		List<SearchRecipeResponseData> data = new ArrayList<SearchRecipeResponseData>();
		
		// get list favorite recipe id
		Favorite fav = FavoriteDAO.getInstance().get(myId, Favorite.class);
		if (fav != null) {
			// get recipe by list
			List<Recipe> recipes = RecipeDAO.getInstance().listFavoriteRecipe(fav.getRecipeIds(), skip, take);
			
			if (recipes != null) {
				for (Recipe recipe : recipes) {
					SearchRecipeResponseData resData = new SearchRecipeResponseData();
					resData.setFavorite(true);
					resData.setRecipeId(recipeId);
					resData.setNFavorite(recipe.getFavoriteNumber());
					resData.setRecipePicture(recipe.getPictureUrl());
					resData.setRecipeStory(recipe.getStory());
					resData.setTitlel(recipe.getTitle());
					
					CompactUserInfo user = UserCache.getInstance().get(recipe.getOwner());
					resData.setUsername(user.getDisplayName());
					
					data.add(resData);
				}
			}
		}
		
		response.setData(data);
		return response;
	}
	
}
