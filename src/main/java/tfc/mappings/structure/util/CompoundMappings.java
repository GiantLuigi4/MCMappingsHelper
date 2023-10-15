package tfc.mappings.structure.util;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

public class CompoundMappings extends MappingsHolder {
    public CompoundMappings(MappingsHolder classes, MappingsHolder methods, MappingsHolder fields) {
        classes.classesByMap.forEach((k, v) -> {
            MappingsClass cls = new MappingsClass(v.getObsfucationName(), v.getMappedName());
            for (MappingsMethod method : methods.getFromObsf(cls.getObsfucationName()).getMethods()) {
                cls.getMethods().add(method);
            }
            for (MappingsField field : methods.getFromObsf(cls.getObsfucationName()).getFields()) {
                cls.getFields().add(field);
            }

            this.classesByMap.put(cls.getMappedName(), cls);
        });
    }
}
