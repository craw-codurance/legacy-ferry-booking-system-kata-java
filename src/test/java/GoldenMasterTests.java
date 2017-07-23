import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GoldenMasterTests {

    @Test
    public void compare_to_golden_master() throws IOException {
        GoldenMaster goldenMaster = new GoldenMaster("test-run.txt");
        goldenMaster.run();
        String master = goldenMaster.readFile("master.txt");
        String tests = goldenMaster.readFile("test-run.txt");
        assertEquals(tests, master);
    }
}
