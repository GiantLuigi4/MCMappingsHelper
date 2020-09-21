package mappings.structure;

public class MojmapHolder extends Holder {

	public MojmapHolder(String mappings) {
		super();
		Class tempC = null;
		Method tempM = null;
		Field tempF = null;
		for (String s : mappings.split("\n")) {
			if (!s.startsWith("#")) {
				if (!s.startsWith(" ")) {
					if (tempC != null && tempM != null)
						tempC.addMethod(tempM);
					if (tempC != null && tempF != null)
						tempC.addField(tempF);
					if (tempC != null)
						classes.put(tempC.getPrimaryName(), tempC);
					String otherName = s.split(" -> ")[1].replace(":", "");
					String primary = s.split(" -> ")[0];
					tempC = new Class(otherName, primary);
					tempM = null;
					tempF = null;
				} else {
					String noSpaceString = s.replace("    ", "");
					if (Character.isDigit(s.charAt(4))) {
						if (tempC != null && tempM != null)
							tempC.addMethod(tempM);
						String[] noNumberStrings = noSpaceString.split(":");
						String[] strings = noNumberStrings[2].split("\\s");
						String desc = parseFieldDescriptorFromString(strings[0]);
						String[] methodArray = strings[1].split("\\(");
						StringBuilder primaryName = new StringBuilder(methodArray[0]).append("(");
						for (String arg : methodArray[1].replace(")", "").split(",")) {
							primaryName.append(parseFieldDescriptorFromString(arg));
						}
						primaryName.append(")");
						String secondaryName = strings[3];
						tempM = new Method(tempC != null ? tempC.getSecondaryName() : "", secondaryName, primaryName.toString(), desc);
					} else {
						if (tempC != null && tempF != null)
							tempC.addField(tempF);
						String[] strings = noSpaceString.split("\\s");
						String desc = parseFieldDescriptorFromString(strings[0]);
						String primaryName = strings[1];
						String secondaryName = strings[3];
						tempF = new Field(tempC != null ? tempC.getSecondaryName() : "", secondaryName, primaryName, desc);
					}
				}
			}
		}
	}

	private String parseFieldDescriptorFromString(String desc) {
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