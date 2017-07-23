import ferry.booking.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoldenMaster {

    public void writeToFile(String fileName) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new File(fileName));
            Program.mainWithTestData(ps);
        } catch (FileNotFoundException e) {
        } finally {
            ps.close();
        }
    }

    public String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
}
