package org.entity;

import org.TimeUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "Comment", noClassnameStored = true)
public class Comment {
	
	@Id
	private String	id;
	
	@Property("owner")
	private String	owner;
	
	@Property("recipe_id")
	private String	recipeId;
	
	@Property("content")
	private String	content;
	
	@Property("create_time")
	private long	createTime	= TimeUtils.getCurrentGMTTime();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
