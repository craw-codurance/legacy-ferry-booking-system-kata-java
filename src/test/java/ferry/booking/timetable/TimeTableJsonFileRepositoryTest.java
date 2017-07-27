package ferry.booking.timetable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class TimeTableJsonFileRepositoryTest {

    @Test
    public void all() throws Exception {
        List<TimeTableEntry> expected = buildExpected();
        TimeTableEntryRepository timeTables = new TimeTableEntryJsonFileRepository();
        assertEquals(expected, timeTables.all());
    }

    private List<TimeTableEntry> buildExpected() {
        List<TimeTableEntry> expected = new ArrayList<>();

        expected.add(new TimeTableEntry(1, 1, 2, 0, 30));
        expected.add(new TimeTableEntry(16, 1, 3, 10, 45));
        expected.add(new TimeTableEntry(2, 1, 2, 20, 30));
        expected.add(new TimeTableEntry(3, 1, 2, 40, 30));
        expected.add(new TimeTableEntry(4, 1, 2, 60, 30));
        expected.add(new TimeTableEntry(5, 1, 3, 70, 45));
        expected.add(new TimeTableEntry(6, 1, 2, 80, 30));
        expected.add(new TimeTableEntry(7, 1, 2, 100, 30));
        expected.add(new TimeTableEntry(8, 2, 1, 10, 30));
        expected.add(new TimeTableEntry(9, 2, 1, 30, 30));
        expected.add(new TimeTableEntry(10, 2, 3, 40, 35));
        expected.add(new TimeTableEntry(11, 2, 1, 50, 30));
        expected.add(new TimeTableEntry(12, 2, 1, 70, 30));
        expected.add(new TimeTableEntry(13, 2, 1, 90, 30));
        expected.add(new TimeTableEntry(14, 2, 3, 100, 35));
        expected.add(new TimeTableEntry(15, 2, 1, 110, 30));
        expected.add(new TimeTableEntry(17, 3, 1, 25, 45));
        expected.add(new TimeTableEntry(18, 3, 2, 40, 35));
        expected.add(new TimeTableEntry(19, 3, 1, 85, 45));
        expected.add(new TimeTableEntry(20, 3, 2, 100, 35));

        return expected;
    }

}