package ferry.booking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static ferry.booking.Util.readFileToString;

public class TimeTables {

    private final List<TimeTableEntry> entries = new ArrayList<>();

    public TimeTables() {
        try {
            String json = readFileToString("/timetable.txt");
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                TimeTableEntry tte = new TimeTableEntry(
                        obj.getInt("Id"),
                        obj.getInt("TimeTableId"),
                        obj.getInt("OriginId"),
                        obj.getInt("DestinationId"),
                        obj.getLong("Time"),
                        obj.getLong("JourneyTime")
                );
                entries.add(tte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TimeTable> all() {
        List<TimeTable> result = new ArrayList<>();

        for (int i = 0; i < 4; i ++) {
            TimeTable timeTable = buildTimeTable(i);
            result.add(timeTable);
        }

        return result;
    }

    private TimeTable buildTimeTable(int originId) {
        List<TimeTableEntry> timeTableEntries = new ArrayList<>();
        for (TimeTableEntry entry : entries) {
            if (entry.getTimeTableId() == originId) {
                timeTableEntries.add(entry);
            }
        }
        addOrigin(timeTableEntries, originId);
        TimeTable timeTable = new TimeTable();
        timeTable.id = originId;
        timeTable.entries = timeTableEntries;
        return timeTable;
    }

    private void addOrigin(List<TimeTableEntry> entries, int origin) {
        for (TimeTableEntry timeTableEntry : entries) {
            timeTableEntry.setOriginId(origin);
        }
    }
}
