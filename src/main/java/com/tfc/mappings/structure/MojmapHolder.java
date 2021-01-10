package com.tfc.mappings.structure;

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
					tempC = new Class(otherName, primary.replace(".","/"));
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
		
		if (tempC != null && tempM != null)
			tempC.addMethod(tempM);
		if (tempC != null && tempF != null)
			tempC.addField(tempF);
		if (tempC != null)
			classes.put(tempC.getPrimaryName(), tempC);
	}
}
