package mappings.structure;

import java.util.ArrayList;
import java.util.Objects;

public class Class {
	private final ArrayList<Method> methods = new ArrayList<>();
	private final ArrayList<Field> fields = new ArrayList<>();
	
	private final String secondaryName;
	private final String primaryName;
	
	public Class(String secondaryName, String primaryName) {
		this.secondaryName = secondaryName;
		this.primaryName = primaryName;
	}
	
	protected void addMethod(Method method) {
		methods.add(method);
	}
	
	protected void addField(Field field) {
		fields.add(field);
	}
	
	public Method getMethodPrimary(String inter) {
		for (Method m : methods) {
			if (m.getPrimary().equals(inter))
				return m;
		}
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Class aClass = (Class) o;
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
	
	public Method getMethodSecondary(String inter) {
		for (Method m : methods) {
			if (m.getSecondary().equals(inter))
				return m;
		}
		return null;
	}
	
	public ArrayList<Method> getMethods() {
		return methods;
	}
	
	public ArrayList<Field> getFields() {
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
		for (Method m : methods)
			map.append("\tMethod: ").append(m.fancyString()).append("\n");
		for (Field f : fields)
			map.append("\tField: ").append(f.fancyString()).append("\n");
		return map.substring(0,map.length()-1);
	}
}
