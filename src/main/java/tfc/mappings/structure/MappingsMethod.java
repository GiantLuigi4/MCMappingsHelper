package tfc.mappings.structure;

import java.util.List;
import java.util.Objects;

public class MappingsMethod {
    private final String owner;
    private final String obsfucation;
    private final String mapped;
    private final String desc;

    String[] mappedParams;

    public MappingsMethod(String owner, String obsfucation, String mapped, String desc) {
        this.owner = owner;
        this.obsfucation = obsfucation;
        this.mapped = mapped;
        this.desc = desc;
    }

    public void setMappedParams(List<String> mappedParams) {
        this.mappedParams = mappedParams.toArray(new String[0]);
    }

    public String[] getMappedParams() {
        return mappedParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappingsMethod method = (MappingsMethod) o;
        return Objects.equals(owner, method.owner) &&
                Objects.equals(obsfucation, method.obsfucation) &&
                Objects.equals(mapped, method.mapped) &&
                Objects.equals(desc, method.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, obsfucation, mapped, desc);
    }

    public String getOwner() {
        return owner;
    }

    public String getObsf() {
        return obsfucation;
    }

    public String getMapped() {
        return mapped;
    }

    public String getDesc() {
        return desc;
    }

    protected String paramsStr() {
        StringBuilder pstr = new StringBuilder();
        for (String mappedParam : mappedParams) {
            pstr.append(mappedParam).append(" ");
        }
        return pstr.toString().trim();
    }

    public String toString() {
        if (mappedParams != null)
            return desc + ":" + owner + ":" + obsfucation + ":" + mapped + ":" + paramsStr();
        return desc + ":" + owner + ":" + obsfucation + ":" + mapped;
    }

    public String fancyString() {
        if (mappedParams != null)
            return desc + " | " + mapped + " : " + obsfucation + " & " + paramsStr();
        return desc + " | " + mapped + " : " + obsfucation;
    }

    public String fancyContructorString() {
        return desc + " | " + owner + "(" + desc.split("\\(")[1];
    }
}
