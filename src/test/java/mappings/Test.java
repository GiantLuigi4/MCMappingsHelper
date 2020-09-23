package mappings;

import mappings.structure.Class;
import mappings.structure.Field;
import mappings.structure.Holder;
import mappings.structure.Method;
import mappings.types.Intermediary;
import mappings.types.Mojang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws IOException {
		for (int i = 14; i <= 16; i++) {
			long startMap = new Date().getTime();
			Holder holder = Intermediary.generate("1." + i);
			long endMap = new Date().getTime();
			System.out.println("Mapping version 1." + i + " took: " + (endMap - startMap) + " ms");
			long startWrite = new Date().getTime();
			File f = new File("mappings\\inter\\1." + i + "_formatted.txt");
			f.getParentFile().mkdirs();
			f.createNewFile();
			FileOutputStream stream = new FileOutputStream(f);
			assert holder != null;
			stream.write(holder.toFancyString().getBytes());
			stream.close();
			long endWrite = new Date().getTime();
			System.out.println("Writing version 1." + i + " took: " + (endWrite - startWrite) + " ms");
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
		}
		Holder holder1 = Mojang.generate("1.15.2");
		File f = new File("mappings\\mojmap\\1.15.2_formatted.txt");
		f.getParentFile().mkdirs();
		f.createNewFile();
		FileOutputStream stream = new FileOutputStream(f);
		assert holder1 != null;
		stream.write(holder1.toFancyString().getBytes());
		stream.close();
		Class clazz = holder1.getFromPrimaryName("net.minecraft.world.entity.AgableMob"); //primary name is real name, secondary is proguarded
	}
}
