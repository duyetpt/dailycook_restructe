package com.vn.dailycookapp.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.entity.Recipe;

public class RecipeManager implements RecipeSearch {
	private Queue<Recipe>				queueRecipes;
	private final AnalysisRecipeWorker worker;
	
	private static final RecipeManager	instance	= new RecipeManager();
	
	private RecipeManager() {
		queueRecipes = new ConcurrentLinkedDeque<>();
		worker = new AnalysisRecipeWorker();
		worker.start();
	}
	
	public static RecipeManager getInstance() {
		return instance;
	}
	
	public void addRecipe(Recipe recipe) {
		queueRecipes.add(recipe);
	}
	
	public Recipe getRecipe() {
		return queueRecipes.poll();
	}
	
	@Override
	public List<String> suggestIngredients(String keyword) {
		List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>();
		Map<String, Integer> ingredientMap = RecipeInfoCache.getInstance().getIngredientMaps();
		for (Entry<String, Integer> entry : ingredientMap.entrySet()) {
			if (entry.getKey().contains(keyword)) {
				entryList.add(entry);
			}
		}
		Collections.sort(entryList, new IntegerComparator());
		List<String> result = new ArrayList<String>();
		for (Entry<String, Integer> entry : entryList) {
			result.add(entry.getKey());
		}
		return result.subList(0, result.size() < 10 ? result.size() : 10);
	}
	
	@Override
	public List<String> suggestTags(String keyword) {
		List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>();
		Map<String, Integer> tagMap = RecipeInfoCache.getInstance().getTagsMap();
		for (Entry<String, Integer> entry : tagMap.entrySet()) {
			if (entry.getKey().contains(keyword)) {
				entryList.add(entry);
			}
		}
		Collections.sort(entryList, new IntegerComparator());
		List<String> result = new ArrayList<String>();
		for (Entry<String, Integer> entry : entryList) {
			result.add(entry.getKey());
		}
		return result.subList(0, result.size() < 10 ? result.size() : 10);
	}
	
	@Override
	public List<String> suggestName(String keyword) {
		Map<String, List<String>> mapName = RecipeInfoCache.getInstance().getNameMap();
		
		List<String> result = new ArrayList<>();
		for (String name : mapName.keySet()) {
			if (result.size() >= 10)
				break;
			if (name.contains(keyword)) {
				result.add(name);
			}
		}
		
		return result;
	}
	
	/**
	 * get recipe ids by ingredients
	 */
	@Override
	public Map<String, Integer> getRecipeByIngredients(List<String> ingredients) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String ingredient : ingredients) {
			List<String> list = RecipeInfoCache.getInstance().getRecipeIngredientsMap().get(ingredient);
			if (list != null)
				for (String recipeId : list) {
					Integer count = map.get(recipeId);
					if (count == null)
						map.put(recipeId, 1);
					else
						map.put(recipeId, ++count);
				}
		}
		
		return map;
	}
	
	/**
	 * Get recipeIds by tags
	 */
	@Override
	public Set<String> getRecipeByTags(List<String> tags) {
		Set<String> recipeIds = new HashSet<String>();
		for (String tag : tags) {
			Map<String, List<String>> map = RecipeInfoCache.getInstance().getTagRecipeIdMap(); 
			List<String> list = map.get(tag);
			if (list != null)
				recipeIds.addAll(list);
		}
		
		return recipeIds;
	}
	
	/**
	 * search all suggest name
	 * get all recipeIds by suggest name
	 */
	@Override
	public Set<String> getRecipeByName(String name) {
		Set<String> recipeIds = new HashSet<String>();
		List<String> names = suggestName(name);
		for (String nameKey : names) {
			List<String> list = RecipeInfoCache.getInstance().getNameMap().get(nameKey);
			recipeIds.addAll(list);
		}
		
		return recipeIds;
	}
}
