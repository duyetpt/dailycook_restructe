package org.entity;

import java.util.List;

import org.TimeUtils;
import org.json.JsonIgnoreEmpty;
import org.json.JsonIgnoreProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

@Entity(value = "Recipe", noClassnameStored = true)
public class Recipe {
	public static final int APPROVED_FLAG = 1;
	public static final int REPORTED_FLAG = 0;
	public static final int REMOVED_FLAG = -1;

	public static final int MAX_CATEGORY_NUMBER = 5;

	@Id
	@JsonIgnoreEmpty
	private String id;

	private String title;

	@Property(value = "normalize_title")
	@JsonIgnoreProperty
	private String normalizedTitle;

	private String owner;

	@JsonIgnoreProperty
	@Property(value = "comment_number")
	private int commentNumber;

	@Property(value = "favorite_number")
	private int favoriteNumber;

	@Property(value = "picture_url")
	private String pictureUrl;

	// tags no normalize
	@Property(value = "categories")
	private List<String> categoryIds;

	// Normalize of categoryIds
	@JsonIgnoreProperty
	private List<String> tags;

	@Property(value = "status_flag")
	@JsonIgnoreProperty
	private int statusFlag = APPROVED_FLAG;

	@Property(value = "interval_cook")
	private int intervalCook;

	private String story;

	@JsonIgnoreProperty
	private boolean selected = false;

	@Property(value = "deleted_time")
	@JsonIgnoreProperty
	private Long deletedTime;

	@Property(value = "created_time")
	@JsonIgnoreProperty
	private Long createdTime = TimeUtils.getCurrentGMTTime();

	@Reference
	private List<Ingredient> ingredients;

	private List<Recipe.Step> steps;

	private Integer view;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNormalizedTitle() {
		return normalizedTitle;
	}

	public void setNormalizedTitle(String normalizedTitle) {
		this.normalizedTitle = normalizedTitle;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	public int getFavoriteNumber() {
		return favoriteNumber;
	}

	public void setFavoriteNumber(int favoriteNumber) {
		this.favoriteNumber = favoriteNumber;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public List<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public int getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(int statusFlag) {
		this.statusFlag = statusFlag;
	}

	public int getIntervalCook() {
		return intervalCook;
	}

	public void setIntervalCook(int intervalCook) {
		this.intervalCook = intervalCook;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Long getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Long deletedTime) {
		this.deletedTime = deletedTime;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	@Transient
	private boolean isFavorite;

	public boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(boolean favorite) {
		this.isFavorite = favorite;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public static class Step {
		private int stepNo;
		private String description;
		@Property(value = "picture_url")
		private String pictureUrl;

		public int getStepNo() {
			return stepNo;
		}

		public void setStepNo(int stepNo) {
			this.stepNo = stepNo;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPictureUrl() {
			return pictureUrl;
		}

		public void setPictureUrl(String pictureUrl) {
			this.pictureUrl = pictureUrl;
		}

	}

}
