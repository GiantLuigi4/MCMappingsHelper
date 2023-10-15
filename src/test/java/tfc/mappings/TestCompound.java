package tfc.mappings;

import tfc.mappings.structure.MappingsHolder;
import tfc.mappings.structure.util.CompoundMappings;
import tfc.mappings.types.Mojang;
import tfc.mappings.types.Tsrg2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestCompound {
    public static void main(String[] args) throws IOException {
        MappingsHolder hldr = Tsrg2.generate("1.20.2");
        MappingsHolder moj = Mojang.generate("1.20.2");
        MappingsHolder compound = new CompoundMappings(moj, hldr, hldr);
        if (!new File("tests").exists()) new File("tests").mkdirs();
        FileOutputStream fos = new FileOutputStream("tests/test_mojsrg.txt");
        fos.write(compound.toFancyString().getBytes());
        fos.flush();
        fos.close();
    }
}
