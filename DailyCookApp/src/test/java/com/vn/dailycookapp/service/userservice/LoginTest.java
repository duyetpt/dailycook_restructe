package com.vn.dailycookapp.service.userservice;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.internal.util.Base64;
import org.json.JSONObject;
import org.json.JsonTransformer;
import org.junit.Test;

import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.security.authentication.CurrentUser;
import com.vn.dailycookapp.security.authentication.LoginMethod;
import com.vn.dailycookapp.service.HeaderField;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class LoginTest extends AbstractTest {
	
	@Test
	// public void testLogin() {
	// String fbToken =
	// "CAAXZBgLezzHwBAN70QlkJyXxlarZCfa2Mc4LGZCCxfF1Bsp1A3Hd1fdlJZCBRe7ZAXDhCIdnf3WfNjPEXYE2yv5kJvMLxjfYxVZC3kl61x3rAbfZAxrfjOkcSwKi7NBkEFPwVRBM7ZCYPhAJZBeEJVZC25OgfJtHYFnNtorvSogJ2T2ouDDcUowqNTWWPsXI0s2rNZC9nD58IwgOW5euVkgXCnKxAiuvzlTbksoiRjFaYontQZDZD";
	// String token = "979565268772394:" + fbToken;
	// String token64 = Base64.encodeAsString(token);
	// String authInfo = "Basic " + token64;
	//
	// String response =
	// target("dailycook/user/login").request(MediaType.APPLICATION_JSON_TYPE).header(HeaderField.AUTHORIZATION,
	// authInfo).header(HeaderField.LOGIN_METHOD,
	// LoginMethod.FACEBOOK).post(null, String.class);
	//
	// JSONObject jsonObj = new JSONObject(response);
	// int errorCode = jsonObj.getInt("error_no");
	// assertEquals(ErrorCodeConstant.SUCCESSUL.getErrorCode(), errorCode);
	// CurrentUser user
	// =JsonTransformer.getInstance().unmarshall(jsonObj.getJSONObject("data").toString(),
	// CurrentUser.class);
	// assertEquals(26, user.getToken().length());
	// }
	public void testLoginWithEmail() {
		try {
			importData("User", getClass().getResource("/User.json").getFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String email = "ki@gmail.com";
		String password = "123456789";
		String token = email + ":" + password;
		String token64 = Base64.encodeAsString(token);
		String authInfo = "Basic " + token64;
		
		String response = target("dailycook/user/login").request(MediaType.APPLICATION_JSON_TYPE)
				.header(HeaderField.AUTHORIZATION, authInfo)
				.header(HeaderField.LOGIN_METHOD, LoginMethod.EMAIL_PASSWORD).post(null, String.class);
		
		JSONObject jsonObj = new JSONObject(response);
		int errorCode = jsonObj.getInt("error_no");
		assertEquals(ErrorCodeConstant.SUCCESSUL.getErrorCode(), errorCode);
		CurrentUser user = JsonTransformer.getInstance().unmarshall(jsonObj.getJSONObject("data").toString(),
				CurrentUser.class);
		assertEquals(26, user.getToken().length());
	}	
}
