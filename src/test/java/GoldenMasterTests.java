import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import ferry.booking.Program;
import org.junit.Test;

public class GoldenMasterTests {

    private void writeToFile(String fileName) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new File(fileName));
            Program.mainWithTestData(ps);
        } catch (FileNotFoundException e) {
        } finally {
            ps.close();
        }
    }

    private String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }

    @Test
    public void compare_to_golden_master() throws IOException {
        writeToFile("test-run.txt");
        String master = readFile("master.txt");
        String tests = readFile("test-run.txt");
        assertEquals(tests, master);
    }
}
