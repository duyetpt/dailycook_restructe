package org.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "Favorited", noClassnameStored = true)
public class Favorited {
	
	@Id
	private ObjectId		id;
	
	@Property("user_ids")
	private List<String>	userIds;
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public List<String> getUserIds() {
		return userIds;
	}
	
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
}
