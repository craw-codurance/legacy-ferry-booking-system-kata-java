package ferry.booking.timetable;

import java.util.ArrayList;
import java.util.List;

public class TimeTable {

    private int id;
    private List<TimeTableEntry> entries;

    public TimeTable(int id) {
        this.entries = new ArrayList<>();
        this.id = id;
    }

    public List<TimeTableEntry> getEntries() {
        return entries;
    }
    
    public void add(TimeTableEntry timeTableEntry) {
        this.entries.add(timeTableEntry);
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id=" + id +
                ", entries=" + entries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTable timeTable = (TimeTable) o;

        if (id != timeTable.id) return false;
        return entries != null ? entries.equals(timeTable.entries) : timeTable.entries == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (entries != null ? entries.hashCode() : 0);
        return result;
    }
}
