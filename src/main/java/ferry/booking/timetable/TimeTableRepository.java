package ferry.booking.timetable;

import ferry.booking.TimeTable;

import java.util.List;

public interface TimeTableRepository {
    List<TimeTable> all();
}
