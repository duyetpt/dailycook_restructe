package com.vn.dailycookapp.security.authentication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vn.dailycookapp.entity.response.AccountInfo;

class VerifyFacebookAccount {
	
	final Logger							logger					= LoggerFactory.getLogger(getClass());
	
	private static String					fb_graph_path			= "https://graph.facebook.com/me?fields=id,name,picture{url},cover,about,address,birthday&access_token=";
	private static String					fb_graph_path_checked	= "https://graph.facebook.com/me?fields=id&access_token=";
	private final String					USER_AGENT				= "Mozilla/5.0";
	
	private static VerifyFacebookAccount	instance;
	
	private VerifyFacebookAccount() {
		
	}
	
	public static VerifyFacebookAccount getInstance() {
		if (instance == null) {
			instance = new VerifyFacebookAccount();
		}
		
		return instance;
	}
	
	// public static void main(String[] args) throws Exception {
	// String token =
	// "CAACEdEose0cBAHxX08ZBBIdD5Vw1UYtMP0pV6XSoCM4O2tZBAWHWjjQUezFUr5UA1Jeu5ZATmQDZCW3ol1YvbKMwNkemyrVRYxfZChND9A78LqY9etX0BfsFeed2sYB4dj9YHGf5GxauDNThuANGlb1zNaRaO3l31vKb1yEyH1X4vGQMcyFWQb5ymPYKoxNWXhALZAJABa61ebfJDrU6Fu";
	//
	// VerifyFacebookAccount fb = new VerifyFacebookAccount();
	// AccountInfo acc = fb.sendGet(token);
	// System.out.println(JsonTransformer.getInstance().marshall(acc));
	// }
	
	// HTTP GET request
	AccountInfo sentGet(String token) {
		try {
			URL obj = new URL(fb_graph_path + token);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			
			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				logger.error(("get acc info from facebook error: " + responseCode + ". token= " + token));
				return null;
			}
			
			// read response data
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			// extract infor
			JSONObject jsonObj = new JSONObject(response.toString());
			// get fb_id
			String id = jsonObj.getString("id");
			// get full name
			String name = jsonObj.getString("name");
			// get cover image
			String coverUrl = jsonObj.getJSONObject("cover").getString("source");
			// get avatar image
			JSONObject picture = jsonObj.getJSONObject("picture");
			String avatarUrl = picture.getJSONObject("data").getString("url");
			// get dob MM-dd-YYYY
			String dob = null;
			try {
				dob = jsonObj.getString("birthday");
			} catch (Exception ex) {
				logger.error(("can't get dob of this user"));
			}
			
			AccountInfo acc = new AccountInfo();
			acc.setFbId(id);
			acc.setAvatarUrl(avatarUrl);
			acc.setCoverUrl(coverUrl);
			acc.setDisplayName(name);
			acc.setDob(dob);
			
			return acc;
		} catch (Exception ex) {
			logger.error(("get facebook infor error"), ex);
		}
		
		return null;
	}
	
	String checkedValidToken(String token) {
		try {
			URL obj = new URL(fb_graph_path_checked + token);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			
			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				logger.error(("Check fb account active error: " + responseCode + ". token= " + token));
				return null;
			}
			
			// read response data
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			// extract infor
			JSONObject jsonObj = new JSONObject(response.toString());
			// get fb_id
			String id = jsonObj.getString("id");
			
			return id;
		} catch (Exception ex) {
			logger.error(("Check fb account active error"), ex);
		}
		
		return null;
	}
}
