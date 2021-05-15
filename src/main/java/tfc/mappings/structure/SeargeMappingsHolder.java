package tfc.mappings.structure;

public class SeargeMappingsHolder extends MappingsHolder {
	public SeargeMappingsHolder(String mappings) {
		MappingsClass tempC = null;
		MappingsMethod tempM = null;
		MappingsField tempF = null;
		for (String s : mappings.split("\n")) {
			if (!s.startsWith("\t")) {
				if (tempF != null) tempC.addField(tempF);
				tempF = null;
				if (tempM != null) tempC.addMethod(tempM);
				tempM = null;
				if (tempC != null) classes.put(tempC.getPrimaryName(), tempC);
				String mappedName = s.split(" ")[1];
				String otherName = s.split(" ")[0];
				tempC = new MappingsClass(otherName, mappedName);
				continue;
			}
			s = s.replace("\t", "");
			String[] parts = s.split(" ");
			if (parts.length == 2) {
				if (tempF != null) tempC.addField(tempF);
				tempF = new MappingsField(tempC.getPrimaryName(), parts[0], parts[1], "");
			} else if (parts.length == 3) {
				if (tempM != null) tempC.addMethod(tempM);
				tempM = new MappingsMethod(tempC.getPrimaryName(), parts[0], parts[2], parts[1]);
			}
		}
	}
}
