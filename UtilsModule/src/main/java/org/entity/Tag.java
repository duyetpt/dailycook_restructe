package org.entity;

import org.bson.types.ObjectId;
import org.json.JsonIgnoreProperty;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

public class Tag {

	@Id
	private ObjectId id;

	private String tag;
	@Indexed(unique = true)
	private String normalizeTag;

	@JsonIgnoreProperty
	private int popularPoint;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNormalizeTag() {
		return normalizeTag;
	}

	public void setNormalizeTag(String normalizeTag) {
		this.normalizeTag = normalizeTag;
	}

	public int getPopularPoint() {
		return popularPoint;
	}

	public void setPopularPoint(int popularPoint) {
		this.popularPoint = popularPoint;
	}

}
