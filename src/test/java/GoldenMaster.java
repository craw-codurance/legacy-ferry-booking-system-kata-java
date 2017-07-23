import ferry.booking.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoldenMaster {

    private PrintStream output;

    public GoldenMaster(String outputFileName) throws FileNotFoundException {
        this.output = new PrintStream(new File(outputFileName));
    }

    public void run() {
        Program.mainWithTestData(output);
        output.close();
    }

    public String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
}
