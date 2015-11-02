package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;

import org.Unicode;
import org.dao.IngredientDAO;
import org.dao.RecipeDAO;
import org.dao.TagDAO;
import org.dao.UserDAO;
import org.entity.Ingredient;
import org.entity.Notification;
import org.entity.Recipe;
import org.entity.Tag;
import org.json.JsonTransformer;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.RecipeResponseData;
import com.vn.dailycookapp.notification.NotificationActionImp;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.validate.Validator;

public class CreateRecipeModel extends AbstractModel {
	private Recipe	recipe;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		userId = data[0];
		recipe = JsonTransformer.getInstance().unmarshall(data[1], Recipe.class);
		validateRecipe();
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		recipe.setOwner(userId);
		// normalize title, ingredient
		recipe.setNormalizedTitle(Unicode.toAscii(recipe.getTitle()).toLowerCase());
		for (Ingredient ing : recipe.getIngredients()) {
			ing.setNormalizedName(Unicode.toAscii(ing.getName()).toLowerCase());
		}
		// normalize tags
		List<Tag> tags = new ArrayList<Tag>();
		for (String tag : recipe.getCategoryIds()) {
			Tag tagObj = new Tag();
			tagObj.setTag(tag);
			tagObj.setNormalizeTag(Unicode.toAscii(tag).toLowerCase());
			tags.add(tagObj);
		}
		recipe.setTags(tags);
		
		recipe.setView(1);
		
		// save ingredients
		IngredientDAO.getInstance().save(recipe.getIngredients());
		// save tags
		TagDAO.getInstance().save(tags);
		// save recipe
		RecipeDAO.getInstance().save(recipe);
		recipe.setIsFavorite(false);
		// get user info
		CompactUserInfo user = UserCache.getInstance().get(userId);
		RecipeResponseData data = new RecipeResponseData(recipe, user);
		data.setIsFollowing(true);
		
		response.setData(data);
		
		// increate recipe number of user
		UserDAO.getInstance().increateRecipeNumber(userId);
		
		// update user cache info
		UserCache.getInstance().get(userId).increaseNumberRecipe();
		
		// notification, add recipe to user_recipe
		NotificationActionImp.getInstance().addNotification(recipeId, userId, null,
				Notification.NEW_RECIPE_FROM_FOLLOWING_TYPE);
		
		return response;
	}
	
	private void validateRecipe() throws DCAException {
		if (Validator.getInstance().isNull(recipe.getTitle())) {
			logger.error("Recipe title is null or empty error");
			throw new InvalidParamException();
		}
		
		if (Validator.getInstance().isNull(recipe.getCategoryIds())) {
			logger.error("Recipe categories is null or empty error");
			throw new InvalidParamException();
		}
		
		if (Validator.getInstance().isNull(recipe.getPictureUrl())) {
			logger.error("Recipe picture is null or empty error");
			throw new InvalidParamException();
		}
		
		if (Validator.getInstance().isNull(recipe.getIngredients())) {
			logger.error("Recipe ingredient is null or empty error");
			throw new InvalidParamException();
		}
		
		if (Validator.getInstance().isNull(recipe.getSteps())) {
			logger.error("Recipe steps is null or empty error");
			throw new InvalidParamException();
		}
	}
}
