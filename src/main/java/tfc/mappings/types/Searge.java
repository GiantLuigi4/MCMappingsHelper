package tfc.mappings.types;

import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.SeargeMappingsHolder;

public class Searge {
	private static final String filePath = "https://raw.githubusercontent.com/MinecraftForge/MCPConfig/0cdc6055297f0b30cf3e27e59317f229a30863a6/versions/release/%game_version%/joined.tsrg";
	
	public static MappingsHolder generate(String version) {
		try {
			return new SeargeMappingsHolder(Mojang.readUrl(filePath.replace("%game_version%", version)));
		} catch (Throwable ignored) {
			ignored.printStackTrace();
			return null;
		}
	}
}
