import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GoldenMasterTests {

    private FileReader master;
    private FileReader tests;

    @Before
    public void setup() {
        master = new FileReader("master.txt");
        tests = new FileReader("test-run.txt");
    }

    @Test
    public void compare_to_golden_master() throws IOException {
        GoldenMaster goldenMaster = new GoldenMaster("test-run.txt");
        goldenMaster.run();
        assertEquals(tests.readFile(), master.readFile());
    }
}
