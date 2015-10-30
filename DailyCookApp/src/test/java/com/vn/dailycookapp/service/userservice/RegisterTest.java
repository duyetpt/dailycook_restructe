package com.vn.dailycookapp.service.userservice;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.json.JSONObject;
import org.json.JsonTransformer;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.entity.request.RegisterInfo;
import com.vn.dailycookapp.security.authentication.CurrentUser;
import com.vn.dailycookapp.utils.lang.Language;

public class RegisterTest extends AbstractTest{
	
	@Test
	public void test() {
		String email = "duyetpt" + System.currentTimeMillis() + "@dailycook.vn";
		String password = "979565268772394:";
		String re_passowrd = password;
		
		RegisterInfo regInfo = new RegisterInfo();
		regInfo.setEmail(email);
//		regInfo.setLanguage(Language.ENGLISH);
		regInfo.setLanguage(Language.VIETNAMESE);
		regInfo.setPassword(password);
		regInfo.setRe_password(re_passowrd);
		
		
		
		String userInfo = JsonTransformer.getInstance().marshall(regInfo);
		Entity<String> entity = Entity.entity(userInfo, MediaType.APPLICATION_JSON_TYPE); 
		responseData = target("dailycook/user/register").request().post(entity, String.class);
		
		JSONObject jsonObj = getResponse();
		CurrentUser user =JsonTransformer.getInstance().unmarshall(jsonObj.getJSONObject("data").toString(), CurrentUser.class);
		assertEquals(26, user.getToken().length());
		
		System.out.println("--->   Favorited collection data   <---");
		FindIterable<Document> result1 = _mongo.getCollection("User").find();
		for (Document doc : result1) {
			System.out.println("--> " + doc.toJson());
		}
	}	
	
	public static void main(String[] args) {
		String email = "duyetpt" + "@dailycook.vn";
		String password = "12345678:";
		String re_passowrd = password;
		
		RegisterInfo regInfo = new RegisterInfo();
		regInfo.setEmail(email);
//		regInfo.setLanguage(Language.ENGLISH);
		regInfo.setLanguage(Language.VIETNAMESE);
		regInfo.setPassword(password);
		regInfo.setRe_password(re_passowrd);
		
		String userInfo = JsonTransformer.getInstance().marshall(regInfo);
		System.out.println(userInfo);
	}
}
