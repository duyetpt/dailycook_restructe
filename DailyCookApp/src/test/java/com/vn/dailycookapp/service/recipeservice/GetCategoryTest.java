package com.vn.dailycookapp.service.recipeservice;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.junit.Test;

import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.utils.lang.Language;

public class GetCategoryTest extends AbstractTest {
	
	@SuppressWarnings("unused")
	@Test
	public void test() {
		try {
			importData("Category", getClass().getResource("/Category.json").getFile());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		String parentId = "";
		{
			responseData = target("dailycook/recipe/" + Language.VIETNAMESE + "/categories")
					.queryParam("parentId", parentId).request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
			JSONObject responseObj = getResponse();
			parentId = responseObj.getJSONArray("data").getJSONObject(0).getString("id");
			System.out.println(responseData);
		}
		
		{
			responseData = target("dailycook/recipe/" + Language.VIETNAMESE + "/categories")
					.queryParam("parentId", parentId).request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
			JSONObject responseObj = getResponse();
			System.out.println("parent:" + parentId + " => " + responseData);
		}	
	}
}
