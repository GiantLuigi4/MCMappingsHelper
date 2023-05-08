package tfc.mappings.types;

import tfc.mappings.structure.MappingsHolder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Intermediary {
	private static final String url =
			"https://raw.githubusercontent.com/FabricMC/intermediary/master/mappings/%version%.tiny";
	
	/**
	 * Creates a mappings holder from the fabric intermediary com.tfc.mappings
	 *
	 * @param version the version you want
	 * @return the holder for the mappings
	 */
	public static MappingsHolder generate(String version) {
		try {
			URL u = new URL(url.replace("%version%", version));
			
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
			
			return new MappingsHolder(txt);
		} catch (Throwable err) {
			err.printStackTrace();
			throw new RuntimeException(err);
		}
	}
}
