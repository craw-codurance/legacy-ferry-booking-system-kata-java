import ferry.booking.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class GoldenMaster {

    private PrintStream output;

    public GoldenMaster(String outputFileName) throws FileNotFoundException {
        this.output = new PrintStream(new File(outputFileName));
    }

    public void run() {
        Program.mainWithTestData(output);
        output.close();
    }
}
