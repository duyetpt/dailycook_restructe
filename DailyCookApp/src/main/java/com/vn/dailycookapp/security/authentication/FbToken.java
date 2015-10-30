package com.vn.dailycookapp.security.authentication;

class FbToken {
	
	private String	fbId;
	private String	refreshToken;
	
	public String getFbId() {
		return fbId;
	}
	
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
