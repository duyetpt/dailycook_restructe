package com.vn.dailycookapp.entity.response;

public class GetNotificationResponseData {
	
	private String	notiId;
	private String	msg;
	private boolean	status;
	private String	recipeId;
	private String	type;
	private String	fromAvatar;
	private String	recipeTitle;
	private String	fromName;
	private String	from;
	
	public String getNotiId() {
		return notiId;
	}
	
	public void setNotiId(String notiId) {
		this.notiId = notiId;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getFromAvatar() {
		return fromAvatar;
	}
	
	public void setFromAvatar(String fromAvatar) {
		this.fromAvatar = fromAvatar;
	}
	
	public String getRecipeTitle() {
		return recipeTitle;
	}
	
	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}
	
	public String getFromName() {
		return fromName;
	}
	
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
}
