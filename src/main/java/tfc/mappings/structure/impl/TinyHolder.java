package tfc.mappings.structure.impl;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

public class TinyHolder extends MappingsHolder {
	public TinyHolder(String mappings) {
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
					addClass(tempC);
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
			addClass(tempC);
	}
}
