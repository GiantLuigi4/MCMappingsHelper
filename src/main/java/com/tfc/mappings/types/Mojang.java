package com.tfc.mappings.types;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tfc.mappings.structure.MojmapHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Mojang {

	private static String versions = null;

	static {
		try {
			versions = readUrl("https://launchermeta.mojang.com/mc/game/version_manifest.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MojmapHolder generate(String versionNumber) {
		Gson gson = new Gson();
		MinecraftVersionMeta.Version versionToSearch = null;
		MinecraftVersionMeta meta = gson.fromJson(versions, MinecraftVersionMeta.class);
		for (MinecraftVersionMeta.Version version : meta.versions) {
			if (version.id.equals(versionNumber)) {
				versionToSearch = version;
				break;
			}
		}
		JsonParser parser = new JsonParser();
		try {
			assert versionToSearch != null;
			JsonElement tree = parser.parse(readUrl(versionToSearch.url));
			
			JsonObject downloads = readJsonObject(tree.getAsJsonObject(), s -> s.equals("downloads"));
			JsonObject clientMappings = readJsonObject(Objects.requireNonNull(downloads).getAsJsonObject(), s -> s.equals("client_mappings"));
			for (Map.Entry<String, JsonElement> clientEntry : Objects.requireNonNull(clientMappings).entrySet()) {
				if (clientEntry.getKey().equals("url")) {
					return new MojmapHolder(readUrl(clientEntry.getValue().getAsString()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return null;
	}

	private static String readUrl(String urlString) throws IOException {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder builder = new StringBuilder();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				builder.append(chars, 0, read);

			return builder.toString();

		} finally {
			if (reader != null)
				reader.close();
		}
	}

	private static JsonObject readJsonObject(JsonObject object, Function<String, Boolean> validator) {
		for (Map.Entry<String, JsonElement> jsonEntry : object.entrySet()) {
			if (validator.apply(jsonEntry.getKey())) {
				return jsonEntry.getValue().getAsJsonObject();
			}
		}
		return null;
	}

	private static class MinecraftVersionMeta {

		public List<Version> versions;

		public static class Version {
			public String id;
			public String url;
		}
	}

}
