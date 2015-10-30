package com.vn.dailycookapp.cache;

import java.util.LinkedList;
import java.util.List;

import org.entity.Recipe;
import org.entity.Recipe.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AnalysisRecipeWorker extends Thread {
	Logger	logger	= LoggerFactory.getLogger(getClass());
	
	public void run() {
		while (true) {
			Recipe recipe = RecipeManager.getInstance().getRecipe();
			if (recipe == null) {
				try {
					Thread.sleep(10);
					continue;
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				} // sleep a minus
			}
			// analysis name
			// System.out.println("cache: " + recipe.getId());
			{
				List<String> recipeIds = RecipeInfoCache.getInstance().getNameMap().get(recipe.getNormalizedTitle());
				if (recipeIds == null) {
					recipeIds = new LinkedList<>();
					recipeIds.add(recipe.getId());
					RecipeInfoCache.getInstance().getNameMap().put(recipe.getNormalizedTitle(), recipeIds);
				} else {
					recipeIds.add(recipe.getId());
				}
			} // end
			
			// analysis tags
			{
				for (String tag : recipe.getCategoryIds()) {
					// analysis for suggest key word
					Integer tagNumber = RecipeInfoCache.getInstance().getTagsMap().get(tag);
					if (tagNumber == null) {
						RecipeInfoCache.getInstance().getTagsMap().put(tag, 1);
					} else {
						RecipeInfoCache.getInstance().getTagsMap().put(tag, ++tagNumber);
					}
					
					// analysis for search
					List<String> recipeIds = RecipeInfoCache.getInstance().getTagRecipeIdMap().get(tag);
					if (recipeIds == null) {
						recipeIds = new LinkedList<>();
						recipeIds.add(recipe.getId());
						RecipeInfoCache.getInstance().getTagRecipeIdMap().put(tag, recipeIds);
					} else {
						recipeIds.add(recipe.getId());
					}
				}
				
			} // end
			
			// analysis ingredient
			{
				for (Ingredient ing : recipe.getIngredients()) {
					// analysis for suggest key word
					Integer tagNumber = RecipeInfoCache.getInstance().getIngredientMaps().get(ing.getNormalizedName());
					if (tagNumber == null) {
						RecipeInfoCache.getInstance().getIngredientMaps().put(ing.getNormalizedName(), 1);
					} else {
						RecipeInfoCache.getInstance().getIngredientMaps().put(ing.getNormalizedName(), ++tagNumber);
					}
					
					// analysis for search
					List<String> recipeIds = RecipeInfoCache.getInstance().getRecipeIngredientsMap()
							.get(ing.getNormalizedName());
					if (recipeIds == null) {
						recipeIds = new LinkedList<>();
						recipeIds.add(recipe.getId());
						RecipeInfoCache.getInstance().getRecipeIngredientsMap().put(ing.getNormalizedName(), recipeIds);
					} else {
						recipeIds.add(recipe.getId());
					}
				}
			}
			
		}
	}
}
