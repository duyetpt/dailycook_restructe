package com.vn.dailycookapp.service.recipeservice;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.vn.dailycookapp.AbstractTest;

public class GetCommentTest extends AbstractTest {
	
	private final String	recipeId	= "5612a3df432ac0716cd94971";
	
	@Test
	public void test() {
		// import data
		getToken();
		try {
			importData("Recipe", getClass().getResource("/recipes.json").getPath());
			importData("Comment", getClass().getResource("/Comment.json").getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// get
		responseData = target("dailycook/recipe/" + recipeId + "/comment/get").queryParam("skip", 0).queryParam("take", 5).request(MediaType.APPLICATION_JSON_TYPE)
				.get(String.class);
		System.out.println(responseData);
		getResponse();
	}
}
