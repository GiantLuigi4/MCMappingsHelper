package tfc.mappings.structure;

import java.util.Objects;

public class MappingsField {
	private final String owner;
	private final String obsfucation;
	private final String mapped;
	private final String desc;
	
	public MappingsField(String owner, String obsfucation, String mapped, String desc) {
		this.owner = owner;
		this.obsfucation = obsfucation;
		this.mapped = mapped;
		this.desc = desc;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MappingsField field = (MappingsField) o;
		return Objects.equals(owner, field.owner) &&
				Objects.equals(obsfucation, field.obsfucation) &&
				Objects.equals(mapped, field.mapped) &&
				Objects.equals(desc, field.desc);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(owner, obsfucation, mapped, desc);
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getObsfucation() {
		return obsfucation;
	}
	
	public String getMapped() {
		return mapped;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String toString() {
		return desc + ":" + owner + ":" + obsfucation + ":" + mapped;
	}
	
	public String fancyString() {
		return desc + " | " + mapped + " : " + obsfucation;
	}
}
