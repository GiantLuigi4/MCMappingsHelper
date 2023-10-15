package tfc.mappings.types;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Tsrg2 {
    private static final String url = "https://raw.githubusercontent.com/MinecraftForge/MCPConfig/master/versions/release/$version$/joined.tsrg";

    public static MappingsHolder parseMappings(String version) {
        // TODO: cache
        String mappingsFile = downloadMappings(version);
        MappingsHolder mappings = new MappingsHolder();
        MappingsClass mappingsClass = null;
        MappingsMethod method = null;
        MappingsField field = null;
        String[] allLines = mappingsFile.split("\n");
        for (int i = 0; i < allLines.length; i++) {
            String string = allLines[i];

            if (!string.startsWith("\t")) {
                if (string.equals("tsrg2 obf srg id")) continue;
                if (string.startsWith("\t\t")) continue;
                string = string.replace("\t", "");
                String[] str = string.split(" ");
                String obf = str[0];
                String descOrName = str[1];
                if (descOrName.contains("/")) {
                    // class
                    if (method != null) mappingsClass.getMethods().add(method);
                    if (mappingsClass != null) mappings.classesByMap.put(mappingsClass.getMappedName(), mappingsClass);
                    method = null;
                    field = null;
                    mappingsClass = new MappingsClass(obf, descOrName);
                }
            } else {
                if (string.startsWith("\t\t")) continue;
                string = string.replace("\t", "");
                String[] str = string.split(" ");
                if (str.length <= 2) continue;
                String obf = str[0];
                String descOrName = str[1];
                if (descOrName.startsWith("f_") || str.length <= 3) {
                    // field
                    if (field != null) mappingsClass.getFields().add(field);
                    field = new MappingsField(mappingsClass.getMappedName(), obf, descOrName, "");
                } else {
                    // method
                    if (method != null) mappingsClass.getMethods().add(method);
                    String name = str[2];

                    method = new MappingsMethod(mappingsClass.getMappedName(), obf, name, descOrName /* TODO */);
                    int pCount = countParams(descOrName);
                    ArrayList<String> params = new ArrayList<>();
                    for (int pNo = 0; pNo < pCount; pNo++) {
                        i++;
                        if (allLines[i].trim().startsWith("static")) {
                            pNo--;
                            continue;
                        }

                        if (allLines[i].trim().startsWith("" + pNo)) {
                            params.add(allLines[i].trim().split(" ")[2]);
                        } else {
                            // something must've gone wrong
                            System.out.println("Found incorrect count of params for " + method.getMapped() + "!");
                            params.clear();
                            i--;
                            break;
                        }
                    }

                    if (!params.isEmpty())
                        method.setMappedParams(params);
                }
            }
        }
        return mappings;
    }

    protected static int countParams(String desc) {
        int count = 0;
        boolean inRefType = false;
        char[] chars = desc.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(' || chars[i] == '[') continue;
            if (chars[i] == ')') break;

            if (chars[i] == 'L') {
                while (chars[i] != ';') i++;
            }
            count++;
        }
        return count;
    }

    public static String downloadMappings(String version) {
        try {
            String url = Tsrg2.url.replace("$version$", version);
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            InputStream stream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[Math.max(4096, stream.available())];
            int read;
            while ((read = stream.read(buf)) != -1)
                outputStream.write(buf, 0, read);
            String txt = outputStream.toString();
            stream.close();
            outputStream.close();
            outputStream.flush();
            return txt;
        } catch (Throwable err) {
            return null;
        }
    }
}