package org.entity;

import org.bson.types.ObjectId;
import org.json.JsonIgnoreEmpty;
import org.json.JsonIgnoreProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

@Entity(noClassnameStored = true)
public class Ingredient {

	@Id
	private ObjectId id;

	private String name;

	@Property(value = "normalize_name")
	@Indexed(background = true)
	@JsonIgnoreProperty
	private String normalizedName;

	private String unit;

	private String quantity;

	private String group;

	@JsonIgnoreEmpty
	private int popularPoint;

	public String getId() {
		return id.toHexString();
	}

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getNormalizedName() {
		return normalizedName;
	}

	public void setNormalizedName(String normalizedName) {
		this.normalizedName = normalizedName;
	}

	public int getPopularPoint() {
		return popularPoint;
	}

	public void setPopularPoint(int popularPoint) {
		this.popularPoint = popularPoint;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ingredient) {
			Ingredient ing = (Ingredient) obj;
			return ing.getId().equals(this.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
