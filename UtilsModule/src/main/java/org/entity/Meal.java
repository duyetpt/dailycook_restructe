package org.entity;

import java.util.Set;

import org.json.JsonIgnoreEmpty;
import org.json.JsonIgnoreProperty;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

@JsonIgnoreEmpty
public class Meal {

	public static final String MONDAY = "T2";
	public static final String TUEDAY = "T3";
	public static final String WENDAY = "T4";
	public static final String THIRDAY = "T5";
	public static final String FRIDAY = "T6";
	public static final String SATERDAY = "T7";
	public static final String SUNDAY = "CN";

	public static final String BREAKFAST = "brkfst";
	public static final String AFTERNOON = "aftrnn";
	public static final String DINNER = "dinnr";
	public static final String MIDNIGHT = "midnight";

	@Id
	private String id;
	@Indexed
	private String userId;
	private String day;
	private String time;
	@JsonIgnoreProperty
	private Set<String> recipeIds;

	@JsonIgnoreProperty
	@Indexed(unique = true)
	private String index;

	@Transient
	private int numRecipe;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Set<String> getRecipeIds() {
		return recipeIds;
	}

	public void setRecipeIds(Set<String> recipeIds) {
		this.recipeIds = recipeIds;
	}

	public int getNumRecipe() {
		return numRecipe;
	}

	public void setNumRecipe(int numRecipe) {
		this.numRecipe = numRecipe;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
