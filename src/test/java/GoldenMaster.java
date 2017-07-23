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
        Program program = new Program(output);
        program.start();
        program.doCommand("help");
        program.doCommand("list ports");
        program.doCommand("search 2 3 00:00");
        program.doCommand("search 2 3 00:00");
        program.doCommand("book 10 2");
        program.doCommand("search 2 3 00:00");
        program.doCommand("book 10 10");
        program.doCommand("book 10 1");
        program.doCommand("search 1 2 01:00");
        program.doCommand("book 4 2");
        program.doCommand("book 6 8");
        program.doCommand("search 1 2 01:00");
        program.doCommand("search 1 3 01:00");
        program.doCommand("search 1 3 01:30");
        program.doCommand("book 5 16");
        program.doCommand("book 16 16");
        program.doCommand("search 1 3 00:00");
        program.doCommand("list bookings");
        output.close();
    }
}
