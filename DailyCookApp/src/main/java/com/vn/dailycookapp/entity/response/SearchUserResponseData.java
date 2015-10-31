package com.vn.dailycookapp.entity.response;

import org.json.JsonIgnoreEmpty;

public class SearchUserResponseData {
	private String	username;
	private String	userId;
	@JsonIgnoreEmpty
	private String	introduce;
	@JsonIgnoreEmpty
	private String	avatarUrl;
	private boolean	following;
	private int		numberRecipes;
	private int		numberFollower;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getIntroduce() {
		return introduce;
	}
	
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public boolean getFollowing() {
		return following;
	}
	
	public void setFollowing(boolean following) {
		this.following = following;
	}
	
	public int getNumberRecipes() {
		return numberRecipes;
	}
	
	public void setNumberRecipes(int numberRecipes) {
		this.numberRecipes = numberRecipes;
	}
	
	public int getNumberFollower() {
		return numberFollower;
	}
	
	public void setNumberFollower(int numberFollower) {
		this.numberFollower = numberFollower;
	}
	
}
