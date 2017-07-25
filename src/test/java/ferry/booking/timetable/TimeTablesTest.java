package ferry.booking.timetable;

import ferry.booking.TimeTable;
import ferry.booking.TimeTableEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TimeTablesTest {

    @Test
    public void all() throws Exception {
        List<TimeTable> expected = buildExpected();
        TimeTables timeTables = new TimeTables();
        assertEquals(expected, timeTables.all());
    }

    private List<TimeTable> buildExpected() {
        List<TimeTable> expected = new ArrayList<>();
        TimeTable tt0 = new TimeTable(0);
        expected.add(tt0);

        TimeTable tt1 = new TimeTable(1);
        tt1.entries.add(new TimeTableEntry(1, 1, 1, 2, 0, 30));
        tt1.entries.add(new TimeTableEntry(16, 1, 1, 3, 10, 45));
        tt1.entries.add(new TimeTableEntry(2, 1, 1, 2, 20, 30));
        tt1.entries.add(new TimeTableEntry(3, 1, 1, 2, 40, 30));
        tt1.entries.add(new TimeTableEntry(4, 1, 1, 2, 60, 30));
        tt1.entries.add(new TimeTableEntry(5, 1, 1, 3, 70, 45));
        tt1.entries.add(new TimeTableEntry(6, 1, 1, 2, 80, 30));
        tt1.entries.add(new TimeTableEntry(7, 1, 1, 2, 100, 30));
        expected.add(tt1);

        TimeTable tt2 = new TimeTable(2);
        tt2.entries.add(new TimeTableEntry(8, 2, 2, 1, 10, 30));
        tt2.entries.add(new TimeTableEntry(9, 2, 2, 1, 30, 30));
        tt2.entries.add(new TimeTableEntry(10, 2, 2, 3, 40, 35));
        tt2.entries.add(new TimeTableEntry(11, 2, 2, 1, 50, 30));
        tt2.entries.add(new TimeTableEntry(12, 2, 2, 1, 70, 30));
        tt2.entries.add(new TimeTableEntry(13, 2, 2, 1, 90, 30));
        tt2.entries.add(new TimeTableEntry(14, 2, 2, 3, 100, 35));
        tt2.entries.add(new TimeTableEntry(15, 2, 2, 1, 110, 30));
        expected.add(tt2);

        TimeTable tt3 = new TimeTable(3);
        tt3.entries.add(new TimeTableEntry(17, 3, 3, 1, 25, 45));
        tt3.entries.add(new TimeTableEntry(18, 3, 3, 2, 40, 35));
        tt3.entries.add(new TimeTableEntry(19, 3, 3, 1, 85, 45));
        tt3.entries.add(new TimeTableEntry(20, 3, 3, 2, 100, 35));
        expected.add(tt3);
        return expected;
    }

}