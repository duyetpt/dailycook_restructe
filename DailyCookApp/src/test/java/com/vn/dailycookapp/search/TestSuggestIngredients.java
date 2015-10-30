package com.vn.dailycookapp.search;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vn.dailycookapp.cache.RecipeInfoCache;
import com.vn.dailycookapp.cache.RecipeManager;

public class TestSuggestIngredients {
	
	/**
	 * test sugget ingredient with result < 10
	 */
	@Test
	public void test1() {
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe1", 10);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe2", 8);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe3", 15);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe4", 48);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe5", 30);
		RecipeInfoCache.getInstance().getIngredientMaps().put("xxxx5", 30);
		RecipeInfoCache.getInstance().getIngredientMaps().put("xrrrr5", 32);
		RecipeManager manager = RecipeManager.getInstance();
		List<String> result = manager.suggestIngredients("recipe");
		System.out.println(result);
		assertEquals(5, result.size());
		assertEquals("recipe4", result.get(0));
		assertEquals("recipe5", result.get(1));
		assertEquals("recipe3", result.get(2));
		assertEquals("recipe1", result.get(3));
		assertEquals("recipe2", result.get(4));

	}
	
	/**
	 * test sugget ingredient with result > 10
	 */
	@Test
	public void test2() {
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe1", 10);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe2", 8);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe3", 15);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe4", 48);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe5", 30);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe6", 31);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe7", 32);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe8", 33);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe9", 34);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe10", 35);
		RecipeInfoCache.getInstance().getIngredientMaps().put("recipe11", 5);
		
		RecipeInfoCache.getInstance().getIngredientMaps().put("xxxx5", 30);
		RecipeInfoCache.getInstance().getIngredientMaps().put("xrrrr5", 32);
		RecipeManager manager = RecipeManager.getInstance();
		List<String> result = manager.suggestIngredients("recipe");
		System.out.println(result);
		assertEquals(10, result.size());
		int i = 0;
		
		assertEquals("recipe4", result.get(i++));
		assertEquals("recipe10", result.get(i++));
		assertEquals("recipe9", result.get(i++));
		assertEquals("recipe8", result.get(i++));
		assertEquals("recipe7", result.get(i++));
		assertEquals("recipe6", result.get(i++));
		assertEquals("recipe5", result.get(i++));
		assertEquals("recipe3", result.get(i++));
		assertEquals("recipe1", result.get(i++));
		assertEquals("recipe2", result.get(i++));
		
	}
	
	/**
	 * test suggest tag with result < 10
	 */
	@Test
	public void test3() {
		RecipeInfoCache.getInstance().getTagsMap().put("recipe1", 10);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe2", 8);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe3", 15);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe4", 48);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe5", 30);
		RecipeInfoCache.getInstance().getTagsMap().put("xxxx5", 30);
		RecipeInfoCache.getInstance().getTagsMap().put("xrrrr5", 32);
		RecipeManager manager = RecipeManager.getInstance();
		List<String> result = manager.suggestTags("recipe");
		System.out.println(result);
		assertEquals(5, result.size());
		assertEquals("recipe4", result.get(0));
		assertEquals("recipe5", result.get(1));
		assertEquals("recipe3", result.get(2));
		assertEquals("recipe1", result.get(3));
		assertEquals("recipe2", result.get(4));

	}
	
	/**
	 * test suggest tag with result > 10
	 */
	@Test
	public void test4() {
		RecipeInfoCache.getInstance().getTagsMap().put("recipe1", 10);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe2", 8);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe3", 15);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe4", 48);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe5", 30);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe6", 31);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe7", 32);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe8", 33);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe9", 34);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe10", 35);
		RecipeInfoCache.getInstance().getTagsMap().put("recipe11", 5);
		
		RecipeInfoCache.getInstance().getTagsMap().put("xxxx5", 30);
		RecipeInfoCache.getInstance().getTagsMap().put("xrrrr5", 32);
		RecipeManager manager = RecipeManager.getInstance();
		List<String> result = manager.suggestTags("recipe");
		System.out.println(result);
		assertEquals(10, result.size());
		int i = 0;
		
		assertEquals("recipe4", result.get(i++));
		assertEquals("recipe10", result.get(i++));
		assertEquals("recipe9", result.get(i++));
		assertEquals("recipe8", result.get(i++));
		assertEquals("recipe7", result.get(i++));
		assertEquals("recipe6", result.get(i++));
		assertEquals("recipe5", result.get(i++));
		assertEquals("recipe3", result.get(i++));
		assertEquals("recipe1", result.get(i++));
		assertEquals("recipe2", result.get(i++));
	}
	
	@Test
	public void test5() {
		RecipeInfoCache.getInstance().getNameMap().put("recipe1", new ArrayList<String>());
		RecipeInfoCache.getInstance().getNameMap().put("XXX recipe", new ArrayList<String>());
		RecipeInfoCache.getInstance().getNameMap().put("YYYY recipe1", new ArrayList<String>());
		RecipeInfoCache.getInstance().getNameMap().put("recipe12", new ArrayList<String>());
		
		RecipeInfoCache.getInstance().getNameMap().put("XKJSDKJF ASJFK", new ArrayList<String>());
		
		RecipeManager manager = RecipeManager.getInstance();
		List<String> result = manager.suggestName("recipe");
		System.out.println(result);
		assertEquals(4, result.size());
	}
}

