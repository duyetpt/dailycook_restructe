package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.Unicode;
import org.dao.DAOException;
import org.dao.FavoriteDAO;
import org.dao.RecipeDAO;
import org.entity.Favorite;
import org.entity.Recipe;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.SearchRecipeResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class SearchRecipeModel extends AbstractModel {
	private static final String	INGREDIENT_TYPE	= "ingredients";
	private static final String	TAG_TYPE		= "tags";
	private static final String	NAME_TYPE		= "name";
	
	private String				filter;
	private String				keyword;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		filter = data[0] == null ? NAME_TYPE : data[0];
		keyword = Unicode.toAscii(data[1]).toLowerCase();
		userId = data[2];
	}
	
	// TODO
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
//		
//		Set<String> recipeIds = null;
//		Map<String, Integer> nIngredientMatchMap = null;
//		switch (filter) {
//			case INGREDIENT_TYPE:
//				List<String> ingredients = parseKeyword();
//				nIngredientMatchMap = RecipeManager.getInstance().getRecipeByIngredients(ingredients);
//				recipeIds = nIngredientMatchMap.keySet();
//				break;
//			case TAG_TYPE:
//				List<String> tags = parseKeyword();
//				recipeIds = RecipeManager.getInstance().getRecipeByTags(tags);
//				break;
//			case NAME_TYPE:
//				recipeIds = RecipeManager.getInstance().getRecipeByName(keyword);
//				break;
//		}
//		
//		List<SearchRecipeResponseData> result = getResult(recipeIds, nIngredientMatchMap);
//		response.setData(result);
		return response;
	}
	
	private List<String> parseKeyword() {
		List<String> result = new ArrayList<String>();
		String[] keys = keyword.split(",");
		
		for (String key : keys) {
			result.add(key);
		}
		
		return result;
	}
	
	private List<SearchRecipeResponseData> getResult(Set<String> recipeIds, Map<String, Integer> nPercentMap)
			throws DAOException {
		List<SearchRecipeResponseData> result = new ArrayList<SearchRecipeResponseData>();
		// return if recipeIds empty
		if (recipeIds == null || recipeIds.isEmpty())
			return result;
		
		List<Recipe> recipes = RecipeDAO.getInstance().getRecipes(recipeIds);
		Favorite favorite = null;
		if (userId != null) {
			favorite = FavoriteDAO.getInstance().get(userId, Favorite.class);
		}
		
		for (Recipe recipe : recipes) {
			SearchRecipeResponseData resData = new SearchRecipeResponseData();
			resData.setUsername(recipe.getOwner()); // TODO - CACHE USER INFO
			resData.setCreateTime(recipe.getCreatedTime());
			resData.setNFavorite(recipe.getFavoriteNumber());
			resData.setRecipeId(recipe.getId());
			resData.setTitlel(recipe.getTitle());
			resData.setRecipePicture(recipe.getPictureUrl());
			resData.setRecipeStory(recipe.getStory());
			resData.setFavorite(favorite == null ? false : favorite.getRecipeIds().contains(recipe.getId()));
			if (nPercentMap != null)
				resData.setPercentMatch(100 * nPercentMap.get(recipe.getId()) / recipe.getIngredients().size());
			
			result.add(resData);
		}
		
		Collections.sort(result);
		return result;
	}
	
}
