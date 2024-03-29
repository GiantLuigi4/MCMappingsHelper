package tfc.mappings;

import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.types.Tsrg2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestTsrg {
    public static void main(String[] args) throws IOException {
        MappingsHolder hldr = Tsrg2.generate("1.20.2");
        if (!new File("tests").exists()) new File("tests").mkdirs();
        FileOutputStream fos = new FileOutputStream("tests/test_tsrg.txt");
        fos.write(hldr.toFancyString().getBytes());
        fos.flush();
        fos.close();
    }
}
