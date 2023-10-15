package tfc.mappings.types;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;
import tfc.mappings.structure.impl.Tsrg2MappingsHolder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Tsrg2 {
    private static final String url = "https://raw.githubusercontent.com/MinecraftForge/MCPConfig/master/versions/release/$version$/joined.tsrg";

    public static MappingsHolder generate(String version) {
        String mappingsFile = downloadMappings(version);
        return new Tsrg2MappingsHolder(mappingsFile);
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