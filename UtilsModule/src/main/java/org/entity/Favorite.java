package org.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value="Favorite", noClassnameStored=true)
public class Favorite {
	
	@Id
	private ObjectId			id;
	
	@Property("recipe_ids")
	private List<String>	recipeIds;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public List<String> getRecipeIds() {
		return recipeIds;
	}
	
	public void setRecipeIds(List<String> recipeIds) {
		this.recipeIds = recipeIds;
	}
	
}