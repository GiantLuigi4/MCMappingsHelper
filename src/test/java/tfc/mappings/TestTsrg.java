package tfc.mappings;

import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.impl.MojmapHolder;
import tfc.mappings.types.Mojang;
import tfc.mappings.types.Tsrg2;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestTsrg {
    public static void main(String[] args) throws IOException {
        MappingsHolder hldr = Tsrg2.parseMappings("1.20.2");
        FileOutputStream fos = new FileOutputStream("test_tsrg.txt");
        fos.write(hldr.toFancyString().getBytes());
        fos.flush();
        fos.close();
    }
}
