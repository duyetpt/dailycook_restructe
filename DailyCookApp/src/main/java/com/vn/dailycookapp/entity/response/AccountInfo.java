package com.vn.dailycookapp.entity.response;

public class AccountInfo {
	
	private String	fbId;
	private String	displayName;
	private String	avatarUrl;
	private String	coverUrl;
	private String	dob;			// MM-DD-YYYY
	
	public String getFbId() {
		return fbId;
	}
	
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getCoverUrl() {
		return coverUrl;
	}
	
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		this.dob = dob;
	}
	
}
