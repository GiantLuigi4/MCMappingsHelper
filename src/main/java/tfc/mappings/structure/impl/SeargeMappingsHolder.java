package tfc.mappings.structure.impl;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

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
				if (tempC != null) addClass(tempC);
				String mappedName = s.split(" ")[1];
				String otherName = s.split(" ")[0];
				tempC = new MappingsClass(otherName, mappedName);
				continue;
			}
			s = s.replace("\t", "");
			String[] parts = s.split(" ");
			if (parts.length == 2) {
				if (tempF != null) tempC.addField(tempF);
				tempF = new MappingsField(tempC.getMappedName(), parts[0], parts[1], "");
			} else if (parts.length == 3) {
				if (tempM != null) tempC.addMethod(tempM);
				tempM = new MappingsMethod(tempC.getMappedName(), parts[0], parts[2], parts[1]);
			}
		}
	}
}
