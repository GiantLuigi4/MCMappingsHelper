package mappings;

import java.util.HashMap;

public class Holder {
	private final HashMap<String,Class> classes = new HashMap<>();
	
	public Class getFromPrimaryName(String name) {
		return classes.get(name);
	}
	
	public Class getFromSecondaryName(String name) {
		for (Class c : classes.values())
			if (c.getSecondaryName().equals(name)) return c;
		return null;
	}
	
	public Holder(String mappings) {
		Class tempC = null;
		Method tempM = null;
		Field tempF = null;
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
				tempC = new Class(
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
				tempM = new Method(
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
				tempF = new Field(
						otherName,primaryName_a,primaryName,desc
				);
			}
		}
//		System.out.println(classes.toString());
	}
	
	@Override
	public String toString() {
		return classes.toString();
	}
	
	public String toFancyString() {
		StringBuilder map = new StringBuilder();
		for (Class c:classes.values()) {
			map.append(c.fancyString()).append("\n");
		}
		return map.toString();
	}
}
