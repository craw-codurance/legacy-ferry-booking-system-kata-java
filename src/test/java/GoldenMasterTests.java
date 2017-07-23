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

    @Test
    public void compare_to_golden_master() throws IOException {
        GoldenMaster goldenMaster = new GoldenMaster();
        goldenMaster.writeToFile("test-run.txt");
        String master = goldenMaster.readFile("master.txt");
        String tests = goldenMaster.readFile("test-run.txt");
        assertEquals(tests, master);
    }
}
