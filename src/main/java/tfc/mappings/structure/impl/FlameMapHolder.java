package tfc.mappings.structure.impl;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

public class FlameMapHolder extends MappingsHolder {
    public FlameMapHolder(String mappings) {
        super();
        MappingsClass currClass = null;
        MappingsMethod currMethod = null;
        MappingsField currField = null;
        for (String s : mappings.split("\n")) {
            if (s.startsWith("e-")) {
                assert currClass != null;
                for (MappingsMethod method : getFromObsf(s.substring(2)).getMethods())
                    currClass.addMethod(method);
            } else if (!s.startsWith("f-") && !s.startsWith("m-") && !s.isEmpty()) {
                if (currClass != null && currMethod != null)
                    currClass.addMethod(currMethod);
                if (currClass != null && currField != null)
                    currClass.addField(currField);
                if (currClass != null)
                    addClass(currClass);
                String otherName = s.split(" : ")[1].replace(":", "/");
                String primary = s.split(" : ")[0].replace(".", "/");
                currClass = new MappingsClass(otherName, primary);
                currMethod = null;
                currField = null;

            } else if (s.startsWith("f-")) {
                if (currClass != null && currField != null)
                    currClass.addField(currField);
                String desc = parseFieldDescriptorFromString(s.substring(2, s.indexOf(" ")));
                String primaryName = s.split("->")[1];
                String secondaryName = s.split("->")[0].substring(s.indexOf(" : ")).substring(3);
                currField = new MappingsField(currClass != null ? currClass.getObsfucationName() : "", secondaryName, primaryName, desc);
            } else if (s.startsWith("m-")) {
                if (currClass != null && currMethod != null)
                    currClass.addMethod(currMethod);

                String desc = s.substring(2, s.indexOf(" "));
                String primaryName = s.split("->")[1];
                String secondaryName = s.split("->")[0].substring(s.indexOf(" : ")).substring(3);
                currMethod = new MappingsMethod(currClass != null ? currClass.getObsfucationName() : "", secondaryName, primaryName, desc);
            }
        }

        if (currClass != null && currMethod != null)
            currClass.addMethod(currMethod);
        if (currClass != null && currField != null)
            currClass.addField(currField);
        if (currClass != null)
            addClass(currClass);
    }
}
