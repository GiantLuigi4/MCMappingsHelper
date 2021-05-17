package tfc.mappings;

import tfc.mappings.structure.FlameMapHolder;
import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.types.Intermediary;
import tfc.mappings.types.Mojang;
import tfc.mappings.types.Searge;

import java.io.*;
import java.net.URL;
import java.util.Date;

public class Test {
	public static void main2(String[] args) throws IOException {
		for (int i = 14; i <= 16; i++) {
			for (int sub = 0;sub<10;sub++) {
				String ver = i+"."+sub;
				if (sub == 0) {
					ver = ""+i;
				}
				try {
					long startMap = new Date().getTime();
					MappingsHolder holder = Intermediary.generate("1." + ver);
					long endMap = new Date().getTime();
					System.out.println("Mapping version 1." + ver + " took: " + (endMap - startMap) + " ms");
					long startWrite = new Date().getTime();
					File f = new File("mappings\\inter\\1." + ver + "_formatted.txt");
					f.getParentFile().mkdirs();
					f.createNewFile();
					FileOutputStream stream = new FileOutputStream(f);
					assert holder != null;
					stream.write(holder.toFancyString().getBytes());
					stream.close();
					long endWrite = new Date().getTime();
					System.out.println("Writing version 1." + ver + " took: " + (endWrite - startWrite) + " ms");
					long startFind = new Date().getTime();
					String clazz = (holder.getFromPrimaryName("net/minecraft/class_1593").fancyString());
					long endFind = new Date().getTime();
					System.out.println("Finding phantom for version 1." + i + " took: " + (endFind - startFind) + " ms\n");
					f = new File("mappings\\inter\\1." + i + "_phantom_class.txt");
					f.getParentFile().mkdirs();
					f.createNewFile();
					stream = new FileOutputStream(f);
					stream.write(clazz.getBytes());
					stream.close();
				} catch (Throwable ignored) {
				}
				try {
					long startMap = new Date().getTime();
					MappingsHolder holder = Mojang.generate("1." + ver);
					long endMap = new Date().getTime();
					System.out.println("Mapping version 1." + ver + " took: " + (endMap - startMap) + " ms");
					long startWrite = new Date().getTime();
					File f = new File("mappings\\mojmap\\1." + ver + "_formatted.txt");
					f.getParentFile().mkdirs();
					f.createNewFile();
					FileOutputStream stream = new FileOutputStream(f);
					assert holder != null;
					stream.write(holder.toFancyString().getBytes());
					stream.close();
					long endWrite = new Date().getTime();
					System.out.println("Writing version 1." + ver + " took: " + (endWrite - startWrite) + " ms");
					long startFind = new Date().getTime();
					String clazz = (holder.getFromPrimaryName("net/minecraft/world/entity/monster/Phantom").fancyString());
					long endFind = new Date().getTime();
					System.out.println("Finding phantom for version 1." + ver + " took: " + (endFind - startFind) + " ms\n");
					f = new File("mappings\\mojmap\\1." + ver + "_phantom_class.txt");
					f.getParentFile().mkdirs();
					f.createNewFile();
					stream = new FileOutputStream(f);
					stream.write(clazz.getBytes());
					stream.close();
				} catch (Throwable ignored) {
				}
				try {
					long startMap = new Date().getTime();
					MappingsHolder holder = Searge.generate("1." + ver);
					long endMap = new Date().getTime();
					System.out.println("Mapping version 1." + ver + " took: " + (endMap - startMap) + " ms");
					long startWrite = new Date().getTime();
					File f = new File("mappings\\searge\\1." + ver + "_formatted.txt");
					f.getParentFile().mkdirs();
					f.createNewFile();
					FileOutputStream stream = new FileOutputStream(f);
					assert holder != null;
					stream.write(holder.toFancyString().getBytes());
					stream.close();
					long endWrite = new Date().getTime();
					System.out.println("Writing version 1." + ver + " took: " + (endWrite - startWrite) + " ms");
					long startFind = new Date().getTime();
					String clazz = (holder.getFromPrimaryName("net/minecraft/entity/monster/PhantomEntity").fancyString());
					long endFind = new Date().getTime();
					System.out.println("Finding phantom for version 1." + ver + " took: " + (endFind - startFind) + " ms\n");
					f = new File("mappings\\searge\\1." + ver + "_phantom_class.txt");
					f.getParentFile().mkdirs();
					f.createNewFile();
					stream = new FileOutputStream(f);
					stream.write(clazz.getBytes());
					stream.close();
				} catch (Throwable ignored) {
				}
			}
		}
//		for (Method m : block.getMethods())
//			System.out.println(m.getDesc());
//		for (Field field : block.getFields())
//			System.out.println(field.getDesc());
	}

	public static void main(String[] args) {
		if (false) {
			try {
				main2(args);
			} catch (Throwable ignored) {
			}
			return;
		}
		try {
			long startMap = new Date().getTime();
			MappingsHolder holder = new FlameMapHolder(readUrl("https://raw.githubusercontent.com/GiantLuigi4/FlameAPI-MC-Rewrite/master/mappings/flame_mappings.mappings"));
			long endMap = new Date().getTime();
			System.out.println("Mapping flame took: " + (endMap - startMap) + " ms");
			holder.classes.forEach((s, c) -> System.out.println(c.fancyString()));
			long startWrite = new Date().getTime();
			File f = new File("mappings\\flame\\mappings_formatted.txt");
			f.getParentFile().mkdirs();
			f.createNewFile();
			FileOutputStream stream = new FileOutputStream(f);
			stream.write(holder.toFancyString().getBytes());
			stream.close();
			long endWrite = new Date().getTime();
			System.out.println("Writing version took: " + (endWrite - startWrite) + " ms");
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
}
