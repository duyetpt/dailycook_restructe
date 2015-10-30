package com.vn.dailycookapp.service.recipeservice;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.junit.Test;

import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.service.HeaderField;

public class GetRecipeTest extends AbstractTest {
	
	@Test
	public void test() {
		// request create recipe
		try {
			importData("Recipe", getClass().getResource("/recipes.json").getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		getToken();
		String userId = "560b3f83f128c211acc9eff5";
		String recipeId = "5612a3be432ac0716cd94970";
		responseData = target("dailycook/recipe/get").queryParam("recipe", recipeId).request(MediaType.APPLICATION_JSON_TYPE)
				.header(HeaderField.USER_ID, userId).get(String.class);
		System.out.println(responseData);
		@SuppressWarnings("unused")
		JSONObject responseObj = getResponse();
	}
	
}
