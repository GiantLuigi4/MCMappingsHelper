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
					if (s.contains("(")) {
						if (tempC != null && tempM != null)
							tempC.addMethod(tempM);
						String noNumberStrings = noSpaceString.replaceAll("(\\d*:)", "");
						String[] strings = noNumberStrings.split("\\s");
						String[] methodArray = strings[1].split("\\(");

						String primaryName = methodArray[0];
						StringBuilder desc = new StringBuilder("(");
						for (String arg : methodArray[1].replace(")", "").split(","))
							desc.append(parseFieldDescriptorFromString(arg));
						desc.append(")").append(parseFieldDescriptorFromString(strings[0]));

						String secondaryName = strings[3];

						tempM = new Method(tempC != null ? tempC.getSecondaryName() : "", secondaryName, primaryName, desc.toString());
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
