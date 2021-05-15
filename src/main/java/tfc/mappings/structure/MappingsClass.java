package tfc.mappings.structure;

import java.util.ArrayList;
import java.util.Objects;

public class MappingsClass {
	private final ArrayList<MappingsMethod> methods = new ArrayList<>();
	private final ArrayList<MappingsField> fields = new ArrayList<>();
	
	private final String secondaryName;
	private final String primaryName;
	
	public MappingsClass(String secondaryName, String primaryName) {
		this.secondaryName = secondaryName;
		this.primaryName = primaryName;
	}
	
	protected void addMethod(MappingsMethod method) {
		methods.add(method);
	}
	
	protected void addField(MappingsField field) {
		fields.add(field);
	}
	
	public MappingsMethod getMethodPrimary(String inter) {
		for (MappingsMethod m : methods) {
			if (m.getPrimary().equals(inter))
				return m;
		}
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MappingsClass aClass = (MappingsClass) o;
		return Objects.equals(methods, aClass.methods) &&
				Objects.equals(fields, aClass.fields) &&
				Objects.equals(secondaryName, aClass.secondaryName) &&
				Objects.equals(primaryName, aClass.primaryName);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(methods, fields, secondaryName, primaryName);
	}
	
	@Override
	public String toString() {
		return "Class{" +
				"methods=" + methods +
				", fields=" + fields +
				", secondaryName='" + secondaryName + '\'' +
				", primaryName='" + primaryName + '\'' +
				'}';
	}
	
	public MappingsMethod getMethodSecondary(String inter) {
		for (MappingsMethod m : methods) {
			if (m.getSecondary().equals(inter))
				return m;
		}
		return null;
	}
	
	public ArrayList<MappingsMethod> getMethods() {
		return methods;
	}
	
	public ArrayList<MappingsField> getFields() {
		return fields;
	}
	
	public String getSecondaryName() {
		return secondaryName;
	}
	
	public String getPrimaryName() {
		return primaryName;
	}
	
	public String fancyString() {
		StringBuilder map = new StringBuilder();
		map.append("Class: ").append(primaryName).append(" : ").append(secondaryName).append("\n");
		for (MappingsMethod m : methods) {
			if (m.getSecondary().equals("<init>")) {
				map.append("\tConstructor: ").append(m.fancyContructorString()).append("\n");
			} else if (m.getSecondary().equals("<clinit>")) {
				map.append("\tStatic constructor: ").append(m.fancyContructorString()).append("\n");
			} else {
				map.append("\tMethod: ").append(m.fancyString()).append("\n");
			}
		}
		for (MappingsField f : fields)
			map.append("\tField: ").append(f.fancyString()).append("\n");
		return map.substring(0,map.length()-1);
	}
}
