package com.tfc.mappings.types;

import com.tfc.mappings.structure.Holder;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Intermediary {
	private static final String url =
			"https://raw.githubusercontent.com/FabricMC/intermediary/master/mappings/%version%.tiny";
	
	/**
	 * Creates a com.tfc.mappings holder from the fabric intermediary com.tfc.mappings
	 *
	 * @param version the version you want
	 * @return the holder for the com.tfc.mappings
	 */
	public static Holder generate(String version) {
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
			return new Holder(new String(bytesA));
		} catch (Throwable err) {
			err.printStackTrace();
		}
		return null;
	}
}
