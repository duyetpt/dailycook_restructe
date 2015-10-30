package com.vn.dailycookapp.security.session;

import org.TimeUtils;

public final class Session {
	
	static final long	TTL	= 10 * 24 * 60 * 60 * 1000;		// ten day
																
	private String				token;
	private long				lastActiveTime;
	private String				userId;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(long lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	
	public void updateLastActiveTime() {
		this.lastActiveTime = TimeUtils.getCurrentGMTTime();
	}
}
