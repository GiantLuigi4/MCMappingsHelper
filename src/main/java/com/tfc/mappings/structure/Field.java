package com.tfc.mappings.structure;

import java.util.Objects;

public class Field {
	private final String owner;
	private final String secondary;
	private final String primary;
	private final String desc;
	
	public Field(String owner, String secondary, String primary, String desc) {
		this.owner = owner;
		this.secondary = secondary;
		this.primary = primary;
		this.desc = desc;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Field field = (Field) o;
		return Objects.equals(owner, field.owner) &&
				Objects.equals(secondary, field.secondary) &&
				Objects.equals(primary, field.primary) &&
				Objects.equals(desc, field.desc);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(owner, secondary, primary, desc);
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getSecondary() {
		return secondary;
	}
	
	public String getPrimary() {
		return primary;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String toString() {
		return desc + ":" + owner + ":" + secondary + ":" + primary;
	}
	
	public String fancyString() {
		return desc + " | " + primary + " : " + secondary;
	}
}
