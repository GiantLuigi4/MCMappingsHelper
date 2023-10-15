package tfc.mappings;

import tfc.mappings.structure.impl.MojmapHolder;
import tfc.mappings.types.Mojang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestMoj {
    public static void main(String[] args) throws IOException {
        MojmapHolder hldr = Mojang.generate("1.20.2");
        if (!new File("tests").exists()) new File("tests").mkdirs();
        FileOutputStream fos = new FileOutputStream("tests/test_moj.txt");
        fos.write(hldr.toFancyString().getBytes());
        fos.flush();
        fos.close();
    }
}
