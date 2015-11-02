package com.vn.dailycookapp.service.userservice;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.bson.Document;
import org.json.JSONObject;
import org.json.JsonTransformer;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.security.authentication.CurrentUser;

public class SearchUserTest extends AbstractTest{
	
	@Test
	public void test() {
		try {
			importData("User", getClass().getResource("/User.json").getFile().substring(1));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseData = target("dailycook/user/search").queryParam("keyword", "ki").queryParam("skip", 0).queryParam("take", 10).request().get(String.class);
		
		JSONObject jsonObj = getResponse();
		CurrentUser user =JsonTransformer.getInstance().unmarshall(jsonObj.getJSONObject("data").toString(), CurrentUser.class);
		assertEquals(26, user.getToken().length());
		
		System.out.println("--->   Favorited collection data   <---");
		FindIterable<Document> result1 = _mongo.getCollection("User").find();
		for (Document doc : result1) {
			System.out.println("--> " + doc.toJson());
		}
	}	
}
