package com.vn.dailycookapp.entity.response;

import org.entity.Recipe;

import com.vn.dailycookapp.cache.user.CompactUserInfo;

public class RecipeResponseData {
	
	private Recipe	recipe;
	private String	userName;
	private String	avatarUrl;
	private boolean	isFollowing;
	private int		numberfollower;
	
	public RecipeResponseData(Recipe recipe, CompactUserInfo user) {
		this.recipe = recipe;
		this.userName = user.getDisplayName();
		this.avatarUrl = user.getAvatarUrl();
		this.numberfollower = user.getNumberFollower();
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public boolean getIsFollowing() {
		return isFollowing;
	}
	
	public void setIsFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	
	public int getNumberfollower() {
		return numberfollower;
	}
	
	public void setNumberfollower(int numberfollower) {
		this.numberfollower = numberfollower;
	}
	
}
