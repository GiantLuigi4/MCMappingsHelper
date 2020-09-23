package com.tfc.mappings;

import com.tfc.mappings.structure.Class;
import com.tfc.mappings.structure.Holder;
import com.tfc.mappings.types.Intermediary;
import com.tfc.mappings.types.Mojang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws IOException {
		for (int i = 14; i <= 16; i++) {
			for (int sub = 0;sub<10;sub++) {
				String ver = i+"."+sub;
				if (sub == 0) {
					ver = ""+i;
				}
				try {
					long startMap = new Date().getTime();
					Holder holder = Intermediary.generate("1." + ver);
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
					Holder holder = Mojang.generate("1." + ver);
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
			}
		}
//		for (Method m : block.getMethods())
//			System.out.println(m.getDesc());
//		for (Field field : block.getFields())
//			System.out.println(field.getDesc());
	}
}
