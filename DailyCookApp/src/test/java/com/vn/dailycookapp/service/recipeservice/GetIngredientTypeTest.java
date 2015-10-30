package com.vn.dailycookapp.service.recipeservice;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.junit.Test;

import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.utils.lang.Language;

public class GetIngredientTypeTest extends AbstractTest {
	
	@SuppressWarnings("unused")
	@Test
	public void test() {
		String lang = Language.VIETNAMESE;
		
		WebTarget webTarget = target("dailycook/recipe/" + lang + "/ingredient_type");
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		
		responseData = builder.get(String.class);
		
		JSONObject jsonObj = getResponse();
		System.out.println(responseData);
	}
}
