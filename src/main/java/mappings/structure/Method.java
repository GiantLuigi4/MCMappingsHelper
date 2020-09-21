package mappings.structure;

import java.util.Objects;

public class Method {
	private final String owner;
	private final String secondary;
	private final String primary;
	private final String desc;
	
	public Method(String owner, String secondary, String primary, String desc) {
		this.owner = owner;
		this.secondary = secondary;
		this.primary = primary;
		this.desc = desc;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Method method = (Method) o;
		return Objects.equals(owner, method.owner) &&
				Objects.equals(secondary, method.secondary) &&
				Objects.equals(primary, method.primary) &&
				Objects.equals(desc, method.desc);
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

	public String fancyContructorString() {
		return desc + " | " + owner + "(" + desc.split("\\(")[1];
	}
}
