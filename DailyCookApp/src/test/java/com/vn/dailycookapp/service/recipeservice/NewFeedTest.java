package com.vn.dailycookapp.service.recipeservice;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JsonTransformer;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.service.HeaderField;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewFeedTest extends AbstractTest {
	
	@Test
	public void test1() {
		// import data
		getToken();
		try {
			importData("Recipe", getClass().getResource("/recipes.json").getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 560b3f83f128c211acc9eff5
		String userId = "560b3f83f128c211acc9eff5";
		// dailycook/user/newfeed?skip={skip}&take={take}&sort={sort}
		responseData = target("dailycook/user/newfeed").queryParam("skip", 0).queryParam("take", 5)
				.queryParam("sort", "new").request(MediaType.APPLICATION_JSON_TYPE).header(HeaderField.USER_ID, userId)
				.get(String.class);
		System.out.println("=> => => " + responseData);
		getResponse();
		
	}
	
	@Test
	public void test2() {
		// dailycook/user/newfeed?skip={skip}&take={take}&sort={sort}
		responseData = target("dailycook/user/newfeed").queryParam("skip", 0).queryParam("take", 5)
				.queryParam("sort", "new").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		System.out.println("=> => => " + responseData);
		getResponse();
		
	}
	
	@Test
	public void test3() {
		// dailycook/user/newfeed?skip={skip}&take={take}&sort={sort}
		responseData = target("dailycook/user/newfeed").queryParam("skip", 0).queryParam("take", 5)
				.queryParam("sort", "new").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		System.out.println("=> => => " + responseData);
		getResponse();
		
	}
	
	@Test
	public void test4() {
		// dailycook/user/newfeed?skip={skip}&take={take}&sort={sort}
		responseData = target("dailycook/user/newfeed").queryParam("skip", 0).queryParam("take", 5)
				.queryParam("sort", "following").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		System.out.println("=> => => " + responseData);
		getResponse();
		
	}
	
	public static void main(String[] args) {
		String str = JsonTransformer.getInstance().marshall(new ArrayList<>());
		System.out.println(str);
	}
}
