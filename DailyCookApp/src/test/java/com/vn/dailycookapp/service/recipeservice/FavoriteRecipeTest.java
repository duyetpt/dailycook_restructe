package com.vn.dailycookapp.service.recipeservice;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mongodb.client.FindIterable;
import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.service.HeaderField;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FavoriteRecipeTest extends AbstractTest {
	
	// test Favorite
	@Test
	public void test1() {
		// request create recipe
		getToken();
		try {
			importData("Recipe", getClass().getResource("/recipes.json").getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String userId =     "560b3f83f128c211acc9eff5";
		String recipeId =   "5612a3be432ac0716cd94970";
		
		{
			responseData = target("dailycook/recipe/" + recipeId + "/favorite").queryParam("flag", 1)
					.request(MediaType.APPLICATION_JSON_TYPE).header(HeaderField.USER_ID, userId).get(String.class);
			System.out.println(responseData);
			@SuppressWarnings("unused")
			JSONObject responseObj = getResponse();
		}
		
		{
			System.out.println("--->   Favorite collection data   <---");
			FindIterable<Document> result = _mongo.getCollection("Favorite").find();
			for (Document doc : result) {
				System.out.println("--> " + doc.toJson());
			}
			
			System.out.println("--->   Favorited collection data   <---");
			FindIterable<Document> result1 = _mongo.getCollection("Favorited").find();
			for (Document doc : result1) {
				System.out.println("--> " + doc.toJson());
			}
		}
		
		{
			userId = "55f0feeadcb2fd437cdd0e2c";
			responseData = target("dailycook/recipe/" + recipeId + "/favorite").queryParam("flag", 1)
					.request(MediaType.APPLICATION_JSON_TYPE).header(HeaderField.USER_ID, userId).get(String.class);
			System.out.println(responseData);
			@SuppressWarnings("unused")
			JSONObject responseObj = getResponse();
		}
		
		{
			System.out.println("--->   Favorite collection data   <---");
			FindIterable<Document> result = _mongo.getCollection("Favorite").find();
			for (Document doc : result) {
				System.out.println("--> " + doc.toJson());
			}
			
			System.out.println("--->   Favorited collection data   <---");
			FindIterable<Document> result1 = _mongo.getCollection("Favorited").find();
			for (Document doc : result1) {
				System.out.println("--> " + doc.toJson());
			}
		}
		
	}
	
	// Test Unfavorite
	@Test
	public void test2() {
		String userId = "560b3f83f128c211acc9eff5";
		String recipeId = "5612a3be432ac0716cd94970";
		
		responseData = target("dailycook/recipe/" + recipeId + "/favorite").queryParam("flag", -1)
				.request(MediaType.APPLICATION_JSON_TYPE).header(HeaderField.USER_ID, userId).get(String.class);
		System.out.println(responseData);
		@SuppressWarnings("unused")
		JSONObject responseObj = getResponse();
		
		System.out.println("--->   Favorite collection data   <---");
		FindIterable<Document> result = _mongo.getCollection("Favorite").find();
		for (Document doc : result) {
			System.out.println("--> " + doc.toJson());
		}
		
		System.out.println("--->   Favorited collection data   <---");
		FindIterable<Document> result1 = _mongo.getCollection("Favorited").find();
		for (Document doc : result1) {
			System.out.println("--> " + doc.toJson());
		}
		
		System.out.println("--->   Favorited collection data   <---");
		FindIterable<Document> result2 = _mongo.getCollection("User").find();
		for (Document doc : result2) {
			System.out.println("--> " + doc.toJson());
		}
	}
}
