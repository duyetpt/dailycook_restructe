package com.vn.dailycookapp.service.userservice;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.json.JSONObject;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.service.HeaderField;

public class FollowUserTest extends AbstractTest {
	
	@Test
	public void test1() {
		// request create recipe
		getToken();
		
		// 55f0feeadcb2fd437cdd0e2c
		// 560a621af128c213003bc11e
		String owner = "560b3f83f128c211acc9eff5";
		String userId = "55f0feeadcb2fd437cdd0e2c";
		
		Entity<String> entity = Entity.entity("", MediaType.APPLICATION_JSON_TYPE);
		
		{
			responseData = target("dailycook/user/follow/" + userId).queryParam("flag", 1)
					.request(MediaType.APPLICATION_JSON_TYPE).header(HeaderField.USER_ID, owner)
					.put(entity, String.class);
			System.out.println(responseData);
			@SuppressWarnings("unused")
			JSONObject responseObj = getResponse();
		}
		
		{
			System.out.println("--->   Following collection data   <---");
			FindIterable<Document> result = _mongo.getCollection("Following").find();
			for (Document doc : result) {
				System.out.println("--> " + doc.toJson());
			}
		}
		
		userId = "560a621af128c213003bc11e";
		{
			responseData = target("dailycook/user/follow/" + userId).queryParam("flag", 1)
					.request(MediaType.APPLICATION_JSON_TYPE).header(HeaderField.USER_ID, owner)
					.put(entity, String.class);
			System.out.println(responseData);
			@SuppressWarnings("unused")
			JSONObject responseObj = getResponse();
		}
		
		{
			System.out.println("--->   Following collection data   <---");
			FindIterable<Document> result = _mongo.getCollection("Following").find();
			for (Document doc : result) {
				System.out.println("--> " + doc.toJson());
			}
		}
	}
	
}
