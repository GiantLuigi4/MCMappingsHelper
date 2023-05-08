package tfc.mappings.types;

import tfc.mappings.structure.MappingsClass;
import tfc.mappings.structure.MappingsField;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.MappingsMethod;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Tsrg2 {
	private static final String url = "https://raw.githubusercontent.com/MinecraftForge/MCPConfig/master/versions/release/$version$/joined.tsrg";
	
	public static MappingsHolder parseMappings(String version) {
		// TODO: cache
		String mappingsFile = downloadMappings(version);
		MappingsHolder mappings = new MappingsHolder();
		MappingsClass mappingsClass = null;
		MappingsMethod method = null;
		MappingsField field = null;
		for (String string : mappingsFile.split("\n")) {
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
					if (mappingsClass != null) mappings.classes.put(mappingsClass.getPrimaryName(), mappingsClass);
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
					field = new MappingsField(mappingsClass.getPrimaryName(), obf, descOrName, "");
				} else {
					// method
					if (method != null) mappingsClass.getMethods().add(method);
					String name = str[2];
					method = new MappingsMethod(mappingsClass.getPrimaryName(), obf, name, descOrName /* TODO */);
				}
			}
		}
		return mappings;
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