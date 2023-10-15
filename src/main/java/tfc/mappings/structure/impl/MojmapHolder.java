package tfc.mappings.structure.impl;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

import java.util.ArrayList;
import java.util.HashMap;

public class MojmapHolder extends MappingsHolder {
    public MojmapHolder(String mappings) {
        MappingsClass tempC = null;
        MappingsMethod tempM = null;
        MappingsField tempF = null;

        HashMap<String, String> mojToObf = new HashMap<>();

        for (String s : mappings.split("\n")) {
            if (!s.startsWith("#")) {
                if (!s.startsWith(" ")) {
                    if (tempC != null && tempM != null)
                        tempC.addMethod(tempM);
                    if (tempC != null && tempF != null)
                        tempC.addField(tempF);
                    if (tempC != null)
                        addClass(tempC);
                    String otherName = s.split(" -> ")[1].replace(":", "");
                    String primary = s.split(" -> ")[0];
                    tempC = new MappingsClass(otherName, primary.replace(".", "/"));
                    tempM = null;
                    tempF = null;

                    mojToObf.put(otherName, primary);
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

                        tempM = new MappingsMethod(tempC != null ? tempC.getObsfucationName() : "", secondaryName, primaryName, desc.toString());
                    } else {
                        if (tempC != null && tempF != null)
                            tempC.addField(tempF);
                        String[] strings = noSpaceString.split("\\s");
                        String desc = parseFieldDescriptorFromString(strings[0]);
                        String primaryName = strings[1];
                        String secondaryName = strings[3];
                        tempF = new MappingsField(tempC != null ? tempC.getObsfucationName() : "", secondaryName, primaryName, desc);
                    }
                }
            }
        }

        if (tempC != null && tempM != null)
            tempC.addMethod(tempM);
        if (tempC != null && tempF != null)
            tempC.addField(tempF);
        if (tempC != null)
            addClass(tempC);

        classesByMap.values().forEach((v) -> {
            ArrayList<MappingsMethod> methods = new ArrayList<>();
            for (MappingsMethod method : v.getMethods()) {
                MappingsMethod copy = new MappingsMethod(
                        method.getOwner(), method.getObsf(), method.getMapped(),
                        remapDesc(method.getDesc())
                );
                methods.add(copy);
            }
            v.getMethods().clear();
            v.getMethods().addAll(methods);
        });
    }

    protected String remapDesc(String desc) {
        StringBuilder bldr = new StringBuilder();
        char[] chrs = desc.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            bldr.append(chrs[i]);
            if (chrs[i] == 'L') {
                i++;
                StringBuilder cls = new StringBuilder();
                while (chrs[i] != ';') {
                    cls.append(chrs[i]);
                    i++;
                }
                MappingsClass cl = getFromMapped(cls.toString());
                if (cl != null)
                    bldr.append(cl.getObsfucationName());
                else cls.toString();
                bldr.append(chrs[i]);
            }
        }
        return bldr.toString();
    }
}
