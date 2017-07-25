package ferry.booking;

import java.util.List;

public class TimeTable {

    public int id;
    public List<TimeTableEntry> entries;

    public TimeTable() {
    }

    public TimeTable(int id) {
        this.id = id;
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
