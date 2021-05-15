package tfc.mappings.types;

import tfc.mappings.structure.MappingsHolder;

import java.io.InputStream;
import java.net.URL;
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
			URL url1 = new URL(url.replace("%version%", version));
			InputStream stream = url1.openStream();
			ArrayList<Byte> bytes = new ArrayList<>();
			byte b;
			while ((b = (byte) stream.read()) != -1) {
				bytes.add(b);
			}
			stream.close();
			byte[] bytesA = new byte[bytes.size()];
			for (int i = 0; i < bytes.size(); i++)
				bytesA[i] = bytes.get(i);
			return new MappingsHolder(new String(bytesA));
		} catch (Throwable err) {
			err.printStackTrace();
			throw new RuntimeException(err);
		}
	}
}
