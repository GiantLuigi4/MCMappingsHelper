package tfc.mappings.structure;

import java.util.ArrayList;
import java.util.Objects;

public class MappingsClass {
	private final ArrayList<MappingsMethod> methods = new ArrayList<>();
	private final ArrayList<MappingsField> fields = new ArrayList<>();
	
	private final String obsfucationName;
	private final String mappedName;
	
	public MappingsClass(String obsfucationName, String mappedName) {
		this.obsfucationName = obsfucationName;
		this.mappedName = mappedName;
	}
	
	public void addMethod(MappingsMethod method) {
		methods.add(method);
	}

	public void addField(MappingsField field) {
		fields.add(field);
	}
	
	public MappingsMethod methodFromMapped(String inter) {
		for (MappingsMethod m : methods) {
			if (m.getMapped().equals(inter))
				return m;
		}
		return null;
	}

	public MappingsMethod methodFromObsf(String inter) {
		for (MappingsMethod m : methods) {
			if (m.getObsf().equals(inter))
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
				Objects.equals(obsfucationName, aClass.obsfucationName) &&
				Objects.equals(mappedName, aClass.mappedName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(methods, fields, obsfucationName, mappedName);
	}

	@Override
	public String toString() {
		return "Class{" +
				"methods=" + methods +
				", fields=" + fields +
				", secondaryName='" + obsfucationName + '\'' +
				", primaryName='" + mappedName + '\'' +
				'}';
	}

	public ArrayList<MappingsMethod> getMethods() {
		return methods;
	}
	
	public ArrayList<MappingsField> getFields() {
		return fields;
	}
	
	public String getObsfucationName() {
		return obsfucationName;
	}
	
	public String getMappedName() {
		return mappedName;
	}
	
	public String fancyString() {
		StringBuilder map = new StringBuilder();
		map.append("Class: ").append(mappedName).append(" : ").append(obsfucationName).append("\n");
		for (MappingsMethod m : methods) {
			if (m.getObsf().equals("<init>")) {
				map.append("\tConstructor: ").append(m.fancyContructorString()).append("\n");
			} else if (m.getObsf().equals("<clinit>")) {
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
