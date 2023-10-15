package tfc.mappings.structure;

import java.util.HashMap;

public class MappingsHolder {
	public final HashMap<String, MappingsClass> classesByMap = new HashMap<>();
	public final HashMap<String, MappingsClass> classesByObf = new HashMap<>();

	public MappingsHolder() {}

	public MappingsClass getFromMapped(String name) {
		return classesByMap.get(name);
	}
	
	public MappingsClass getFromObsf(String name) {
		return classesByObf.get(name);
	}

	protected void addClass(MappingsClass mappingsClass) {
		classesByMap.put(mappingsClass.getMappedName(), mappingsClass);
		classesByObf.put(mappingsClass.getObsfucationName(), mappingsClass);
	}

	@Override
	public String toString() {
		return classesByMap.toString();
	}
	
	public String toFancyString() {
		StringBuilder map = new StringBuilder();
		for (MappingsClass c: classesByMap.values()) {
			map.append(c.fancyString()).append("\n");
		}
		return map.toString();
	}

	protected String parseFieldDescriptorFromString(String desc) {
		StringBuilder parsed = new StringBuilder();
		if (desc.contains("[]"))
			parsed.append("[");
		if (desc.contains("long"))
			parsed.append("J");
		else if (desc.contains("int"))
			parsed.append("I");
		else if (desc.contains("short"))
			parsed.append("S");
		else if (desc.contains("byte"))
			parsed.append("B");
		else if (desc.contains("char"))
			parsed.append("C");
		else if (desc.contains("float"))
			parsed.append("F");
		else if (desc.contains("double"))
			parsed.append("D");
		else if (desc.contains("boolean"))
			parsed.append("Z");
		else if (desc.contains("void"))
			parsed.append("V");
		else if (!desc.equals(""))
			parsed.append("L").append(desc.replace(".", "/").replace("[]", "")).append(";");

		return parsed.toString();
	}
}
