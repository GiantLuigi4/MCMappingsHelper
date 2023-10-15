package tfc.mappings.structure.util;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

public class CompoundMappings extends MappingsHolder {
    public CompoundMappings(MappingsHolder classes, MappingsHolder methods, MappingsHolder fields) {
        classes.classesByMap.forEach((k, v) -> {
            MappingsClass cls = new MappingsClass(v.getObsfucationName(), v.getMappedName());
            if (methods.classesByObf.containsKey(cls.getObsfucationName())) {
                for (MappingsMethod method : methods.getFromObsf(cls.getObsfucationName()).getMethods()) {
                    cls.getMethods().add(method);
                }
            } else
                for (MappingsMethod field : v.getMethods()) cls.addMethod(field);

            if (fields.classesByObf.containsKey(cls.getObsfucationName())) {
                for (MappingsField field : fields.getFromObsf(cls.getObsfucationName()).getFields()) {
                    cls.getFields().add(field);
                }
            } else
                for (MappingsField field : v.getFields()) cls.addField(field);

            this.classesByMap.put(cls.getMappedName(), cls);
        });
    }
}
