package com.vn.dailycookapp.entity.response;

import org.json.JsonIgnoreEmpty;

public class NewFeedResponseData {
	
	private String recipeId;
	
	private String	username;
	
	@JsonIgnoreEmpty
	private String	avatarUrl;
	
	private String	recipeName;
	
	@JsonIgnoreEmpty
	private String	recipePicture;
	
	private int		nComment;
	
	private int		nFavorite;
	
	private boolean	isFavorite;
	
	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getRecipeName() {
		return recipeName;
	}
	
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	
	public String getRecipePicture() {
		return recipePicture;
	}
	
	public void setRecipePicture(String recipePicture) {
		this.recipePicture = recipePicture;
	}
	
	public int getNComment() {
		return nComment;
	}
	
	public void setnComment(int nComment) {
		this.nComment = nComment;
	}
	
	public int getNFavorite() {
		return nFavorite;
	}
	
	public void setnFavorite(int nFavorite) {
		this.nFavorite = nFavorite;
	}
	
	public boolean getIsFavorite() {
		return isFavorite;
	}
	
	public void setIsFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
}
