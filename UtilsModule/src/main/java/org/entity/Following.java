package org.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * 
 * @author duyetpt
 *         contain all users who owner is following
 */
@Entity(noClassnameStored = true)
public class Following {
	
	@Id
	private ObjectId		owner;
	
	@Property("following")
	private List<String>	starIds;
	
	private List<String>	followers;
	
	public ObjectId getOwner() {
		return owner;
	}
	
	public void setOwner(ObjectId owner) {
		this.owner = owner;
	}
	
	public List<String> getStarIds() {
		return starIds;
	}
	
	public void setStarIds(List<String> starIds) {
		this.starIds = starIds;
	}
	
	public List<String> getFollowers() {
		return followers;
	}
	
	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}
	
}
