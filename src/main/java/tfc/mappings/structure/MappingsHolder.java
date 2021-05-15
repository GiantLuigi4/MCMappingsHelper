package tfc.mappings.structure;

import java.util.HashMap;

public class MappingsHolder {
	public final HashMap<String, MappingsClass> classes = new HashMap<>();

	public MappingsHolder() {}

	public MappingsClass getFromPrimaryName(String name) {
		return classes.get(name);
	}
	
	public MappingsClass getFromSecondaryName(String name) {
		for (MappingsClass c : classes.values())
			if (c.getSecondaryName().equals(name)) return c;
		return null;
	}
	
	public MappingsHolder(String mappings) {
		MappingsClass tempC = null;
		MappingsMethod tempM = null;
		MappingsField tempF = null;
		for (String s : mappings.split("\n")) {
			if (s.startsWith("CLASS")) {
				if (tempC != null && tempM != null)
					tempC.addMethod(tempM);
				if (tempC != null && tempF != null)
					tempC.addField(tempF);
				if (tempC != null)
					classes.put(tempC.getPrimaryName(),tempC);
				String otherName = s.replace("CLASS\t","");
				otherName = otherName.substring(0,otherName.indexOf("\t"));
				String primary = s.replace("CLASS\t"+otherName+"\t","");
				tempC = new MappingsClass(
						otherName,primary
				);
				tempM = null;
				tempF = null;
			} else if (s.startsWith("METHOD")) {
				if (tempC != null && tempM != null)
					tempC.addMethod(tempM);
				String otherName = s.replace("METHOD\t","");
				otherName = otherName.substring(0,otherName.indexOf("\t"));
				String desc = s.replace("METHOD\t"+otherName+"\t","");
				desc = desc.substring(0,desc.indexOf("\t"));
				String primaryName_a = s.replace("METHOD\t"+otherName+"\t"+desc+"\t","");
				primaryName_a = primaryName_a.substring(0,primaryName_a.indexOf("\t"));
				String primaryName = s.replace("METHOD\t"+otherName+"\t"+desc+"\t"+primaryName_a+"\t","");
				tempM = new MappingsMethod(
						otherName,primaryName_a,primaryName,desc
				);
			} else if (s.startsWith("FIELD")) {
				if (tempC != null && tempF != null)
					tempC.addField(tempF);
				String otherName = s.replace("FIELD\t","");
				otherName = otherName.substring(0,otherName.indexOf("\t"));
				String desc = s.replace("FIELD\t"+otherName+"\t","");
				desc = desc.substring(0,desc.indexOf("\t"));
				String primaryName_a = s.replace("FIELD\t"+otherName+"\t"+desc+"\t","");
				primaryName_a = primaryName_a.substring(0,primaryName_a.indexOf("\t"));
				String primaryName = s.replace("FIELD\t"+otherName+"\t"+desc+"\t"+primaryName_a+"\t","");
				tempF = new MappingsField(
						otherName,primaryName_a,primaryName,desc
				);
			}
		}
//		System.out.println(classes.toString());
		
		if (tempC != null && tempM != null)
			tempC.addMethod(tempM);
		if (tempC != null && tempF != null)
			tempC.addField(tempF);
		if (tempC != null)
			classes.put(tempC.getPrimaryName(), tempC);
	}
	
	@Override
	public String toString() {
		return classes.toString();
	}
	
	public String toFancyString() {
		StringBuilder map = new StringBuilder();
		for (MappingsClass c:classes.values()) {
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
